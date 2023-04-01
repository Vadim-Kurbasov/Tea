package com.example.tea.presentation.ui.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.R
import com.example.tea.databinding.PlantItemBinding
import com.example.tea.data.models.Product
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object KorsObject {
    lateinit var korzList: ArrayList<ItemKorsina>
}

class PlantAdapter(val listener: Listener, val choisenFolder: String) : RecyclerView.Adapter<PlantAdapter.PlantHolder>() {

    // Если хотим сорировать, то лист должен быть mutable
    val plantList = mutableListOf<Product>()

    class PlantHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = PlantItemBinding.bind(item)

        fun bind(product: Product, listener: Listener, choisenFolder: String) = with(binding){
            val pos = adapterPosition
            if(product.imageHref!="") {
                Picasso.get()
                    .load(product.imageHref)
                    .resize(130, 130)
                    .into(im, object : Callback {
                        override fun onSuccess() {
                                progressBar4.setVisibility(View.GONE)
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

            btShowMinus.isVisible = false
            btShowPlus.isVisible = false
            tvPriseShowSht.isVisible = false

            for(i in 0 until KorsObject.korzList.size){

                if(product.title == KorsObject.korzList[i].name){
                    btShowMinus.isVisible = true
                    btShowPlus.isVisible = true
                    tvPriseShowSht.isVisible = true
                    btShowAddKorz.isVisible = false
                    if(choisenFolder == "Чай" || choisenFolder == "Кофе") {
                        tvPriseShowSht.text = KorsObject.korzList[i].count.toString() + " гр"
                    }
                    else{
                      //  tvPriseShowSht.text = "1"
                        tvPriseShowSht.text = KorsObject.korzList[i].count.toString()
                    }
                }
            }

            itemView.setOnClickListener(){
                listener.onClick(product)
            }

            btShowAddKorz.setOnClickListener(){
                listener.onClickButtonAddKorzina(product)
                btShowMinus.isVisible = true
                btShowPlus.isVisible = true
                tvPriseShowSht.isVisible = true
                btShowAddKorz.isVisible = false
                if(choisenFolder == "Чай" || choisenFolder == "Кофе") {
                    tvPriseShowSht.text = "50 гр"
                }
                else{
                    tvPriseShowSht.text = "1"
                }
            }

             btShowPlus.setOnClickListener(){
                 val c = tvPriseShowSht.text.toString()
                 val r = c.replace(" ", "")
                 val re = r.replace("г", "")
                 val rep = re.replace("р", "")
                 val count = rep.toInt()
                 listener.setCountPlus(product, pos, count)

                 if(choisenFolder == "Чай" || choisenFolder == "Кофе") {
                     val a = 50
                     val b = count + a
                     tvPriseShowSht.text = b.toString() + " гр"
                 }
                 else{
                     val a = 1
                     val b = count + a
                     tvPriseShowSht.text = b.toString()
                 }
             }

             btShowMinus.setOnClickListener(){
                val c = tvPriseShowSht.text.toString()
                val r = c.replace(" ", "")
                val re = r.replace("г", "")
                val rep = re.replace("р", "")
                val count = rep.toInt()
                listener.setCountMinus(product, pos, count)

                if(choisenFolder == "Чай" || choisenFolder == "Кофе") {
                    if(count == 50 ){
                     btShowMinus.isVisible = false
                     btShowPlus.isVisible = false
                     tvPriseShowSht.isVisible = false
                     btShowAddKorz. isVisible = true
                    }
                    val a = 50
                    val b = count - a
                    tvPriseShowSht.text = b.toString() + " гр"
                }
                else{
                    if(count == 0 ){
                        btShowMinus.isVisible = false
                        btShowPlus.isVisible = false
                        tvPriseShowSht.isVisible = false
                        btShowAddKorz. isVisible = true
                    }
                    val a = 1
                    val b = count - a
                    tvPriseShowSht.text = b.toString()
                }
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

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list : List<Product>){
        plantList.clear()
        plantList.addAll(list)
        //Если хотим отсортировать
        plantList.sortBy { it.prise }
        notifyDataSetChanged()
    }

     fun setKorsObject(korsinsList: ArrayList<ItemKorsina>){
         KorsObject.korzList = korsinsList
     }

    interface Listener{
        fun onClick(product: Product){
        }
        fun onClickButtonAddKorzina(product: Product){
        }
        fun setCountPlus(product: Product, pos: Int, count: Int){
        }
        fun setCountMinus(product: Product, pos: Int, count: Int){
        }
    }
}



//    @SuppressLint("NotifyDataSetChanged")
//    fun a(){
//        notifyDataSetChanged()
//    }








