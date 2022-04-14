package com.example.player.ui.adapter

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.player.data.entity.MusicModel
import com.example.player.databinding.ItemMusicBinding


class MusicAdapter(
    private val musicList: List<MusicModel>,
    private val callBack: MusicPlayerCallBack
) :
    RecyclerView.Adapter<MusicAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding = ItemMusicBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        var currentModel = musicList[position]

        with(holder) {
            binding.mainShimmer.visibility = View.GONE
            binding.mainShimmer.stopShimmer()
            binding.mainShimmer.setShimmer(null)
            binding.tvName.text = currentModel.title
            binding.tvArtist.text = currentModel.artist
        }

        // Calling to viewModel to play music
        holder.itemView.setOnClickListener {
//            it.tvName.isSelected = true
            callBack.onClick(position)
        }
    }

    override fun getItemCount(): Int = musicList.size

    class AdapterViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root)
}

interface MusicPlayerCallBack {
    fun onClick(position: Int)
}
