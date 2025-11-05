package com.egsi.labsi.sog.model

import java.util.Date

data class EggCode(
    val code: String,
    val category: EggCategory,
    val housingType: HousingType,
    val country: String,
    val producer: String,
    val expiryDate: Date?,
    val status: EggStatus
)

enum class EggCategory(val code: String, val displayName: String, val description: String) {
    C0("C0", "Category 0", "Premium quality - Highest category"),
    C1("C1", "Category 1", "First category - Excellent quality"),
    C2("C2", "Category 2", "Second category - Good quality"),
    C3("C3", "Category 3", "Third category - Standard quality")
}

enum class HousingType(val code: String, val displayName: String, val description: String) {
    ORGANIC("0", "Organic", "Free-range organic farming with natural feed"),
    FREE_RANGE("1", "Free-Range", "Hens have outdoor access"),
    BARN("2", "Barn", "Indoor housing with open space"),
    CAGE("3", "Cage", "Conventional cage system")
}

enum class EggStatus {
    FRESH,      // Green - Safe to consume
    EXPIRING,   // Yellow - Expiring soon
    EXPIRED     // Red - Past expiry date
}

data class CheckHistory(
    val id: String,
    val code: String,
    val eggCode: EggCode,
    val timestamp: Date
)

data class FavoriteProducer(
    val id: String,
    val producer: String,
    val country: String,
    val addedDate: Date
)

