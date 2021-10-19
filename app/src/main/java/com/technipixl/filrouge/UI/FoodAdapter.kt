package com.technipixl.filrouge.UI

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.FoodCellBinding
import kotlin.math.round


private lateinit var binding:FoodCellBinding
class FoodAdapter(val foodList :List<Businesse> ,val listener:OnclickFoodListener):RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(){
    interface OnclickFoodListener {
        fun onclickFoodListener(businesse: Businesse)
    }

    class FoodViewHolder(binding:FoodCellBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(foodOne:Businesse,listener: OnclickFoodListener){

            binding.Restaurantname.text = foodOne.name
            binding.textType.text = foodOne.categories.firstOrNull()?.title
            binding.villeText.text = foodOne.location.city
            val distanceTransform = round((foodOne.distance /1000)*100)/100
            binding.distanceText.text =  distanceTransform.toString()+" km"
            if (foodOne.image_url.isNotEmpty()) {
                Picasso.get()
                    .load(foodOne.image_url)
                    .into(binding.imageRestaurant)
            }
            itemView.setOnClickListener {
                listener.onclickFoodListener(foodOne)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        binding = FoodCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position],listener )
    }

    override fun getItemCount(): Int {
        return foodList.count()
    }

}


