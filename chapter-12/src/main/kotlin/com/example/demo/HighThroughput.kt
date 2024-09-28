package com.example.demo

import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.random.Random

fun main() {
    val queue = RequestQueue<Int>()

    repeat(10000) {
        queue.put(Random.nextInt())
    }

    var count = 0
    while (queue.size() > 0) {
        println("expensive remove: ${++count}")

        queue.expensiveRemove()?.also {
            someProcessing(it)
        }
    }
}

inline fun someProcessing(value: Int): Int = value

class RequestQueue<T> {
    private val requests = ConcurrentLinkedQueue<T>()

    fun put(request: T) {
        requests.add(request)
    }

    fun expensiveRemove(): T? {
        Thread.sleep(2000)
        return requests.remove()
    }

    fun size(): Int = requests.size
}
