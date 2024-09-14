package core.port

import core.Household

interface HouseholdRepository {
    fun findByName(householdName: String): Household?
} 
