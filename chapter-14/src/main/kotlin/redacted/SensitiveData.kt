package redacted

import java.time.LocalDate

@Redacted
data class BankAccount(
    val iban: String,
    val bic: String,
    val holderName: String
)

data class UserAccount(
    @Redacted val username: String,
    @Redacted val password: String,
    val createdAt: LocalDate
)