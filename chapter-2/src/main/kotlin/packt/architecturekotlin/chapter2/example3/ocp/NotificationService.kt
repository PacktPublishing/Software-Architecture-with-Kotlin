package packt.architecturekotlin.chapter2.example3.ocp

import packt.architecturekotlin.chapter2.example3.srp.Contract

interface NotificationService {
    fun notifyIHouseholds(contract: Contract)
}
class SmsNotificationService : NotificationService {
    override fun notifyIHouseholds(contract: Contract) {
        // send sms messages to household's phone numbers
    }
}
class EmailNotificationService : NotificationService {
    override fun notifyIHouseholds(contract: Contract) {
        // send messages to household's email addresses
    }
}
