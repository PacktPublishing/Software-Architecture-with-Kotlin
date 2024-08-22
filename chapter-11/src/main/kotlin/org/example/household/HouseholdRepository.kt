package org.example.household

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.github.oshai.kotlinlogging.withLoggingContext
import java.util.UUID

class HouseholdRepository {
    private val log = logger {}

    fun create() {
        val householdName = "Whittington"
        val sessionId = UUID.randomUUID().toString()
        withLoggingContext("session" to sessionId) {
            log.atInfo {
                message = "Created a new household '$householdName'"
                payload = mapOf(
                    "householdName" to householdName
                )
            }
        }
    }
}

fun main() {
    HouseholdRepository().create()
}