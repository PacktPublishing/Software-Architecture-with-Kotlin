package polymorphic

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

interface Household {
    fun performService(time: Instant)

    fun wasServicePerformed(): Boolean
}

class PlumbingHousehold : Household {
    var startedAt: Instant? = null

    var completedAt: Instant? = null

    var confirmedAt: Instant? = null

    override fun performService(time: Instant) {
        startedAt = time
    }

    fun completeService(time: Instant) {
        completedAt = time
    }

    fun confirmService(time: Instant) {
        confirmedAt = time
    }

    override fun wasServicePerformed(): Boolean {
        return startedAt != null && completedAt != null && confirmedAt != null
    }
}

class BabysittingHousehold(val agreedHours: Int) : Household {
    var startedAt: Instant? = null

    var endedAt: Instant? = null

    override fun performService(time: Instant) {
        startedAt = time
    }

    fun endService(time: Instant) {
        endedAt = time
    }

    override fun wasServicePerformed(): Boolean {
        return if (startedAt == null || endedAt == null) {
            false
        } else {
            Duration.between(startedAt, endedAt).toHours() >= agreedHours
        }
    }
}

class RoomCleaningHousehold(val agreedRooms: Set<String>) : Household {
    var startedAt: Instant? = null

    val roomCleaned: MutableSet<String> = mutableSetOf()

    var endedAt: Instant? = null

    override fun performService(time: Instant) {
        startedAt = time
    }

    fun cleaned(
        time: Instant,
        room: String,
    ) {
        roomCleaned.add(room)

        if (allAgreedRoomsCleaned()) {
            endedAt = time
        }
    }

    private fun allAgreedRoomsCleaned() = roomCleaned.containsAll(agreedRooms)

    override fun wasServicePerformed(): Boolean = allAgreedRoomsCleaned()
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
