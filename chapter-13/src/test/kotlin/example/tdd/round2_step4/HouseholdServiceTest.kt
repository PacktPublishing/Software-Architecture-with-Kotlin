package example.tdd.round2_step4

import example.tdd.round2_step4.Result.Failure
import example.tdd.round2_step4.Result.Success
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

class HouseholdServiceTest :
    DescribeSpec({
        val blankStrings = listOf("", " ", "\t", "\n", "  ", " \t", " \t \n ")

        describe("household creation") {
            blankStrings.forEach { blankString ->
                it("ensures surname is not blank") {
                    val service = HouseholdService()
                    service.createHousehold(Household(surname = blankString)) shouldBe
                        Failure(
                            "Surname must be non-empty",
                        )

                }
            }
            it("succeeds with non-blank surname") {
                val service = HouseholdService()
                val household = Household(surname = Arb.string(minSize = 3).next())
                service.createHousehold(household) shouldBe Success(household)
            }

        }
    })

sealed class Result {
    data class Success(
        val household: Household,
    ) : Result()

    data class Failure(
        val reason: String,
    ) : Result()
}

data class Household(
    val surname: String,
)

class HouseholdService {
    fun createHousehold(household: Household): Result =
        if (household.surname.isNotBlank()) {
            Success(household)
        } else {
            Failure("Surname must be non-empty")
        }
}
