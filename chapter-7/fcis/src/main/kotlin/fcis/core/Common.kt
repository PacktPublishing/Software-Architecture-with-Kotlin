package fcis.core

enum class ErrorType {
    HOUSEHOLD_NOT_FOUND,
    SAME_HOUSEHOLD_IN_CONTRACT,
}

data class Error(
    val reason: String,
    val type: ErrorType,
)
