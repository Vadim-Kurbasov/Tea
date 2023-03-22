package com.example.tea.domain.repository

import com.example.tea.domain.models.GetListProductToDisplayModelDomain

interface DomainGetProductInterface {
    fun getProductToDisplay(getListProductToDisplayModelDomain: GetListProductToDisplayModelDomain)
}