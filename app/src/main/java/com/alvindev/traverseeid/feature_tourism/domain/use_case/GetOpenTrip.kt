package com.alvindev.traverseeid.feature_tourism.domain.use_case

import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository

class GetOpenTrip(
    private val repository: TourismRepository
) {
    operator fun invoke() = repository.getOpenTrip()
}