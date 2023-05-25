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
    modifier: Modifier = Modifier,
    disabled: Boolean = true,
    onSubmit: () -> Unit = {},
    actionText: String = "",
    label: String = "",
    placeholder: String = "",
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeTextField(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 120.dp),
            label = {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = label,
                )
            },
            placeholder = {
                Text(placeholder)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            singleLine = false,
        )

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = actionText,
            onClick = onSubmit,
            enabled = disabled
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForumCommentPreview(){
    TraverseeTheme {
        ForumTextField(
            modifier = Modifier
                .shadow(4.dp)
                .clip(Shapes.large)
                .fillMaxWidth()
                .background(color = Color.White, shape = Shapes.large)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}