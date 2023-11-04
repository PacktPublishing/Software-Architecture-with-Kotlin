package packt.architecturekotlin.chapter2.example1

data class Household(
    val name: String,
    val members: List<Person>,
)

data class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val skills: List<String>,
)

fun Household.validate(): List<String> =
    listOfNotNull(
        if (name.isBlank()) "name must not be empty" else null,
    ) + members.flatMap { it.validate() }

fun Person.validate(): List<String> = listOfNotNull(
    if (firstName.isBlank()) "first name must be non-empty" else null,
    if (lastName.isBlank()) "last name must be non-empty" else null,
    if (age < 0) "age must be non-negative" else null,
)
