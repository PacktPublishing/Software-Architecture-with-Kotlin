package packt.architecturekotlin.chapter2.example3.lsp

import packt.architecturekotlin.chapter2.example3.srp.Contract
import packt.architecturekotlin.chapter2.example3.srp.NotificationService

class PhoneNotificationService : NotificationService {

    override fun notifyHouseholds(contract: Contract) {
        // ring an automated message to household's phone

        // also update contract status to UNDER_REVIEW
    }
}
