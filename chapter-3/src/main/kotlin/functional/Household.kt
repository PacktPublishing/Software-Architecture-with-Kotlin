package functional

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MINUTES

data class Plumbing(
    val startedAt: Instant? = null,
    val completedAt: Instant? = null,
    val confirmedAt: Instant? = null,
)

data class Babysitting(
    val agreedHours: Int,
    val startedAt: Instant? = null,
    val endedAt: Instant? = null,
)

data class RoomCleaning(
    val agreedRooms: Set<String>,
    val startedAt: Instant? = null,
    val completed: Set<String> = emptySet(),
    val endedAt: Instant? = null,
)

fun <T> T.start(
    time: Instant,
    transform: T.(Instant) -> T,
): T = transform(time)

fun Plumbing.complete(time: Instant): Plumbing = copy(completedAt = time)

fun Plumbing.confirm(time: Instant): Plumbing = copy(confirmedAt = time)

fun Babysitting.end(time: Instant): Babysitting = copy(endedAt = time)

fun RoomCleaning.complete(
    time: Instant,
    room: String,
): RoomCleaning {
    val newCleaned = completed + room

    val newEnded = if (completed.containsAll(agreedRooms)) time else endedAt

    return copy(completed = newCleaned, endedAt = newEnded)
}

fun Plumbing.wasServicePerformed(): Boolean = startedAt != null && completedAt != null && confirmedAt != null

fun Babysitting.wasServicePerformed(): Boolean =

    if (startedAt == null || endedAt == null) {
        false
    } else {
        Duration.between(startedAt, endedAt).toHours() >= agreedHours
    }

fun RoomCleaning.wasServicePerformed(): Boolean = endedAt != null

fun main() {
    val now = Instant.now()

    val plumbing =
        Plumbing()
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .complete(now.plus(2, HOURS))
            .confirm(now.plus(2, HOURS).plus(3, MINUTES))

    println("Was plumbing service performed? ${plumbing.wasServicePerformed()}")

    val babysitting =
        Babysitting(3)
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .end(now.plus(3, HOURS))

    println("Was babysitting service performed? ${babysitting.wasServicePerformed()}")

    val roomCleaning =
        RoomCleaning(setOf("Kitchen", "Bathroom"))
            .start(now) { startedAt -> copy(startedAt = startedAt) }
            .complete(now.plus(10, MINUTES), "Kitchen")

    println("Was room cleaning service performed? ${roomCleaning.wasServicePerformed()}")
}
