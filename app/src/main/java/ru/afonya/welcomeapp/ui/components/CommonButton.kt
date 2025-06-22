package ru.afonya.test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.afonya.test.R
import ru.afonya.test.ui.theme.TestAppTheme


@Composable
fun CommonButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {

    val gradient = if (enabled) Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to Color(0xFF9EA3FF),
            0.95f to Color(0xFF4E26FF)
        )
    ) else Brush.horizontalGradient(
        listOf(
            Color(0xFF252525).copy(alpha = 0.82f),
            Color(0xFF252525).copy(alpha = 0.82f)
        )
    )

    val shape = RoundedCornerShape(12.dp)

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        modifier = modifier,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        elevation = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient, shape)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            CommonText(
                text,
                color = if (enabled) Color.White else Color.White.copy(alpha = 0.28f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewButton() {
    TestAppTheme {
        CommonButton(stringResource(R.string.next), true) { }
    }
}