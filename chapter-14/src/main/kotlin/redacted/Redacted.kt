package redacted

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class Redacted

data class Secret<T> (val value: T) {
    override fun toString(): String = "*"
}