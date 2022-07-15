package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.PlaceEntityForFB
import com.example.bookingapp.domain.entities.Place

fun Place.toFB(): PlaceEntityForFB =
    PlaceEntityForFB(
        id = this.id,
        name = this.name,
        type = this.type,
        floor = this.floor,
        numberParticipants = this.numberParticipants,
        attributes = this.attributes
    )

fun PlaceEntityForFB.toDomain(): Place =
    Place(
        id = this.id,
        name = this.name,
        type = this.type,
        floor = this.floor,
        numberParticipants = this.numberParticipants,
        attributes = this.attributes
    )
