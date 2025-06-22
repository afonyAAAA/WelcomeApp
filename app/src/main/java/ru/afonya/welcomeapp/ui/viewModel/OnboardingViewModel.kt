package ru.afonya.welcomeapp.ui.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import ru.afonya.welcomeapp.R
import ru.afonya.welcomeapp.ui.event.OnboardingScreenEvent
import ru.afonya.welcomeapp.ui.models.OnboardingScreenType
import ru.afonya.welcomeapp.ui.state.OnboardingScreenState
import ru.afonya.welcomeapp.ui.utils.Constants
import ru.afonya.welcomeapp.ui.utils.rawResUri

class OnboardingViewModel(
    private val context: Context,
    private val pref: SharedPreferences
) : BaseViewModel<OnboardingScreenState, OnboardingScreenEvent>() {

    override val _state: MutableStateFlow<OnboardingScreenState> = MutableStateFlow(
        OnboardingScreenState(
            selectedGender = "",
            listOf(
                OnboardingScreenType.Gender,
                OnboardingScreenType.Video(
                    context.getString(R.string.try_all_the_processing_options),
                    rawResUri(context, R.raw.video1)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.lots_of_templates_for_ideas),
                    rawResUri(context, R.raw.video2)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video3)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video4)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video4)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video4)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video4)
                )
            )
        )
    )

    override fun onEvent(event: OnboardingScreenEvent) {
        when(event) {
            is OnboardingScreenEvent.SelectGender -> {
                setSelectedGender(event.gender)
                saveSelectedGender(event.gender)
            }

            OnboardingScreenEvent.Init -> getSavedGender()
        }
    }

    private fun saveSelectedGender(gender: String) {
        pref.edit {
            putString(Constants.GENDER, gender)
        }
    }

    private fun getSavedGender() {
        updateUiState {
            copy(
                selectedGender = pref.getString(Constants.GENDER, null) ?: ""
            )
        }
    }

    private fun setSelectedGender(gender: String) {
        updateUiState {
            copy(
                selectedGender = gender
            )
        }
    }
}