package com.java.flightscheduler.utils

fun converterFactory(moshi: Moshi): Converter.Factory {
    return WrapperConverterFactory(
        CollectionFormatConverterFactory(),
        EnumToValueConverterFactory(),
        MoshiConverterFactory.create(moshi)
    )
}