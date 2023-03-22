package com.example.tea.domain.usecase

import android.content.Context
import android.widget.Toast

class ShowTostUseCase(private val contex: Context) {
    fun execute( text: String ){
        Toast.makeText(contex, "$text", Toast.LENGTH_SHORT).show()
    }
}