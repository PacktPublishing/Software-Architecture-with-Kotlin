package scoping

fun main() {
    println("3".let { it + "5" })
    println("3".run { this + "5" })
    println(with("3") { this + "5" })

    "3".also { println(it) }
    "3".apply { println(this) }
}