package ru.afonya.test.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.afonya.test.event.OnboardingScreenEvent
import ru.afonya.test.ui.components.OnboardingComponent
import ru.afonya.test.ui.state.OnboardingScreenState
import ru.afonya.test.ui.viewModel.OnboardingViewModel

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel = koinViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val onEvent: (OnboardingScreenEvent) -> Unit = {
        viewModel.onEvent(it)
    }

    LaunchedEffect(Unit) {
        onEvent(OnboardingScreenEvent.Init)
    }

    OnboardingPager(uiState, onEvent)
}

@Composable
fun OnboardingPager(state: OnboardingScreenState, onEvent: (OnboardingScreenEvent) -> Unit) {
    val items = state.items
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { items.size }

    var rowIndicatorHeightPx by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val item = items.getOrNull(page)
            item?.let {
                OnboardingComponent(
                    content = it,
                    onNextClick = {
                        scope.launch { pagerState.animateScrollToPage(page + 1) }
                    },
                    rowIndicatorHeightPx = rowIndicatorHeightPx,
                    isCurrent = pagerState.currentPage == page,
                    state = state,
                    onEvent = onEvent
                )
            }
        }

        PagerIndicator(
            pagerState = pagerState,
            onGetHeightIndicator = { rowIndicatorHeightPx = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
fun PagerIndicator(
    pagerState: PagerState,
    onGetHeightIndicator: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .onGloballyPositioned {
                onGetHeightIndicator(it.size.height)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val pageCount = pagerState.pageCount
        val currentPage = pagerState.currentPage

        repeat(pageCount) { index ->
            val isLast = index == pageCount - 1
            val baseSize = 8.dp

            val targetScale = when {
                index == currentPage -> 1f
                isLast && (currentPage >= pageCount - 3) -> 1f
                isLast -> 0.5f
                else -> 1f
            }

            val animatedScale by animateFloatAsState(
                targetValue = targetScale,
                animationSpec = tween(durationMillis = 300),
                label = "DotScale"
            )

            val color = if (index == currentPage) Color.White else Color.DarkGray

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(baseSize * animatedScale)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}