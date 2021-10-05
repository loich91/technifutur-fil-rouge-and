package com.technipixl.filrouge.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technipixl.filrouge.databinding.JokeCellBinding

private lateinit var binding:JokeCellBinding
class JokeAdpater (val jokelist:List<ChuckData>):RecyclerView.Adapter<JokeAdpater.JokeViewHolder>() {
    class JokeViewHolder(viewBinding: ViewBinding):RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(joke: ChuckData){
            binding.text1.text = joke.joke
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        binding = JokeCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(jokelist[position])
    }

    override fun getItemCount(): Int {
        return  jokelist.count()
    }
}