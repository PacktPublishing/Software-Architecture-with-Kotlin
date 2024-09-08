package com.example.plugins

object ReadHouseholdByName {
    operator fun invoke(name: String): Household? = householdByNames[name]
}

object UpsertHousehold {
    operator fun invoke(household: Household): Household? = householdByNames.put(household.name, household)
}

object DeleteHousehold {
    operator fun invoke(name: String): Household? = householdByNames.remove(name)
}
