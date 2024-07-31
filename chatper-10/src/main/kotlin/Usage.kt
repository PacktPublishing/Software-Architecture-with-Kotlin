fun main() {
    val repo = HouseholdRepository()

    val name = "Whittington"
    val email1 = "info@whittington.com"
    val email2a = "query@whittington.com"
    val email2b = "contact@whittington.com"
    val household1 = Household(0, name, email1)

    repo.create(name) { household1 }

    repo.update(name) { household1.copy(version = 1, email = email2a)}
    repo.update(name) { household1.copy(version = 1, email = email2b)}

    repo.get(name)?.also {
        println("${it.version}, ${it.email}")
    }
}
