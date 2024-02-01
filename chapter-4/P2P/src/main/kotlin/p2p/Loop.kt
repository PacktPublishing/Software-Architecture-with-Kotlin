package p2p

fun loopForever(
    intervalInMs: Long,
    delegate: () -> Unit,
) {
    assert(intervalInMs >= 0L)

    while (true) {
        try {
            delegate()
            if (intervalInMs > 0) {
                Thread.sleep(intervalInMs)
            }
        } catch (ignored: Exception) {
        }
    }
}
