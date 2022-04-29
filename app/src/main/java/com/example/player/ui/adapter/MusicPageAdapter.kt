package com.example.player.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.player.ui.fragment.playlist.MusicListFragment

class MusicPageAdapter(private val title: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return title
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Fragment {
        return MusicListFragment()
    }
}