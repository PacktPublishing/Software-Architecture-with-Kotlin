package packt.architecturekotlin.chapter2.example2

enum class InternalError {
    WRONG_PASSWORD,
    USERNAME_NOT_FOUND,
    FAILED_CAPTCHA,
    TIMED_OUT,
    INVALID_REQUEST
}

enum class ExternalError {
    FAILED_AUTHENTICATION,
    TIMED_OUT,
    INVALID_REQUEST
}