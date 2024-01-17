package packt.architecturekotlin.chapter3.polymorphic

import java.time.Duration
import java.time.Instant

interface Service {
    fun wasPerformed(): Boolean
}

class PlumbingService(val description: String) : Service {
    var completedAt: Instant? = null
    var confirmedAt: Instant? = null
    fun completed() {
        completedAt = Instant.now()
    }
    fun confirmed() {
        confirmedAt = Instant.now()
    }
    override fun wasPerformed(): Boolean {
        return (completedAt != null && confirmedAt != null)
    }
}

class BabysittingService(val toddlerName: String, val agreedHours: Int) : Service {
    var startedAt: Instant? = null
    var endedAt: Instant? = null

    fun started() {
        startedAt = Instant.now()
    }

    fun ended() {
        endedAt = Instant.now()
    }

    override fun wasPerformed(): Boolean =
        if (startedAt == null || endedAt == null) {
            false
        } else {
            Duration.between(endedAt, startedAt).toHours() >= agreedHours
        }
}

class RoomCleaningService(val agreedRooms: Set<String>) : Service {
    var startedAt: Instant? = null
    val roomCleaned: MutableSet<String> = mutableSetOf()
    var endedAt: Instant? = null

    fun started() {
        startedAt = Instant.now()
    }

    fun cleaned(room: String) {
        roomCleaned.add(room)

        if (allAgreedRoomsCleaned()) {
            endedAt = Instant.now()
        }
    }

    private fun allAgreedRoomsCleaned() = roomCleaned.containsAll(agreedRooms)

    override fun wasPerformed(): Boolean = allAgreedRoomsCleaned()
}
