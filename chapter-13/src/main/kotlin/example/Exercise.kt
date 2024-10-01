package example

import java.io.File
import java.io.IOException
import java.time.Instant
import java.time.ZoneId

enum class Weather {
    SUNNY,
    RAINY,
    CLOUDY,
    STORMY,
}

sealed class Exercise {
    data object RunInThePark : Exercise()

    data object GoToGym : Exercise()
}

class ExerciseExecutor(
    private val log: ExerciseLog,
) {
    fun doExercise(
        weather: Weather,
        time: Instant,
    ): Exercise {
        val exercise =
            when (weather) {
                Weather.SUNNY, Weather.CLOUDY -> Exercise.RunInThePark
                Weather.STORMY, Weather.RAINY -> Exercise.GoToGym
            }
        log.record(time, exercise)
        return exercise
    }
}

interface ExerciseLog {
    fun record(
        time: Instant,
        exercise: Exercise,
    )
}

class ExerciseFileLog(
    private val file: File,
) : ExerciseLog {
    val utc = ZoneId.of("UTC")

    override fun record(
        time: Instant,
        exercise: Exercise,
    ) {
        try {
            val utcDateTime = time.atZone(utc).toLocalDateTime()
            val text = "$utcDateTime: $exercise\n"
            file.appendText(text)
        } catch (e: IOException) {
            println("error writing to the file: $file")
        }
    }
}
