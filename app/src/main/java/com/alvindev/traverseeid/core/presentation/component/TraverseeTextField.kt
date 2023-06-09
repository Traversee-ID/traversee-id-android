package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvindev.traverseeid.core.theme.TraverseeBlack100
import com.alvindev.traverseeid.core.theme.TraverseeBlack400
import com.alvindev.traverseeid.core.theme.TraverseeTheme

@Composable
fun TraverseeTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = TraverseeBlack100,
        cursorColor = Color.Black,
        disabledLabelColor = Color.Gray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    ),
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    minLines: Int = 1,
    maxLines: Int = 1,
    isError: Boolean = false,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        modifier = modifier,
        colors = if(enabled.not()) TextFieldDefaults.textFieldColors(
            backgroundColor = TraverseeBlack400,
            cursorColor = TraverseeBlack400,
            disabledLabelColor = TraverseeBlack400,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ) else colors,
        shape = shape,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        minLines = minLines,
        maxLines = maxLines,
        isError = isError,
        enabled = enabled,
        keyboardActions = keyboardActions,
    )
}

@Preview(showBackground = true)
@Composable
fun MosavTextFieldPreview() {
    TraverseeTheme() {
        TraverseeTextField(
            value = "Value",
            onValueChange = {},
            placeholder = {},
            label = {},
            isError = true,
        )
    }
}