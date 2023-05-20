package com.alvindev.traverseeid.feature_auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField

@Composable
fun EmailForm(
    modifier : Modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
    value : String,
    onValueChange : (String) -> Unit,
){
    TraverseeTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        },
        label = {
            Text("Email")
        },
        placeholder = {
            Text("Email")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
    )
}