package com.example.tea.presentation.ui.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.R
import com.example.tea.databinding.KorsinaItemBinding
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina

class KorsinaAdapter (val listener: KorsinaAdapter.Listener) : RecyclerView.Adapter<KorsinaAdapter.KorsinaHolder>(){

    var korsinaList = ArrayList<ItemKorsina>()

    class KorsinaHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = KorsinaItemBinding.bind(item)

        fun bind(korsina : ItemKorsina, listener: KorsinaAdapter.Listener, korsinaList:ArrayList<ItemKorsina>) = with(binding){
            val pos = adapterPosition
            tvNameKor.text = korsina.name
            if(korsina.number.trim() == "Чай"|| korsina.number.trim() == "Кофе"){
                tvPriseKor.text = "Цена: " + korsina.cena.toString() + " р за 100 гр"
                tvKorItog.text = "Сумма: " + korsina.itog.toString()+" р"
                tvPriseSht.text = korsina.count.toString() + " гр"
            }
            else{
                tvPriseKor.text = "Цена: " + korsina.cena.toString()+" р"
                tvKorItog.text = "Сумма: " + korsina.itog.toString()+" р"
                tvPriseSht.text = korsina.count.toString() + " шт"
            }

            buttonKorPlus.setOnClickListener(){
                listener.onClickButtonKorPlus(korsina, it, pos, binding)
            }
            buttonKorMinus.setOnClickListener(){
                listener.onClickButtonKorMinus(korsina, it, pos, binding)
            }
            buttonKorDelete.setOnClickListener(){
                listener.onClickButtonDelete(korsina, it, pos, binding, korsinaList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : KorsinaAdapter.KorsinaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.korsina_item,parent,false)
        return KorsinaAdapter.KorsinaHolder(view)
    }

    override fun onBindViewHolder(holder: KorsinaAdapter.KorsinaHolder, position: Int) {
        holder.bind(korsinaList[position],listener, korsinaList)
    }

    override fun getItemCount(): Int {
        return korsinaList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addKorsina(korsina: ItemKorsina){
        korsinaList.add(korsina)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun replace(itemKorsina: ItemKorsina, pos: Int){
        korsinaList[pos] = itemKorsina
        notifyDataSetChanged()
    }

    //  В случае отображения готового массива

    fun addAll(list : List<ItemKorsina>){
        korsinaList.clear()
        korsinaList.addAll(list)
      //  notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
     fun removeItem(pos: Int){
         korsinaList.removeAt(pos)
         notifyDataSetChanged()
     }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAll(){
        korsinaList.clear()
        notifyDataSetChanged()
    }

    // Интерфейс чтобы работать с элементами recyclerView из FragmentDialogKorsina
    interface Listener{
        fun onClickButtonKorPlus(korsina: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding){
        }
        fun onClickButtonKorMinus(korsina: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding){
        }
        fun onClickButtonDelete(korsina: ItemKorsina, it: View, pos: Int, bind: KorsinaItemBinding, korsinaList: ArrayList<ItemKorsina>){
        }
    }
}

