package com.alvindev.traverseeid.feature_tourism.domain.use_case

import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository

class GetTourisms(
    private val repository: TourismRepository
) {
    operator fun invoke(params: TourismParams) = repository.getTourisms(params)
}