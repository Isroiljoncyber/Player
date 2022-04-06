package com.example.player.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.player.data.entity.MusicModel
import com.example.player.databinding.FragmentMainPlayBinding
import com.example.player.databinding.ItemMusicBinding

class MusicAdapter(private val musicList: List<MusicModel>) :
    RecyclerView.Adapter<MusicAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding = ItemMusicBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        with(holder) {

        }
    }

    override fun getItemCount(): Int = musicList.size

    class AdapterViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root)
}
