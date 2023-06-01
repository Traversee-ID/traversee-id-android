package com.alvindev.traverseeid.feature_forum.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme

@Composable
fun ForumTextField(
    label: String = "",
    placeholder: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    TraverseeTextField(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 120.dp),
        label = {
            Text(
                text = label,
            )
        },
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = false,
        value = value,
        onValueChange = onValueChange,
    )
}

@Preview(showBackground = true)
@Composable
fun ForumCommentPreview() {
    TraverseeTheme {
        ForumTextField()
    }
}