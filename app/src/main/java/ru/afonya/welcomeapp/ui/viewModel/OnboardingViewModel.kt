package ru.afonya.test.ui.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.flow.MutableStateFlow
import ru.afonya.test.R
import ru.afonya.test.event.OnboardingScreenEvent
import ru.afonya.test.ui.models.OnboardingScreenType
import ru.afonya.test.ui.state.OnboardingScreenState
import ru.afonya.test.ui.utils.Constants
import ru.afonya.test.ui.utils.rawResUri

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
                    rawResUri(context, R.raw.video)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.lots_of_templates_for_ideas),
                    rawResUri(context, R.raw.vid2)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video)
                ),
                OnboardingScreenType.Video(
                    context.getString(R.string.make_your_dreams_come_true),
                    rawResUri(context, R.raw.video)
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