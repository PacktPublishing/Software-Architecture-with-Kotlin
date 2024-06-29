class Executor<A, E>(
    transact: (A, E) -> Transaction<A, E>
) {
    private val events = mutableListOf<E>()
    private var aggregate: A? = null

    fun execute(event: E) {

    }

    fun toTransaction(): Transaction<A, E>? {
        return aggregate?.let { Transaction(events, it) }
    }
}

data class Transaction<A, E>(
    val events: List<E>,
    val aggregate: A
)
