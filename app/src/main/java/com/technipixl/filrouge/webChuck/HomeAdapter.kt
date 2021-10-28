package com.technipixl.filrouge.webChuck

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.technipixl.filrouge.R
import com.technipixl.filrouge.UI.HomeFragViewer

class HomeAdapter (activity:FragmentActivity, private val listener:HomeFragViewer.HomeFragClickListener):FragmentStateAdapter(activity) {
    private val fragments = mutableListOf<HomeFragViewer>().apply {
        add(HomeFragViewer.newInstance(R.drawable.wolf, "J'ai une faim de loup !", 1, listener))
        add(HomeFragViewer.newInstance(R.drawable.`fun`, "jsuis en dep!", 2, listener))
        add(HomeFragViewer.newInstance(R.drawable.run, "Faut qu'jme bouge !", 3, listener))
    }
    override fun getItemCount(): Int {
    return fragments.count()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}