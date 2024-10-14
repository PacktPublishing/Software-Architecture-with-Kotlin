package override

import java.time.Instant

data class UserAccount(
    val username: String,
    val password: String,
    val createdAt: Instant
) {
    override fun toString(): String {
        return "UserAccount(createdAt=$createdAt)"
    }
}