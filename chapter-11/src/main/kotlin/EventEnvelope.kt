import java.time.Instant
import java.util.UUID

data class EventEnvelope<E>(
    val id: UUID,
    val sessionId: UUID? = null,
    val correlationId: UUID? = null,
    val happenedAt: Instant,
    val action: String,
    val outcome: String,
    val actor: Actor,
    val otherActors: Set<Actor>? = null,
    val resource: Resource,
    val otherResources: Set<Resource>? = null,
    val content: E,
    val diffs: List<Difference>? = null,
)

data class Actor(
    val id: UUID,
    val type: String,
    val involvement: String,
)

data class Resource (
    val id: UUID,
    val type: String,
    val applicationId: String,
    val version: Int? = null
)

data class Difference(
    val op: String,
    val path: String,
    val fromValue: Any? = null,
    val toValue: Any? = null
)
