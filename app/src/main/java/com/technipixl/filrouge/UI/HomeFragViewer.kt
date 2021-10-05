package com.technipixl.filrouge.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.HomeViewerBinding

class HomeFragViewer:Fragment() {
    interface HomeFragClickListener{
        fun homeFragClickListener(menuIndex : Int)
    }
    private lateinit var binding:HomeViewerBinding
    private lateinit var listener: HomeFragClickListener
    private var image: Int? = null
    private var title: String? = null
    private var menuIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getInt(ARG_IMAGE)
            title = it.getString(ARG_TITLE)
            menuIndex = it.getInt(ARG_MENU_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeViewerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image?.let {
            view.findViewById<ImageView>(R.id.imageCardView).setImageResource(it)
        }
        title?.let {
            view.findViewById<TextView>(R.id.text).text = it
        }

        view.setOnClickListener {
            listener.homeFragClickListener(menuIndex ?: 0)
        }
    }
    companion object{
        private const val ARG_IMAGE = "image"
        private const val ARG_TITLE = "title"
        private const val ARG_MENU_INDEX = "index"
        @JvmStatic
        fun newInstance(image: Int, title: String, menuIndex: Int, listener: HomeFragClickListener) =
            HomeFragViewer().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE, image)
                    putString(ARG_TITLE, title)
                    putInt(ARG_MENU_INDEX, menuIndex)
                }
            }
    }
}