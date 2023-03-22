package com.example.tea.presentation.ui.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.R
import com.example.tea.databinding.PlantItemBinding
import com.example.tea.data.models.Product
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PlantAdapter(val listener: Listener, val choisenFolder: String) : RecyclerView.Adapter<PlantAdapter.PlantHolder>() {

    // Если хотим сорировать, то лист должен быть mutable
    val plantList = mutableListOf<Product>()

    class PlantHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = PlantItemBinding.bind(item)

        fun bind(product: Product, listener: Listener, choisenFolder: String) = with(binding){
            if(product.imageHref!="") {
                Picasso.get()
                    .load(product.imageHref)
                    .resize(130, 130)
                    .into(im, object : Callback {
                        override fun onSuccess() {
                            if (progressBar4 != null) {
                                progressBar4.setVisibility(View.GONE)
                            }
                        }
                        override fun onError(e: Exception?) {
                            TODO("Not yet implemented")
                        }
                    })
                tvTitle.text = product.title
                if(choisenFolder == "Чай" || choisenFolder == "Кофе") {
                    tvPrise.text = product.prise.toString() + " руб за 100гр"
                }
                else{
                    tvPrise.text = product.prise.toString() + " р"
                }
            }
            itemView.setOnClickListener(){
                listener.onClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PlantHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item,parent,false)
        return PlantHolder(view)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        holder.bind(plantList[position],listener, choisenFolder)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPlant(product: Product){
        plantList.add(product)
        //  plantList.sortedWith(compareBy { it.title.length})
        notifyDataSetChanged()
    }

    //  В случае отображения готового массива
    fun addAll(list : List<Product>){
        plantList.clear()
        plantList.addAll(list)
        //Если хотим отсортировать
        plantList.sortBy { it.prise }
    }

    interface Listener{
        fun onClick(product: Product){
        }
    }
}






