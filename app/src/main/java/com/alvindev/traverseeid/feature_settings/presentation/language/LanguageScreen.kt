package com.alvindev.traverseeid.feature_settings.presentation.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.feature_campaign.presentation.component.RadioOption
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.Language,
)
@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val radioOptions = listOf("English", "Bahasa Indonesia")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val state = viewModel.state

    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
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
                .padding(top = 24.dp, bottom = 8.dp),
            onClick = {},
            enabled = state.isSubmitting.not(),
            text = state.isSubmitting.not().let {
                if (it) "Save" else "Saving..."
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen()
}