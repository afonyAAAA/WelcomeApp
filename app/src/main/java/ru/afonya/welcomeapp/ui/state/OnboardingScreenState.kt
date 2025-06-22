package ru.afonya.test.ui.state

import ru.afonya.test.ui.models.OnboardingScreenType

data class OnboardingScreenState(
    val selectedGender: String = "",
    val items: List<OnboardingScreenType>
)
