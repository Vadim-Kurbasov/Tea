package com.example.tea.data.storage

import com.example.tea.data.models.GreetingTextModel

interface GreetingTextInterface {
    fun  saveTextGreeting(greetingTextModel: GreetingTextModel)
    fun getTextGreeting(): GreetingTextModel
}