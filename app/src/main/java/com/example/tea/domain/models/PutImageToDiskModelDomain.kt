package com.example.tea.domain.models

import android.net.Uri

data class PutImageToDiskModelDomain(val uri: Uri, val href: String, val contentResolver: android.content.ContentResolver)
