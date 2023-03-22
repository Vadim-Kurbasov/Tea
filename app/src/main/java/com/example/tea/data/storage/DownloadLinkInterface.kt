package com.example.tea.data.storage

import com.example.tea.data.models.LinkToDownloadImageModel

interface DownloadLinkInterface {
    fun downLoadImage(linkToDownloadImageModel: LinkToDownloadImageModel)
}