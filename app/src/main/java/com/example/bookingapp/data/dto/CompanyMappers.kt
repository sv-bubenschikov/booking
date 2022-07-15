package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.CompanyEntityForFB
import com.example.bookingapp.domain.entities.Company

fun Company.toFB(): CompanyEntityForFB =
    CompanyEntityForFB(
        id = this.id,
        name = this.name,
        logoUrl = this.logoUrl
    )

fun CompanyEntityForFB.toDomain(): Company =
    Company(
        id = this.id,
        name = this.name,
        logoUrl = this.logoUrl
    )