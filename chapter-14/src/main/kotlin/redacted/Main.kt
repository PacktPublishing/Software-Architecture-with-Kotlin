package redacted

import java.time.LocalDate

fun main() {
    println("${BankAccount("Iban", "bic", "holderName")}")
    println("${UserAccount("username", "password", LocalDate.now())}")
    println("Secret wrapper: ${Secret("email@address.com")}")
}