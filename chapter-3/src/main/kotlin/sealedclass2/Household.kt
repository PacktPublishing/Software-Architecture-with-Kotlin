package sealedclass2

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

sealed class Household {
    var startedAt: Instant? = null

    fun performService(time: Instant) {
        startedAt = time
    }

    fun wasServicePerformed(): Boolean {
        return when (this) {
            is BabysittingHousehold -> {
                if (startedAt == null || endedAt == null) {
                    false
                } else {
                    Duration.between(startedAt, endedAt).toHours() >= agreedHours
                }
            }

            is PlumbingHousehold -> {
                startedAt != null && completedAt != null && confirmedAt != null
            }

            is RoomCleaningHousehold -> allAgreedRoomsCleaned()
        }
    }
}

class PlumbingHousehold : Household() {
    var completedAt: Instant? = null

    var confirmedAt: Instant? = null

    fun completeService(time: Instant) {
        completedAt = time
    }

    fun confirmService(time: Instant) {
        confirmedAt = time
    }
}

class BabysittingHousehold(val agreedHours: Int) : Household() {
    var endedAt: Instant? = null

    fun endService(time: Instant) {
        endedAt = time
    }
}

class RoomCleaningHousehold(val agreedRooms: Set<String>) : Household() {
    val roomCleaned: MutableSet<String> = mutableSetOf()

    var endedAt: Instant? = null

    fun cleaned(
        time: Instant,
        room: String,
    ) {
        roomCleaned.add(room)

        if (allAgreedRoomsCleaned()) {
            endedAt = time
        }
    }

    internal fun allAgreedRoomsCleaned() = roomCleaned.containsAll(agreedRooms)
}

fun main() {
    val now = Instant.now()

    val plumbingHousehold = PlumbingHousehold()
    plumbingHousehold.performService(now)
    plumbingHousehold.completeService(now.plus(2, HOURS))
    plumbingHousehold.confirmService(now.plus(2, HOURS).plus(3, MINUTES))

    println("Was plumbing service performed? ${plumbingHousehold.wasServicePerformed()}")

    val babysittingHousehold = BabysittingHousehold(3)
    babysittingHousehold.performService(now)
    babysittingHousehold.endService(now.plus(3, HOURS))

    println("Was babysitting service performed? ${babysittingHousehold.wasServicePerformed()}")

    val roomCleaningHousehold = RoomCleaningHousehold(setOf("Kitchen", "Bathroom"))
    roomCleaningHousehold.performService(now)
    roomCleaningHousehold.cleaned(now.plus(3, HOURS), "Kitchen")

    println("Was room cleaning service performed? ${roomCleaningHousehold.wasServicePerformed()}")
}
