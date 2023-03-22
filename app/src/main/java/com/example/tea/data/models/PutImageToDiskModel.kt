package com.example.tea.data.models

import android.net.Uri

data class PutImageToDiskModel(val uri: Uri, val href: String, val contentResolver: android.content.ContentResolver )
