package com.technipixl.filrouge.UI

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technipixl.filrouge.databinding.JokeCellBinding

import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import com.technipixl.filrouge.HomeActivity
import com.technipixl.filrouge.R
import java.util.*


private lateinit var binding:JokeCellBinding
private var ttsInitializationStatus = TextToSpeech.STOPPED
private var colorRandom = mutableListOf(
    R.color.green_gradient_start,
    R.color.green_gradient_end,
    R.color.dark_blue,
    R.color.medium_blue,
    R.color.black,
)
class JokeAdpater (val jokelist:List<ChuckData>):RecyclerView.Adapter<JokeAdpater.JokeViewHolder>() {
    class JokeViewHolder(viewBinding: ViewBinding,@ColorInt val textColor: Int):RecyclerView.ViewHolder(viewBinding.root),TextToSpeech.OnInitListener {
        fun bind(joke: ChuckData){
            val tts = TextToSpeech(binding.root.context, this)
            binding.text1.setTextColor(textColor)
            binding.text1.text = joke.joke
            binding.imageButtonShare.setOnClickListener {
            sharefun(joke.joke)
            }
            binding.imageButton2.setOnClickListener {
                val text = joke.joke
                if(ttsInitializationStatus==TextToSpeech.SUCCESS) {
                    tts.language = Locale.FRANCE
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
        val colorRessource = colorRandom.random()
        val color = binding.root.resources.getColor(colorRessource,null)
        return JokeViewHolder(binding,color)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(jokelist[position])
    }

    override fun getItemCount(): Int {
        return  jokelist.count()
    }
}