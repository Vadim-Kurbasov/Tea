package com.example.tea.data.repository

import com.example.tea.data.storage.GetListProductToDisplayInterface
import com.example.tea.data.models.GetListProductToDisplayModel
import com.example.tea.domain.models.GetListProductToDisplayModelDomain
import com.example.tea.domain.repository.DomainGetProductInterface

class GetProductRepository(private val getListProductToDisplayInterface: GetListProductToDisplayInterface): DomainGetProductInterface {

          override fun getProductToDisplay(getListProductToDisplayModelDomain: GetListProductToDisplayModelDomain) {
              val getListProductToDisplayModel = GetListProductToDisplayModel(token = getListProductToDisplayModelDomain.token, choisenFolder = getListProductToDisplayModelDomain.choisenFolder)
             getListProductToDisplayInterface.getListProduct(getListProductToDisplayModel)
         }
}