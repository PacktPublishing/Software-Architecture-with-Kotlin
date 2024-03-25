package sealedclass1

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

sealed class Service {
    var startedAt: Instant? = null

    fun performService(time: Instant) {
        startedAt = time
    }

    abstract fun wasServicePerformed(): Boolean
}

class Plumbing : Service() {
    var completedAt: Instant? = null

    var confirmedAt: Instant? = null

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

class Babysitting(val agreedHours: Int) : Service() {
    var endedAt: Instant? = null

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

class RoomCleaning(val agreedRooms: Set<String>) : Service() {
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

    private fun allAgreedRoomsCleaned() = roomCleaned.containsAll(agreedRooms)

    override fun wasServicePerformed(): Boolean = allAgreedRoomsCleaned()
}

fun main() {
    val now = Instant.now()
    val plumbing = Plumbing()
    plumbing.performService(now)
    plumbing.completeService(now.plus(2, HOURS))
    plumbing.confirmService(now.plus(2, HOURS).plus(3, MINUTES))
    println("Was plumbing service performed? ${plumbing.wasServicePerformed()}")
    val babysitting = Babysitting(3)
    babysitting.performService(now)
    babysitting.endService(now.plus(3, HOURS))
    println("Was babysitting service performed? ${babysitting.wasServicePerformed()}")
    val roomCleaning = RoomCleaning(setOf("Kitchen", "Bathroom"))
    roomCleaning.performService(now)
    roomCleaning.cleaned(now.plus(3, HOURS), "Kitchen")

    println("Was room cleaning service performed? ${roomCleaning.wasServicePerformed()}")
}
