import java.util.UUID
import java.util.function.BiFunction

class EventStore<KEY, AGGREGATE> {
    private val aggregatesByKey = mutableMapOf<KEY, List<AGGREGATE>>()

    fun append(id: KEY, payload: AGGREGATE) {
        aggregatesByKey.merge(id, listOf(payload)) { t1, t2 -> t1 + t2 }
    }

    fun get(id: KEY): List<AGGREGATE>? = aggregatesByKey[id]
}