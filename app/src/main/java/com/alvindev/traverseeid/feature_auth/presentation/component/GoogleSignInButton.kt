package com.alvindev.traverseeid.feature_auth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String = stringResource(id = R.string.sign_in_with_google),
) {
    TraverseeButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black,
        ),
        border = ButtonDefaults.outlinedBorder,
        enabled = enabled,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
             Image(
                 painter = painterResource(id = R.drawable.ic_google),
                 contentDescription = null,
                 modifier = Modifier
                     .size(24.dp)
                     .padding(end = 8.dp)
             )
             Text(
                 text = text,
                 style = MaterialTheme.typography.button,
                 color = Color.Black,
             )
        }
    }
}