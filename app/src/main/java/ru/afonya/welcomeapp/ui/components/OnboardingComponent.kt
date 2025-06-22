package ru.afonya.welcomeapp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.afonya.welcomeapp.R
import ru.afonya.welcomeapp.ui.event.OnboardingScreenEvent
import ru.afonya.welcomeapp.ui.models.OnboardingScreenType
import ru.afonya.welcomeapp.ui.state.OnboardingScreenState
import ru.afonya.welcomeapp.ui.theme.BlackPrimary
import ru.afonya.welcomeapp.ui.utils.Constants
import ru.afonya.welcomeapp.ui.utils.LocalInnerPadding
import ru.afonya.welcomeapp.ui.utils.dropShadow

@Composable
fun OnboardingComponent(
    content: OnboardingScreenType,
    isCurrent: Boolean,
    state: OnboardingScreenState,
    onNextClick: () -> Unit,
    onEvent: (OnboardingScreenEvent) -> Unit,
) {
    val backgroundPainter = painterResource(R.drawable.background_onboarding)

    val bottomPaddingButton = when(content) {
        OnboardingScreenType.Gender -> 16.dp
        is OnboardingScreenType.Video -> 2.dp
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (content) {
            OnboardingScreenType.Gender -> {
                Image(
                    painter = backgroundPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is OnboardingScreenType.Video -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BlackPrimary)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (content) {
                OnboardingScreenType.Gender -> {
                    GenderSelection(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 16.dp)
                            .fillMaxSize(),
                        state.selectedGender
                    ) { gender ->
                        onEvent(OnboardingScreenEvent.SelectGender(gender))
                    }
                }

                is OnboardingScreenType.Video -> {
                    OnboardingVideo(
                        title = content.title,
                        videoUri = content.videoUrl,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        isVisible = isCurrent
                    )
                }
            }
            CommonButton(
                text = stringResource(R.string.next),
                onClick = onNextClick,
                enabled = state.selectedGender.isNotEmpty(),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = bottomPaddingButton)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(45.dp))
        }
    }
}

@Composable
fun GenderSelection(
    modifier: Modifier = Modifier,
    selectedGender: String,
    onChooseGender: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = LocalInnerPadding.current.calculateTopPadding() + 9.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonText(
                    stringResource(R.string.select_your_avatar_s_gender),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                CommonText(
                    stringResource(R.string.this_will_help_you_make_your_personalized_avatar),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GenderPhoto(
                    textLabel = stringResource(R.string.woman),
                    image = R.drawable.woman,
                    isLeft = true,
                    gender = Constants.GENDER_WOMEN,
                    selectedGender = selectedGender,
                    onChooseGender = onChooseGender
                )
                GenderPhoto(
                    textLabel = stringResource(R.string.man),
                    image = R.drawable.man,
                    isLeft = false,
                    gender = Constants.GENDER_MEN,
                    selectedGender = selectedGender,
                    onChooseGender = onChooseGender
                )
            }
        }
    }
}

@Composable
private fun RowScope.GenderPhoto(
    textLabel: String,
    image: Int,
    isLeft: Boolean,
    gender: String,
    selectedGender: String,
    onChooseGender: (String) -> Unit
) {
    var isSelected by remember(selectedGender) { mutableStateOf(selectedGender == gender) }

    val shape = if (isLeft)
        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
    else
        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()

    ) {
        // Вложенный Box с clip и background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .dropShadow(shape, Color(0xFF905CFF).copy(alpha = 0.8f), offsetX = 0.dp, offsetY = 1.dp, blur = 8.dp)
                .dropShadow(shape, Color(0xFF905CFF).copy(alpha = 0.2f), offsetX = 0.dp, offsetY = 1.dp, blur = 48.dp)
                .clip(shape)
                .background(Color.LightGray)
                .clickable {
                    if (isSelected) return@clickable
                    isSelected = !isSelected
                    onChooseGender(gender)
                }
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            GenderLabel(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                text = textLabel,
                gender,
                selectedGender
            )
        }
    }
}

@Composable
private fun GenderLabel(
    modifier: Modifier = Modifier,
    text: String,
    gender: String,
    selectedGender: String
) {
    val icon = if (selectedGender == gender) {
        R.drawable.checkbox_activated
    } else {
        R.drawable.checkbox_disabled
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x66000000))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Spacer(Modifier.width(16.dp))
            Icon(painterResource(icon), "", modifier = Modifier.padding(vertical = 6.dp), tint = Color.White)
            CommonText(text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun OnboardingVideo(
    title: String,
    videoUri: Uri,
    modifier: Modifier = Modifier,
    isVisible: Boolean,
) {
    Column(
        modifier = modifier
            .background(BlackPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val gradient = Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to Color.Transparent,
                1.0f to BlackPrimary
            )
        )

        Box(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth()
        ) {
            Player(
                contentUrl = videoUri,
                modifier = Modifier.matchParentSize(),
                foregroundGradient = gradient,
                isVisible = isVisible
            )
        }

        Box(
            modifier = Modifier
                .weight(0.25f)
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            CommonText(
                title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
    }
}


