package packt.architecturekotlin.chapter2.example3.dip

import java.time.Instant

class ContractWorkflowService(
    val emailNotificationService: EmailNotificationService,
) {
    fun agree(contract: Contract): Contract {
        return contract.copy(agreedAt = Instant.now()).also {
            emailNotificationService.notifyHouseholds(contract)
        }
    }
}
interface NotificationService {
    fun notifyHouseholds(contract: Contract)
}
class EmailNotificationService : NotificationService {
    override fun notifyHouseholds(contract: Contract) {
        // send messages to household's email addresses
    }
}

data class Contract(
    val draftedAt: Instant,
    val agreedAt: Instant,
)
