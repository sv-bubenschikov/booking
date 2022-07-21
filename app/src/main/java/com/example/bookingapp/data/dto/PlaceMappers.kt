package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.PlaceEntityForFB
import com.example.bookingapp.domain.entities.Place

fun Place.toFB(): PlaceEntityForFB =
    PlaceEntityForFB(
        id = this.id,
        companyId = this.companyId,
        name = this.name,
        type = this.type,
        floor = this.floor,
        numberParticipants = this.numberParticipants,
        periods = this.periods,
    )

fun PlaceEntityForFB.toDomain(): Place =
    Place(
        id = this.id,
        companyId = this.companyId,
        name = this.name,
        type = this.type,
        floor = this.floor,
        numberParticipants = this.numberParticipants,
        periods = this.periods,
    )
