package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.RadioOption
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun SortBottomSheet(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {}
) {
    val radioOptions = listOf("All Campaigns", "Coming Soon Campaigns", "Ongoing Campaigns", "Completed Campaigns")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }

    Column(
        modifier = modifier
    ) {
        TraverseeRowIcon(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClose() }
                .padding(bottom = 8.dp),
            icon = Icons.Default.Close,
            text = "Sort",
            textStyle = Typography.h2,
        )

        radioOptions.forEachIndexed{ index, text ->
            Column {
                RadioOption(
                    text = text,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )

                if (index != radioOptions.size - 1) {
                    TraverseeDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = "Apply",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SortBottomSheetPreview() {
    TraverseeTheme() {
        SortBottomSheet(
            modifier = Modifier.padding(16.dp)
        )
    }
}