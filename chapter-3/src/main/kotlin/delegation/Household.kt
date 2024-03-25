package delegation

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

interface ServiceStarter {
    fun start(time: Instant)
}

interface ServiceChecker {
    fun wasServicePerformed(): Boolean
}

class Started : ServiceStarter {
    var startedAt: Instant? = null

    override fun start(time: Instant) {
        startedAt = time
    }
}

interface ThreePhaseService : ServiceStarter, ServiceChecker {
    fun complete(time: Instant)

    fun confirm(time: Instant)
}

class ThreePhaseServiceImpl(val started: Started = Started()) :
    ThreePhaseService, ServiceStarter by started {
    var completedAt: Instant? = null

    var confirmedAt: Instant? = null

    override fun complete(time: Instant) {
        completedAt = time
    }

    override fun confirm(time: Instant) {
        confirmedAt = time
    }

    override fun wasServicePerformed(): Boolean {
        return started.startedAt != null && completedAt != null && confirmedAt != null
    }
}

interface HourlyService : ServiceStarter, ServiceChecker {
    fun end(time: Instant)
}

class HourlyServiceImpl(val agreedHours: Int, val started: Started = Started()) :
    HourlyService, ServiceStarter by started {
    var endedAt: Instant? = null

    override fun end(time: Instant) {
        endedAt = time
    }

    override fun wasServicePerformed(): Boolean =
        if (started.startedAt == null || endedAt == null) {
            false
        } else {
            Duration.between(started.startedAt, endedAt).toHours() >= agreedHours
        }
}

interface ItemizedService<T> : ServiceStarter, ServiceChecker {
    fun complete(
        time: Instant,
        item: T,
    )
}

class ItemizedServiceImpl<T>(val agreed: Set<T>) : ItemizedService<T>, ServiceStarter by Started() {
    val completed: MutableSet<T> = mutableSetOf()

    var endedAt: Instant? = null

    override fun complete(
        time: Instant,
        item: T,
    ) {
        completed.add(item)

        if (allAgreedItemsCleaned()) {
            endedAt = time
        }
    }

    private fun allAgreedItemsCleaned() = completed.containsAll(agreed)

    override fun wasServicePerformed(): Boolean = allAgreedItemsCleaned()
}

class Plumbing : ThreePhaseService by ThreePhaseServiceImpl()

class Babysitting(agreedHours: Int) : HourlyService by HourlyServiceImpl(agreedHours)

class RoomCleaning(agreedRooms: Set<String>) :
    ItemizedService<String> by ItemizedServiceImpl(agreedRooms)

fun main() {
    val now = Instant.now()
    val plumbing = Plumbing()
    plumbing.start(now)
    plumbing.complete(now.plus(2, HOURS))
    plumbing.confirm(now.plus(2, HOURS).plus(3, MINUTES))
    println("Was plumbing service performed? ${plumbing.wasServicePerformed()}")
    val babysitting = Babysitting(3)
    babysitting.start(now)
    babysitting.end(now.plus(3, HOURS))
    println("Was babysitting service performed? ${babysitting.wasServicePerformed()}")
    val roomCleaning = RoomCleaning(setOf("Kitchen", "Bathroom"))
    roomCleaning.start(now)
    roomCleaning.complete(now.plus(10, MINUTES), "Kitchen")
    println("Was room cleaning service performed? ${roomCleaning.wasServicePerformed()}")
}
