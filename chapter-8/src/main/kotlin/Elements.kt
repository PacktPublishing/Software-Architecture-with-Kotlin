
data class Address(
    val line1: String,
    val line2: String? = null,
    val line3: String? = null,
    val postalCode: String,
    val city: String,
    val country: String
)