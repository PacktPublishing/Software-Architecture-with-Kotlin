package redacted

import java.time.Instant
import java.time.LocalDate

fun main() {
    val bankAccount = BankAccount("Iban", "bic", "holderName")
    println("$bankAccount")

    val userAccount = UserAccount("username", "password", LocalDate.now())
    println("$userAccount")

    val emailAddress = Secret("email@address.com")
    println("Secret wrapper: $emailAddress")
}