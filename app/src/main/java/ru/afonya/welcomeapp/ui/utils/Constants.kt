package ru.afonya.welcomeapp.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri

object Constants {
    const val GENDER = "GENDER"
    const val GENDER_MEN = "m"
    const val GENDER_WOMEN = "w"
}

fun rawResUri(context: Context, resId: Int): Uri {
    return "android.resource://${context.packageName}/$resId".toUri()
}