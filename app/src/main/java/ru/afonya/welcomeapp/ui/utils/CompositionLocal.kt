package ru.afonya.test.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

data class Padding(
    val paddingValues: PaddingValues
)

val LocalInnerPadding = compositionLocalOf<PaddingValues> {
    error("InnerPadding not provided")
}

@Composable
fun ProvideInnerPadding(
    paddingValues: PaddingValues,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalInnerPadding provides paddingValues,
    ) {
        content()
    }
}