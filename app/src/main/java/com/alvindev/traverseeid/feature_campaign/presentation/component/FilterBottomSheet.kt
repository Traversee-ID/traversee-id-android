package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignStatusConstant

@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    selectedStatus: String? = null,
    onClose: () -> Unit = {},
    onApply: (isChecked: Boolean, status: String?) -> Unit = { _, _ -> }
) {
    val radioOptions = listOf("All Campaigns", "Coming Soon Campaigns", "Ongoing Campaigns", "Completed Campaigns")
    val (selectedOption, onOptionSelected) = when (selectedStatus) {
        CampaignStatusConstant.ALL_STATUS -> remember { mutableStateOf(radioOptions[0]) }
        CampaignStatusConstant.COMING_SOON -> remember { mutableStateOf(radioOptions[1]) }
        CampaignStatusConstant.ONGOING -> remember { mutableStateOf(radioOptions[2]) }
        CampaignStatusConstant.COMPLETED -> remember { mutableStateOf(radioOptions[3]) }
        else -> remember { mutableStateOf(radioOptions[0]) }
    }
    val isChecked = remember { mutableStateOf(false) }

//    val dropdownMenuItems = listOf("Indonesia", "Magelang", "Depok", "Semarang", "Purworkerto")
//    val selectedItem = remember { mutableStateOf(dropdownMenuItems[0]) }

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
        Text(
            text = stringResource(id = R.string.registration),
            style = Typography.h2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        SwitchOption(
            text = stringResource(id = R.string.not_registered),
            isChecked = isChecked.value,
            onCheckedChange = { isChecked.value = it }
        )

        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.status),
            style = Typography.h2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        radioOptions.forEach{ text ->
                RadioOption(
                    text = text,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected,
                )
        }

//        Text(
//            text = "Location",
//            style = Typography.h2,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//        TraverseeDropdown(
//            dropdownMenuItems = dropdownMenuItems,
//            selectedItem = selectedItem.value,
//            onSelectedItemChange = { selectedItem.value = it },
//            modifier = Modifier
//                .fillMaxWidth()
//        )

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(id = R.string.apply),
            onClick = {
                val status = when(selectedOption) {
                    radioOptions[0] -> CampaignStatusConstant.ALL_STATUS
                    radioOptions[1] -> CampaignStatusConstant.COMING_SOON
                    radioOptions[2] -> CampaignStatusConstant.ONGOING
                    radioOptions[3] -> CampaignStatusConstant.COMPLETED
                    else -> CampaignStatusConstant.ALL_STATUS
                }
                onApply(isChecked.value,status)
            }
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