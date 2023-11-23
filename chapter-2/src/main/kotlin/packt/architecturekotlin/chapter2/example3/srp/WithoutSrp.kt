package packt.architecturekotlin.chapter2.example3.srp

import packt.architecturekotlin.chapter2.example1.Household
import java.time.Instant

interface HouseholdService {
    fun create(household: Household): Household
}
interface ContractService {
    fun draftContract(contract: Contract)
}
interface NotificationService {
    fun notifyHouseholds(contract: Contract)
}

class SmsNotificationService : NotificationService {

    override fun notifyHouseholds(contract: Contract) {
        // send sms messages to household's phone numbers
    }
}

class EmailNotificationService : NotificationService {
    override fun notifyHouseholds(contract: Contract) {
        // send messages to household's email addresses
    }
}

data class Contract(
    val draftedBy: Instant,
)
