package com.technipixl.filrouge.UI

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technipixl.filrouge.databinding.JokeCellBinding

import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import com.technipixl.filrouge.HomeActivity
import java.util.*


private lateinit var binding:JokeCellBinding
private var ttsInitializationStatus = TextToSpeech.STOPPED
class JokeAdpater (val jokelist:List<ChuckData>):RecyclerView.Adapter<JokeAdpater.JokeViewHolder>() {
    class JokeViewHolder(viewBinding: ViewBinding):RecyclerView.ViewHolder(viewBinding.root),TextToSpeech.OnInitListener {
        fun bind(joke: ChuckData){
            val tts = TextToSpeech(binding.root.context, this)
            binding.text1.text = joke.joke
            binding.imageButtonShare.setOnClickListener {
            sharefun(joke.joke)
            }
            binding.imageButton2.setOnClickListener {
                val text = joke.joke
                if(ttsInitializationStatus==TextToSpeech.SUCCESS) {
                    tts.language = Locale.CHINESE
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
        fun sharefun(joke:String){
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "chuck joke")

            shareIntent.putExtra(Intent.EXTRA_TEXT, joke)
            startActivity(itemView.context,Intent.createChooser(shareIntent,"partager votre blague"),null)
        }

        override fun onInit(status: Int) {
            ttsInitializationStatus = status
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