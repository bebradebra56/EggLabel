package com.egsi.labsi.sog.data

import com.egsi.labsi.sog.model.*
import java.util.*

class EggCodeRepository {
    
    fun decodeEggCode(code: String): EggCode? {
        // Simple validation and parsing
        if (code.isBlank()) return null
        
        // Parse the code (simplified logic)
        val cleanCode = code.trim().uppercase()
        
        // Determine category
        val category = when {
            cleanCode.contains("C0") || cleanCode.contains("0") -> EggCategory.C0
            cleanCode.contains("C1") || cleanCode.contains("1") -> EggCategory.C1
            cleanCode.contains("C2") || cleanCode.contains("2") -> EggCategory.C2
            else -> EggCategory.C3
        }
        
        // Determine housing type (simplified - first digit)
        val housingType = when {
            cleanCode.startsWith("0") -> HousingType.ORGANIC
            cleanCode.startsWith("1") -> HousingType.FREE_RANGE
            cleanCode.startsWith("2") -> HousingType.BARN
            else -> HousingType.CAGE
        }
        
        // Mock country and producer data
        val country = getCountryFromCode(cleanCode)
        val producer = "Producer #${cleanCode.takeLast(4)}"
        
        // Mock expiry date (30 days from now)
        val expiryDate = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, (15..45).random())
        }.time
        
        val status = calculateStatus(expiryDate)
        
        return EggCode(
            code = cleanCode,
            category = category,
            housingType = housingType,
            country = country,
            producer = producer,
            expiryDate = expiryDate,
            status = status
        )
    }
    
    private fun calculateStatus(expiryDate: Date): EggStatus {
        val now = Date()
        val daysUntilExpiry = ((expiryDate.time - now.time) / (1000 * 60 * 60 * 24)).toInt()
        
        return when {
            daysUntilExpiry < 0 -> EggStatus.EXPIRED
            daysUntilExpiry <= 7 -> EggStatus.EXPIRING
            else -> EggStatus.FRESH
        }
    }
    
    private fun getCountryFromCode(code: String): String {
        return when {
            code.contains("US") || code.contains("USA") -> "United States"
            code.contains("UK") || code.contains("GB") -> "United Kingdom"
            code.contains("DE") -> "Germany"
            code.contains("FR") -> "France"
            code.contains("ES") -> "Spain"
            code.contains("IT") -> "Italy"
            code.contains("NL") -> "Netherlands"
            code.contains("PL") -> "Poland"
            else -> "Unknown"
        }
    }
    
    fun getAllCategories(): List<EggCategory> {
        return EggCategory.values().toList()
    }
    
    fun getAllHousingTypes(): List<HousingType> {
        return HousingType.values().toList()
    }
}

