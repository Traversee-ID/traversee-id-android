package com.alvindev.traverseeid.feature_tourism.domain.use_case

import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository

class GetTourismById(
    private val repository: TourismRepository
) {
    suspend operator fun invoke(id: String) = repository.getTourismById(id)
}