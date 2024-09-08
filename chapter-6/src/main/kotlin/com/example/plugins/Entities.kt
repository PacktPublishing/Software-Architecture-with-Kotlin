package com.example.plugins

import kotlinx.serialization.Serializable

@Serializable
data class Household(
    val name: String,
    val email: String,
    val members: List<String>,
    val deleted: Boolean = false
)

val householdByNames = mutableMapOf(
    "Whittington" to Household(
        "Whittington",
        "info@whittington.com",
        listOf(
            "Mary Whittington",
            "Daniel Whittington"
        )
    )
)