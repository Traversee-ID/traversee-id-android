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
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignRegistrationConstant
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignStatusConstant
import com.alvindev.traverseeid.core.domain.entity.LocationEntity

@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    selectedStatus: String? = null,
    selectedRegistartion: String? = null,
    onClose: () -> Unit = {},
    onApply: (isRegistered: Boolean?, status: String?, locationId: Int?) -> Unit = { _, _, _ -> },
    campaignLocations: List<LocationEntity>,
    locationSelected: Int? = null,
) {
    val radioStatusOptions =
        listOf(
            stringResource(id = R.string.all_campaigns),
            stringResource(id = R.string.coming_soon_campaigns),
            stringResource(id = R.string.ongoing_campaigns),
            stringResource(id = R.string.completed_campaigns)
        )
    val (selectedStatsOption, onOptionStatusSelected) = when (selectedStatus) {
        CampaignStatusConstant.ALL_STATUS -> remember { mutableStateOf(radioStatusOptions[0]) }
        CampaignStatusConstant.COMING_SOON -> remember { mutableStateOf(radioStatusOptions[1]) }
        CampaignStatusConstant.ONGOING -> remember { mutableStateOf(radioStatusOptions[2]) }
        CampaignStatusConstant.COMPLETED -> remember { mutableStateOf(radioStatusOptions[3]) }
        else -> remember { mutableStateOf(radioStatusOptions[0]) }
    }

    val radioRegistrationOptions = listOf(
        stringResource(id = R.string.all_campaigns),
        stringResource(id = R.string.registered_only),
        stringResource(id = R.string.unregistered_only)
    )
    val (selectedRegistrationOption, onOptionRegistrationSelected) = when (selectedRegistartion) {
        CampaignRegistrationConstant.ALL -> remember { mutableStateOf(radioRegistrationOptions[0]) }
        CampaignRegistrationConstant.REGISTERED -> remember { mutableStateOf(radioRegistrationOptions[1]) }
        CampaignRegistrationConstant.UNREGISTERED -> remember { mutableStateOf(radioRegistrationOptions[2]) }
        else -> remember { mutableStateOf(radioRegistrationOptions[0]) }
    }


    val selectedItem = remember {
        mutableStateOf(
            if (locationSelected != null) {
                campaignLocations.find { it.id == locationSelected }?.name
                    ?: campaignLocations[0].name
            } else {
                campaignLocations[0].name
            }
        )
    }

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
        radioRegistrationOptions.forEach { text ->
            RadioOption(
                text = text,
                selectedOption = selectedRegistrationOption,
                onOptionSelected = onOptionRegistrationSelected,
            )
        }

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
        radioStatusOptions.forEach { text ->
            RadioOption(
                text = text,
                selectedOption = selectedStatsOption,
                onOptionSelected = onOptionStatusSelected,
            )
        }

        Text(
            text = stringResource(id = R.string.location),
            style = Typography.h2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        TraverseeDropdown(
            dropdownMenuItems = campaignLocations.map { it.name },
            selectedItem = selectedItem.value,
            onSelectedItemChange = { selectedItem.value = it },
            modifier = Modifier
                .fillMaxWidth()
        )

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(id = R.string.apply),
            onClick = {
                val status = when (selectedStatsOption) {
                    radioStatusOptions[0] -> CampaignStatusConstant.ALL_STATUS
                    radioStatusOptions[1] -> CampaignStatusConstant.COMING_SOON
                    radioStatusOptions[2] -> CampaignStatusConstant.ONGOING
                    radioStatusOptions[3] -> CampaignStatusConstant.COMPLETED
                    else -> CampaignStatusConstant.ALL_STATUS
                }
                val location = campaignLocations.find { it.name == selectedItem.value }
                val registration = when (selectedRegistrationOption) {
                    radioRegistrationOptions[0] -> null
                    radioRegistrationOptions[1] -> true
                    radioRegistrationOptions[2] -> false
                    else -> null
                }

                onApply(registration, status, location?.id)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBottomSheetPreview() {
    TraverseeTheme() {
        FilterBottomSheet(
            modifier = Modifier.padding(16.dp),
            onClose = {},
            onApply = { _, _, _ -> },
            campaignLocations = listOf(),
            locationSelected = 0
        )
    }
}