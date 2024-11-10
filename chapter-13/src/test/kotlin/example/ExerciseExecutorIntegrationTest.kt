package example

import example.Exercise.GoToGym
import example.Exercise.RunInThePark
import example.Weather.CLOUDY
import example.Weather.RAINY
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.File
import java.io.FileReader
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit.HOURS

fun main() {
}

class ExerciseExecutorIntegrationTest :
    StringSpec({
        "Gym when cloudy and run in the park when rainy as recorded in file log" {
            val file =
                File
                    .createTempFile("Exer", "cise")
                    .apply { deleteOnExit() }
            val exec = ExerciseExecutor(ExerciseFileLog(file))
            val now = Instant.now()
            val fourHoursLater = now.plus(4, HOURS)
            val utc = ZoneId.of("UTC")

            exec.doExercise(RAINY, now)
            exec.doExercise(CLOUDY, fourHoursLater)

            FileReader(file).readLines() shouldBe
                listOf(
                    "${now.atZone(utc).toLocalDateTime()}: GoToGym",
                    "${fourHoursLater.atZone(utc).toLocalDateTime()}: RunInThePark",
                )
        }
    })
