package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun RadioOption(
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            style = Typography.body2
        )
        RadioButton(
            selected = (text == selectedOption),
            onClick = {
                onOptionSelected(text)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioOptionPreview() {
    RadioOption(
        text = "All Campaigns",
        selectedOption = "All Campaigns",
        onOptionSelected = {}
    )
}