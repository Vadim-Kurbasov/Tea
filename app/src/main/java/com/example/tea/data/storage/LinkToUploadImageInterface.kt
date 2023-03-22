package com.example.tea.data.storage

import com.example.tea.data.models.HrefModel
import com.example.tea.data.models.LinkToUploadImageModel

interface LinkToUploadImageInterface {
    fun getLinkTiUploadImage(linkToUploadImageModel: LinkToUploadImageModel): HrefModel
}