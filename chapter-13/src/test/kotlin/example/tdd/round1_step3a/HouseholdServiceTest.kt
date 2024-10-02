package example.tdd.round1_step3a

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

class Failure(reason: String) {

}

class Household(surname: String) {

}

class HouseholdService {
    fun createHousehold(household: Household): Failure {
        TODO("Not yet implemented")
    }
}
