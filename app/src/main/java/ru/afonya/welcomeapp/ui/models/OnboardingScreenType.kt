package ru.afonya.welcomeapp.ui.models

import android.net.Uri

sealed class OnboardingScreenType {
    data object Gender : OnboardingScreenType()
    data class Video(val title: String, val videoUrl: Uri) : OnboardingScreenType()
}