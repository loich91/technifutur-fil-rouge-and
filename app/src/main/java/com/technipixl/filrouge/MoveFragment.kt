package com.technipixl.filrouge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technipixl.filrouge.databinding.FragmentMoveBinding


class MoveFragment : Fragment() {
    private lateinit var binding: FragmentMoveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoveBinding.inflate(layoutInflater)
        return binding.root
    }


}