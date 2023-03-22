package com.example.tea.presentation.ui.MainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tea.data.models.Product

class TokenViewModel: ViewModel() {

      val messageFromactivityToken : MutableLiveData<String> by lazy{
          MutableLiveData<String>()
      }

    val productLiveData : MutableLiveData<Product> by lazy{
        MutableLiveData<Product>()
    }
}
