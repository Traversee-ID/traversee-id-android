package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.theme.TraverseeBlack

@Composable
fun TraverseeRowIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    drawable: Int? = null,
    iconSize: Dp = 24.dp,
    iconPaddingEnd: Dp = 4.dp,
    iconTintColor: Color = MaterialTheme.colors.secondary,
    text: String,
    textColor: Color = TraverseeBlack,
    textStyle: TextStyle = Typography.caption,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        drawable?.let {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = iconPaddingEnd),
                painter = painterResource(id = drawable),
                contentDescription = text,
                tint = iconTintColor
            )
        }

        icon?.let {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = iconPaddingEnd),
                imageVector = icon,
                contentDescription = text,
                tint = iconTintColor
            )
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}