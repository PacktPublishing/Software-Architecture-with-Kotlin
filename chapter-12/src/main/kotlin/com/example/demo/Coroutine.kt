package com.example.demo

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val result1 = async { task1() }
        val result2 = async { task2() }
        val combinedResult = result1.await() + result2.await()
        println("Combined Result: $combinedResult")
    }

suspend fun task1(): Int {
    delay(1000)
    println("Task 1 completed")
    return 42
}

suspend fun task2(): Int {
    delay(1500) // Simulate a 1.5-second delay
    println("Task 2 completed")
    return 58
}
