package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val dropdownMenuItems = listOf("Indonesia", "Magelang", "Depok", "Semarang", "Purworkerto")
    val selectedItem = remember { mutableStateOf(dropdownMenuItems[0]) }
    val isChecked = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        TraverseeRowIcon(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClose() }
                .padding(bottom = 8.dp),
            icon = Icons.Default.Close,
            text = "Filter",
            textStyle = Typography.h2,
        )

        SwitchOption(
            text = "Not Registered",
            isChecked = isChecked.value,
            onCheckedChange = { isChecked.value = it }
        )

        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = "Location",
            style = Typography.h2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            dropdownMenuItems.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem.value = text
                        expanded = false
                    },
                ) {
                    Text(text = text)
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
fun FilterBottomSheetPreview() {
    TraverseeTheme() {
        FilterBottomSheet(
            modifier = Modifier.padding(16.dp)
        )
    }
}