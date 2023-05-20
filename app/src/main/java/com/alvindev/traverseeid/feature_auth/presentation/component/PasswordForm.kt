package com.alvindev.traverseeid.feature_auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField


@Composable
fun PasswordForm(
    modifier : Modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
    value : String,
    onValueChange : (String) -> Unit,
    isPasswordVisible : Boolean,
    onPasswordVisibilityChange : () -> Unit,
    labelText : String = "Password",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
){
    TraverseeTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        label = {
            Text(labelText)
        },
        placeholder = {
            Text(labelText)
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            val image = if (isPasswordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description =
                if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(imageVector = image, description)
            }
        },
        singleLine = true,
    )
}