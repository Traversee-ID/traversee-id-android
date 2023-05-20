package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.TraverseeTheme

@Composable
fun TraverseeButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    text: String = "Button",
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Red,
        contentColor = Color.White,
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.SemiBold,
        )
    }
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        elevation = elevation,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun MosavButtonPreview() {
    TraverseeTheme() {
        TraverseeButton()
    }
}