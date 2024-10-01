package example

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class FindBiggestNumberKtTest :
    FunSpec({
        test("Find the biggest out of positive numbers") {
            findBiggestNumber(listOf(17, 18, 6)) shouldBe 18
        }
    })

class FindBiggestNumberParameterizedTest :
    FunSpec({
        context("Find the biggest out of positive numbers") {
            withData(
                emptyList<Int>() to null,
                listOf(8) to 8,
                listOf(99, 8) to 99,
                listOf(17, 18, 6) to 18,
                listOf(944, 0, 633) to 944,
                listOf(0, -32, 76) to 76,
                listOf(-11, -32, -102) to -11,
                listOf(-25, -57, 0) to 0,
                listOf(
                    Integer.MAX_VALUE + 1,
                    Integer.MAX_VALUE,
                    0,
                    Int.MIN_VALUE,
                    -Int.MIN_VALUE - 1,
                    -Int.MAX_VALUE,
                    Int.MIN_VALUE - 1,
                ) to Integer.MAX_VALUE,
            ) { (allNumbers, expectedMax) ->
                findBiggestNumber(allNumbers) shouldBe expectedMax
            }
        }
    })
