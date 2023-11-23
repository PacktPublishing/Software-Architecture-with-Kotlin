package packt.architecturekotlin.chapter2.example4

class Person(val name: String, val address: Address) {
    fun getAddressCity(): String {
        return address.city
    }
}

class Address(val city: String)
