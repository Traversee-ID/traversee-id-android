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
fun TraverseeOutlinedButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    text: String = "OutlinedButton",
    elevation: ButtonElevation? = null,
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colors.primary),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colors.primary,
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
    OutlinedButton(
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
fun MosavOutlinedButtonPreview() {
    TraverseeTheme {
        TraverseeOutlinedButton()
    }
}