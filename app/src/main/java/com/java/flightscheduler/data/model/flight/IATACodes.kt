package com.java.flightscheduler.data.model.flight

data class IATACodes constructor(
    val IDENT: String,
    val TYPE: String,
    val IATA: String?,
    val NAME: String?,
    val ELEVATION_FT : String?,
    val ISO_COUNTRY: String?,
    val ISO_REGION: String?,
    val MUNICIPALITY: String?,
    val GPS_CODE : String?,
    val IATA_CODE : String?,
    val LOCAL_CODE: String?,
    val COORDINATES : String?
)