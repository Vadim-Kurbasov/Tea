package com.example.tea.data.storage

import com.example.tea.data.models.GetListProductToDisplayModel

interface GetListProductToDisplayInterface {
    fun getListProduct(getListProductToDisplayModel: GetListProductToDisplayModel)
}