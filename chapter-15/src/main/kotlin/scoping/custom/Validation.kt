package scoping.custom

fun <T> T.validate(
    build: ValidationBuilder.(T) -> Unit
): List<String> =
    ValidationBuilder()
        .also { builder -> builder.build(this) }
        .getErrors()

class ValidationBuilder {
    private val failures = mutableListOf<String>()

    fun evaluate(
        result: Boolean,
        failureMessage: () -> String
    ) {
        if (!result) failures.add(failureMessage())
    }
    fun getErrors() = failures.toList()
}

fun main() {
    val failures = "Some very%long nickname".validate {
        evaluate(it.length < 20) { "Must be under 20 characters: \"$it\"" }
        evaluate(it.contains("%").not()) { "Must not contains % character"}
    }
    println("failures: $failures")
}