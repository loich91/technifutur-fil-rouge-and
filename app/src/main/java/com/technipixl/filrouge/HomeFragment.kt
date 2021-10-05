package com.technipixl.filrouge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.technipixl.filrouge.UI.HomeFragViewer
import com.technipixl.filrouge.databinding.FragmentHomeBinding
import com.technipixl.filrouge.webChuck.HomeAdapter


class HomeFragment : Fragment(), HomeFragViewer.HomeFragClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val onPageChangeCallback: ViewPager2.OnPageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            (activity as? HomeActivity)?.let {
                it.selectedHomeCardIndex = position
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = HomeAdapter(requireActivity(), this)
        binding.viewPager.clipToPadding = false
        binding.viewPager.setPadding(120, 0, 120, 0)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            //TabLayout selected @ position
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }


    override fun homeFragClickListener(menuIndex: Int) {
        when (menuIndex) {
            1 -> findNavController().navigate(R.id.foodFragment)
            2 -> findNavController().navigate(R.id.funFragment)
            3 -> findNavController().navigate(R.id.MoveFragment)
        }
    }
}