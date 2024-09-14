package entity

data class Household(
    val name: String,
    val members: List<String>,
) {
    init {

        require(members.isNotEmpty()) { "Household must have at least one member" }
    }
} 
