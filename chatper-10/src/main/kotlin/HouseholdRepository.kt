import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class HouseholdRepository {
    private val values: ConcurrentMap<String, Household> = ConcurrentHashMap()

    fun create(
        key: String,
        callback: () -> Household
    ): Household {
        val household = callback()
        val result = values.putIfAbsent(key, household)
        return result ?: household
    }

    fun update(
        key: String,
        callback: (Household) -> Household
    ): Household? = values.computeIfPresent(key) { _, existing ->
        callback(existing).let { updated ->
            if (updated.version == existing.version + 1) {
                updated
            } else {
                existing
            }
        }
    }

    fun get(key: String): Household? = values[key]
}