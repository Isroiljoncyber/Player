package com.example.player.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.player.App.Companion.lastMusicPosition
import com.example.player.data.entity.MusicModel
import com.example.player.databinding.ItemMusicBinding
import com.example.player.viewmodel.MusicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicAdapter(
    private val musicList: List<MusicModel>,
    private val callBack: MusicPlayerCallBack
) :
    RecyclerView.Adapter<MusicAdapter.AdapterViewHolder>() {

    lateinit var viewModel: MusicViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding = ItemMusicBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        var currentModel = musicList[position]

        with(holder) {
            binding.mainShimmer.visibility = View.GONE
            binding.mainShimmer.stopShimmer()
            binding.mainShimmer.setShimmer(null)
            binding.tvName.text = currentModel.title
            binding.tvArtist.text = currentModel.artist

            // These are for lottio animation and I will fix it later
//            if (currentModel.lottieVisible)
//                binding.lottieView.visibility = View.VISIBLE
//            else
//                binding.lottieView.visibility = View.GONE
        }

        // Calling to viewModel to play music
        holder.itemView.setOnClickListener {
//            it.tvName.isSelected = true
            callBack.onClick(position)
//            currentModel.lottieVisible = true
//            notifyItemChanged(lastMusicPosition)
//            notifyItemChanged(position)
//            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = musicList.size

    class AdapterViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root)
}

interface MusicPlayerCallBack {
    fun onClick(position: Int)
}
