package com.alvindev.traverseeid.core.presentation.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.Shapes

@Composable
fun TraverseeDropdown(
    modifier: Modifier = Modifier,
    dropdownMenuItems: List<String>,
    selectedItem: String,
    onDismissRequest: () -> Unit = { },
    onSelectedItemChange: (String) -> Unit = { },
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black.copy(alpha = 0.2f), shape = Shapes.small)
                .padding(8.dp)
                .clickable {
                    expanded = true
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = selectedItem)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            expanded = expanded,
            onDismissRequest = {
                onDismissRequest()
                expanded = false
            },
        ) {
            dropdownMenuItems.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedItemChange(text)
                        expanded = false
                    },
                ) {
                    Text(text = text)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTraverseeDropdown() {
    TraverseeDropdown(
        dropdownMenuItems = listOf("Indonesia", "Magelang", "Depok", "Semarang", "Purworkerto"),
        selectedItem = "Indonesia",
        )
}