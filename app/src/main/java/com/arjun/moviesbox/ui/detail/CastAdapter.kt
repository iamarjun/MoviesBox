package com.arjun.moviesbox.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arjun.moviesbox.BuildConfig
import com.arjun.moviesbox.GlideApp
import com.arjun.moviesbox.R
import com.arjun.moviesbox.databinding.ItemCastBinding
import com.arjun.moviesbox.model.Cast

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Cast>() {

        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Cast>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindCast(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    class CastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindCast(cast: Cast) {
            binding.castName.text = cast.name
            binding.castCharacter.text = cast.character
            GlideApp.with(itemView)
                .load(BuildConfig.TMDB_IMAGE_BASE_URL + cast.profilePath)
                .error(R.drawable.ic_undraw_avatar)
                .placeholder(R.drawable.ic_undraw_images)
                .override(120, 120)
                .into(binding.castPhoto)
        }
    }
}