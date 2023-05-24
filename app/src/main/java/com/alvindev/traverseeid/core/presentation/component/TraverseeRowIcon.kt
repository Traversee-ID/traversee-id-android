package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.Black
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun TraverseeRowIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = 24.dp,
    iconPaddingEnd: Dp = 4.dp,
    iconTintColor: Color = MaterialTheme.colors.primary,
    text: String,
    textColor: Color = Black,
    textStyle: TextStyle = Typography.caption,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .padding(end = iconPaddingEnd),
            imageVector = icon,
            contentDescription = "Participants",
            tint = iconTintColor
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}