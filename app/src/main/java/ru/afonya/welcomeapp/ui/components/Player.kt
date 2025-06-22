package ru.afonya.welcomeapp.ui.components

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun Player(
    modifier: Modifier = Modifier,
    contentUrl: Uri,
    volume: Float = 0f,
    foregroundGradient: Brush? = null,
    isVisible: Boolean = false
) {
    val context = LocalContext.current
    val player = remember(isVisible) {
        if (isVisible) ExoPlayer.Builder(context).build() else null
    }
    val showOverlay = remember { mutableStateOf(true) }
    val lifecycleOwner = LocalLifecycleOwner.current

    val overlayAlpha by animateFloatAsState(
        targetValue = if (showOverlay.value) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "OverlayAlpha"
    )

    LaunchedEffect(contentUrl, isVisible) {
        player?.let {
            if (isVisible) {
                showOverlay.value = true
                it.setMediaItem(androidx.media3.common.MediaItem.fromUri(contentUrl))
                it.volume = volume
                it.prepare()
                it.playWhenReady = true

                while (!it.isPlaying) delay(50)

                showOverlay.value = false
            } else {
                it.pause()
                showOverlay.value = true
            }
        }
    }

    Box(modifier = modifier) {
        if (player != null) {
            PlayerSurface(
                player = player,
                surfaceType = SURFACE_TYPE_SURFACE_VIEW,
                modifier = Modifier.matchParentSize()
            )

            if (overlayAlpha > 0f) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = overlayAlpha))
                )
            }

            if (foregroundGradient != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(foregroundGradient)
                )
            }
        }
    }

    DisposableEffect(lifecycleOwner, player) {
        val observer = LifecycleEventObserver { _, event ->
            if (player != null) {
                when (event) {
                    Lifecycle.Event.ON_STOP,
                    Lifecycle.Event.ON_PAUSE -> {
                        player.pause()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        if (isVisible) {
                            if (player.playbackState == ExoPlayer.STATE_ENDED) player.seekTo(0)
                            player.playWhenReady = true
                        }
                    }
                    else -> Unit
                }
            }
        }

        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
            player?.release()
        }
    }
}
