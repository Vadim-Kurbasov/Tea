package com.example.tea.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
   val messageFromactivityToken : MutableLiveData<String> by lazy{
     MutableLiveData<String>()
   }
}

