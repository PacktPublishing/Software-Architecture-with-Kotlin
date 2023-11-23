package packt.architecturekotlin.chapter2.example3.isp

interface Human {
    fun logOn()

    fun exerciseContract()
}

data class User(val username: String) : Human {
    override fun logOn() {
        // user log on
    }

    override fun exerciseContract() {
        throw UnsupportedOperationException("user cannot exercise contract")
    }
}

data class HouseholdMember(val name: String) : Human {
    override fun logOn() {
        throw UnsupportedOperationException("household members do not log on")
    }

    override fun exerciseContract() {
        // exercise contract
    }
}
