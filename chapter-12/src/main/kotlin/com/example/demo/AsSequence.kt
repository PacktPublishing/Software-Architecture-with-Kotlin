package com.example.demo

fun someExpensiveOp(n: Int): Int = n

fun processAsList() {
    val result =
        listOf(1, 7, 3, 23, 63)
            .filter {
                println("filter:$it")
                it > 3
            }.map {
                println("expensive:$it")
                someExpensiveOp(it)
            }.take(2)
    println(result)
}

fun processAsSequence() {
    val result =
        listOf(1, 7, 3, 23, 63)
            .asSequence()
            .filter {
                println("filter:$it")
                it > 3
            }.map {
                println("expensive:$it")
                someExpensiveOp(it)
            }.take(2)
    println(result.toList())
}

fun main() {
    println("-----as list-----")
    processAsList()
    println("----------")

    println("-----as sequence-----")
    processAsSequence()
    println("----------")
}
