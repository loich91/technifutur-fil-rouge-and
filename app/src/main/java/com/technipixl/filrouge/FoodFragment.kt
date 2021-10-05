package com.technipixl.filrouge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.technipixl.filrouge.UI.FoodAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.technipixl.filrouge.UI.ConnexionYelpImpl
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodFragment : Fragment() {

    private lateinit var binding: FragmentFoodBinding
    private val conn by lazy { ConnexionYelpImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupyelpdata()
    }
    fun setupyelpdata(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = conn.getSearch( 50.6333,5.56667,20)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    response.body()?.let {
                        binding.recyclerView.adapter = FoodAdapter(it.businesses)
                    }
                }
            }
        }
    }
}