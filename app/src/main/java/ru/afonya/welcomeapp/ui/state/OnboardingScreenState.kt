package ru.afonya.welcomeapp.ui.state

import ru.afonya.welcomeapp.ui.models.OnboardingScreenType

data class OnboardingScreenState(
    val selectedGender: String = "",
    val items: List<OnboardingScreenType>
)
