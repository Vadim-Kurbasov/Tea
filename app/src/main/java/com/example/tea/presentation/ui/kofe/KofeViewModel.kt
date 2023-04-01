package com.example.tea.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.data.models.Product
import com.example.tea.domain.models.GetListProductToDisplayModelDomain
import com.example.tea.domain.usecase.GetListProductUseCaseUseCase
import com.example.tea.domain.usecase.WorkWithKorzinaUseCase
import com.example.tea.presentation.ui.Adapters.PlantAdapter
import com.example.tea.presentation.ui.MainActivity.TokenViewModel
import com.example.tea.presentation.ui.showproductdialog.ItemKorsina

class KofeViewModel(token: String,
                    getListProductUseCaseUseCase: GetListProductUseCaseUseCase,
                    val workWithKorzinaUseCase: WorkWithKorzinaUseCase,
                    val tokenViewModel: TokenViewModel
): ViewModel(){

    val plantList = mutableListOf<Product>()
    var progressBarVisibility = MutableLiveData<Boolean>()
    var recyclerLiveData = MutableLiveData<RecyclerView>()

    lateinit var recyclerView1: RecyclerView
    lateinit var adapter1: PlantAdapter
    init {
        progressBarVisibility.value = true
        val getListProductToDisplayModelDomain = GetListProductToDisplayModelDomain(token, "Кофе")
        getListProductUseCaseUseCase.execute(getListProductToDisplayModelDomain)
    }

    fun initAdapter(recyclerView: RecyclerView, adapter: PlantAdapter){
        recyclerView1 = recyclerView
        adapter1 = adapter
    }

    fun showPlant( product: Product){
        plantList.add(product)
            progressBarVisibility.value = false
            recyclerLiveData.value = recyclerView1
            recyclerLiveData.value!!.adapter = adapter1
            adapter1.addAll(plantList)
    }

    fun reloadPlantAdapter(){
        recyclerLiveData.value = recyclerView1
        recyclerLiveData.value!!.adapter = adapter1
        adapter1.setKorsObject(workWithKorzinaUseCase.getBasket().korsinaList)
        adapter1.addAll(plantList)
    }

    fun putItemVkorsinu(product: Product){
        val itemKorsina = ItemKorsina(product.title, 50, product.prise, product.prise / 2, "Кофе","")
        val korsinaDomainModel = workWithKorzinaUseCase.getBasket()
        val count = korsinaDomainModel.korsinaList.size + 1
        workWithKorzinaUseCase.putitemBasket(itemKorsina)
        tokenViewModel.countAddKorsinaLiveData.value = count
    }

    fun setCount(count: Int, plusOrMinus: String, product: Product){
        val korsinaDomainModel = workWithKorzinaUseCase.getBasket()
        adapter1.setKorsObject(korsinaDomainModel.korsinaList)
        var itemKorsina = ItemKorsina(product.title, count, product.prise, 0 , "Кофе","")
        var position = 0
        for(i in 0 until korsinaDomainModel.korsinaList.size){
            if(korsinaDomainModel.korsinaList[i].name == itemKorsina.name){
                position = i
            }
        }
        itemKorsina = ItemKorsina(product.title, count, product.prise,korsinaDomainModel.korsinaList[position].itog , "Кофе","")
        val newItemKorsina = workWithKorzinaUseCase.setShtuk(itemKorsina, plusOrMinus)
        korsinaDomainModel.korsinaList[position] = newItemKorsina
        workWithKorzinaUseCase.saveBasket(korsinaDomainModel)
    }

    fun deleteItemKorsina(product: Product, count: Int){
        val korsinaDomainModel = workWithKorzinaUseCase.getBasket()
        val itemKorsina = ItemKorsina(product.title, count, product.prise, 0 , "Кофе","")
        var position = 0
        for(i in 0 until korsinaDomainModel.korsinaList.size){
            if(korsinaDomainModel.korsinaList[i].name == itemKorsina.name){
                position = i
            }
        }
        workWithKorzinaUseCase.deleteItemBasket(position)
        val c = korsinaDomainModel.korsinaList.size - 1
        tokenViewModel.countDelKorsinaLiveData.value = c
    }
    fun setKorzObj(){
        adapter1.setKorsObject(workWithKorzinaUseCase.getBasket().korsinaList)
    }
}
