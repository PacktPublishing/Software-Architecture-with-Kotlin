package example.tdd.round1_step3b

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HouseholdServiceTest : StringSpec({
        "fail to create household of empty surname" {
            val service = HouseholdService()
            service.createHousehold(Household(surname = "")) shouldBe Failure(
                "Surname must be non-empty"
            )
        }
    })

data class Failure(val reason: String)

class Household(surname: String) { }

class HouseholdService {
    fun createHousehold(household: Household): Failure = Failure("Surname must be non-empty")
}
