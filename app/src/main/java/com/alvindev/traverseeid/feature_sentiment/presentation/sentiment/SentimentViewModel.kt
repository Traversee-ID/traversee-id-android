package com.alvindev.traverseeid.feature_sentiment.presentation.sentiment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.feature_sentiment.domain.use_case.UseCasesSentiment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentimentViewModel @Inject constructor(
    private val useCases: UseCasesSentiment
): ViewModel(){
    var state by mutableStateOf(SentimentState())
        private set

    fun onQueryChanged(query: String){
        state = state.copy(query = query)
    }

    fun onSubmitQuery() = viewModelScope.launch {
        state = state.copy(isLoading = true, queryResult = state.query)
        delay(3000)
        state = state.copy(isLoading = false)
    }
}