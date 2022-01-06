package com.java.flightscheduler.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.java.flightscheduler.R

val regularFontFamily = FontFamily(Font(R.font.product_sans_regular))
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = regularFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)