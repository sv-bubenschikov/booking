package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlacesAndFeaturesByCompanyNameUseCase @Inject constructor(private val placeRepository: PlaceRepository) {
    operator fun invoke(companyId: String) : Flow<Pair<List<Place>, List<String>>> {
        return placeRepository.getPlacesInfoByCompanyName(companyId).map { places ->
            places to places.flatMap { place -> place.features }.toSet().toList()
        }
    }
}