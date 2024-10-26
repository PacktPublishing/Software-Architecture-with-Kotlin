package extension

fun String.getFirstLetters(): String =
    split(" ").joinToString(".") {
        it.first().toString()
    }

fun List<String>?.concat(): String = this?.joinToString(",")?: ""

fun main() {
    println(Name("Sam") + Name("Jones"))
}

data class Name(val value: String)

operator fun Name.plus(other: Name): Name = Name("$value ${other.value}")

