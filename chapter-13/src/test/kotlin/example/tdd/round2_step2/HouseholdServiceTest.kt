package example.tdd.round2_step2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

class HouseholdServiceTest : StringSpec({
        "fail to create household of empty surname" {
            val service = HouseholdService()
            service.createHousehold(Household(surname = "")) shouldBe Failure(
                "Surname must be non-empty"
            )
        }
        "successfully create a household" {
            val service = HouseholdService()
            val household = Household(surname = Arb.string(minSize = 3).next())
            service.createHousehold(household) shouldBe Success(household)
        }
    })

data class Success(val household: Household)

data class Failure(val reason: String)

class Household(surname: String) { }

class HouseholdService {
    fun createHousehold(household: Household): Failure = Failure("Surname must be non-empty")
}
