package functional

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

data class PlumbingHousehold(
    val startedAt: Instant? = null,
    val completedAt: Instant? = null,
    val confirmedAt: Instant? = null,
)

data class BabysittingHousehold(
    val agreedHours: Int,
    val startedAt: Instant? = null,
    val endedAt: Instant? = null,
)

data class RoomCleaningHousehold(
    val agreedRooms: Set<String>,
    val startedAt: Instant? = null,
    val completed: Set<String> = emptySet(),
    val endedAt: Instant? = null,
)

fun <T> T.start(
    time: Instant,
    transform: T.(Instant) -> T,
): T = transform(time)

fun PlumbingHousehold.complete(time: Instant): PlumbingHousehold = copy(completedAt = time)

fun PlumbingHousehold.confirm(time: Instant): PlumbingHousehold = copy(confirmedAt = time)

fun BabysittingHousehold.end(time: Instant): BabysittingHousehold = copy(endedAt = time)

fun RoomCleaningHousehold.complete(
    time: Instant,
    room: String,
): RoomCleaningHousehold {
    val newCleaned = completed + room

    val newEnded = if (completed.containsAll(agreedRooms)) time else endedAt

    return copy(completed = newCleaned, endedAt = newEnded)
}

fun PlumbingHousehold.wasServicePerformed(): Boolean = startedAt != null && completedAt != null && confirmedAt != null

fun BabysittingHousehold.wasServicePerformed(): Boolean =

    if (startedAt == null || endedAt == null) {
        false
    } else {
        Duration.between(startedAt, endedAt).toHours() >= agreedHours
    }

fun RoomCleaningHousehold.wasServicePerformed(): Boolean = endedAt != null

fun main() {
    val now = Instant.now()

    val plumbingHousehold =
        PlumbingHousehold()
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .complete(now.plus(2, HOURS))
            .confirm(now.plus(2, HOURS).plus(3, MINUTES))

    println("Was plumbing service performed? ${plumbingHousehold.wasServicePerformed()}")

    val babysittingHousehold =
        BabysittingHousehold(3)
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .end(now.plus(3, HOURS))

    println("Was babysitting service performed? ${babysittingHousehold.wasServicePerformed()}")

    val roomCleaningHousehold =
        RoomCleaningHousehold(setOf("Kitchen", "Bathroom"))
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .complete(now.plus(10, MINUTES), "Kitchen")

    println("Was room cleaning service performed? ${roomCleaningHousehold.wasServicePerformed()}")
}
