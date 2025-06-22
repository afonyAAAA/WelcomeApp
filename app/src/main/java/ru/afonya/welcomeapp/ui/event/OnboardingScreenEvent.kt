package ru.afonya.test.event

sealed class OnboardingScreenEvent {
    data class SelectGender(val gender: String) : OnboardingScreenEvent()
    data object Init: OnboardingScreenEvent()
}