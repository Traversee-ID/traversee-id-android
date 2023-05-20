package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TraverseeDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    thickness: Dp = 1.dp,
    textModifier: Modifier = Modifier.padding(horizontal = 8.dp),
    text : String? = null,
    textSize : TextUnit = 12.sp,
    ) {
    if(text != null){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = color,
                thickness = thickness
            )
            Text(
                modifier = textModifier,
                text = text,
                color = color,
                fontSize = textSize,
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = color,
                thickness = thickness
            )
        }
    }else{
        Divider(
            modifier = modifier,
            color = color,
            thickness = thickness
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MosavDividerPreview() {
    TraverseeDivider(
        text = "OR"
    )
}