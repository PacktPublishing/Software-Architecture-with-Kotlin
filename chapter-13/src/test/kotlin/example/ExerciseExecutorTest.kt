package example

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.Instant

class ExerciseExecutorTest :
    BehaviorSpec({
        Given("Today is sunny") {
            val exerciseLog = mockk<ExerciseLog>()
            val executor = ExerciseExecutor(exerciseLog)
            every { exerciseLog.record(any(), any()) } returns Unit
            val weather = Weather.SUNNY
            When("doing an exercise") {
                val now = Instant.now()
                Then("running in the park") {
                    executor.doExercise(weather, now) shouldBe Exercise.RunInThePark
                }
                And("the exercise is logged") {
                    verify { exerciseLog.record(now, Exercise.RunInThePark) }
                }
            }
        }
    })
