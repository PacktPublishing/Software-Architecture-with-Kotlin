import io.github.oshai.kotlinlogging.KotlinLogging.logger

class Logging {
    private val log = logger {}

    fun test() {
        log.atInfo {
            message = ""
        }
    }
}