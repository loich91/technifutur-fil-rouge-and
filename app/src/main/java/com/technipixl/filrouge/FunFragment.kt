package com.technipixl.filrouge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.technipixl.filrouge.UI.JokeAdpater
import com.technipixl.filrouge.databinding.FragmentFunBinding
import com.technipixl.filrouge.UI.ConnexionChuckImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FunFragment : Fragment() {
    private lateinit var binding: FragmentFunBinding
    private val service by lazy { ConnexionChuckImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFunBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDate()
    }
    fun setupDate(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getJoke()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response?.let {
                        it.body()?.let {

                            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                            binding.recyclerView.adapter = JokeAdpater(it.value)
                        }
                    }
                }
            }
        }
    }
}