package com.example.tea.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tea.data.models.Product
import com.example.tea.domain.models.GetListProductToDisplayModelDomain
import com.example.tea.domain.usecase.GetListProductUseCaseUseCase
import com.example.tea.presentation.ui.Adapters.PlantAdapter

class KofeViewModel(token: String, getListProductUseCaseUseCase: GetListProductUseCaseUseCase): ViewModel(){
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

}
