package com.arjun.moviesbox.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arjun.moviesbox.TmdbAPI
import com.arjun.moviesbox.db.MovieDatabase
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val restAPI: TmdbAPI,
    private val database: MovieDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PAGE
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                when {
                    remoteKeys == null -> PAGE
                    remoteKeys.nextKey == null -> throw InvalidObjectException("Remote key should not be null for $loadType")
                    else -> remoteKeys.nextKey
                }
            }

        }


        try {
            val apiResponse = restAPI.getPopularMovies(page)

            val movies = apiResponse.movies
            val endOfPaginationReached = movies.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao.clearRemoteKeys()
                    database.movieDao.clearMovie()
                }
                val prevKey = if (page == PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao.insertAll(keys)
                database.movieDao.insertMovieList(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao.remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                database.remoteKeysDao.remoteKeysMovieId(movieId)
            }
        }
    }


    companion object {
        private const val PAGE = 1
    }

}