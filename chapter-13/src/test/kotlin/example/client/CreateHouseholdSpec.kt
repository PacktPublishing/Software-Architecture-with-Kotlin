package example.client

import example.api.HouseholdApi
import example.model.HouseholdV1
import io.kotest.core.spec.style.ShouldSpec
import java.util.UUID

class CreateHouseholdSpec :
    ShouldSpec({
        should("create a household") {
            val id = UUID.randomUUID()
            val household = HouseholdV1("Hello", "iamhere@test.com")
            HouseholdApi().createHousehold(id, household)
        }
    })
