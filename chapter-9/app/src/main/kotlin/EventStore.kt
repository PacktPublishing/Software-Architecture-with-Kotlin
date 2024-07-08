
class EventStore<K, E> {
    private val eventsByKey = mutableMapOf<K, List<E>>()
    fun append(id: K, payload: E) {
        eventsByKey.merge(id, listOf(payload)) { t1, t2 -> t1 + t2 }
    }
    fun get(id: K): List<E>? = eventsByKey[id]
    fun get(predicate: (E) -> Boolean): List<E> = eventsByKey.flatMap {
        it.value.filter(predicate)
    }
}