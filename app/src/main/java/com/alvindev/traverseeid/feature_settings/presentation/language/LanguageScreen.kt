package com.alvindev.traverseeid.feature_settings.presentation.language

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.TraverseeApplication
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.util.LocaleUtil
import com.alvindev.traverseeid.core.presentation.component.RadioOption
import com.alvindev.traverseeid.feature_settings.domain.mapper.LanguageMapper
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Language,
)
@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Log.d("LanguageScreen", "LanguageScreen: " + TraverseeApplication.LANGUAGE)
    val radioOptions = listOf("English", "Bahasa Indonesia")
    val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions.first {
                it == LanguageMapper.mapLocaleToLanguage(TraverseeApplication.LANGUAGE)
            }
        )
    }
    val state = viewModel.state

    if(state.isSuccess){
        navigator.popBackStack()
        TraverseeApplication.LANGUAGE = LanguageMapper.mapLanguageToLocale(selectedOption)
        LocaleUtil.setLocale(LocalContext.current, TraverseeApplication.LANGUAGE)
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
        radioOptions.forEachIndexed { index, text ->
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
            onClick = {
                val langId = LanguageMapper.mapLanguageToLocale(selectedOption)
                viewModel.saveLanguage(langId)
            },
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
    LanguageScreen(
        navigator = EmptyDestinationsNavigator
    )
}