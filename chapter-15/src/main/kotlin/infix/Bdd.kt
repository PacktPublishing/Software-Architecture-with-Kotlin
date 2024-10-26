package infix

object When

typealias PreCondition = () -> Int

typealias Action = (Int) -> Int

infix fun When.number(n: Int): PreCondition = { n }

infix fun PreCondition.then(action: Action): Int = action(this())

object Square: Action {
    override fun invoke(p1: Int): Int = p1 * p1
}

infix fun Int.shouldBe(expected: Int) {
    require(this == expected) {
        "Expected: $expected but was $this"
    }
}

fun main() {
    ((When.number(2)).then(Square)).shouldBe(5)
    When number 2 then Square shouldBe 5

}