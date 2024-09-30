import Exercise.GoToGym
import Exercise.RunInThePark
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.File
import java.io.FileReader
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit.HOURS

fun main() {
}

class ExerciseLogIntegrationTest :
    StringSpec({
        "should append a new line to the file log" {
            val file =
                File
                    .createTempFile("E", "L")
                    .apply { deleteOnExit() }
            val log = ExerciseFileLog(file)
            val now = Instant.now()
            val utc = ZoneId.of("UTC")

            val input =
                listOf(
                    now to GoToGym,
                    now.plus(4, HOURS) to RunInThePark,
                )

            input.forEach { (time, exercise) ->
                log.record(time, exercise)
            }

            FileReader(file).readLines() shouldBe
                input.map { (time, exercise) ->
                    "${time.atZone(utc).toLocalDateTime()}: $exercise"
                }
        }
    })
