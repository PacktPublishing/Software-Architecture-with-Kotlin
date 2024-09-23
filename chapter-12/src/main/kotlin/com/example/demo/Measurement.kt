package demo

import kotlin.system.measureTimeMillis
import kotlin.time.measureTimedValue

fun main() {
    val iterations = 1_000
    val operationTime = measureTotalTimeElapsed(iterations) { sampleOperation() }
    println("Total time elapsed: ${operationTime / 1000.0} second")
    println("Throughput: ${iterations / (operationTime / 1000.0)} operations per second")
    println("Latency (average): ${operationTime / iterations} ms")
}

fun sampleOperation() {
    Thread.sleep(1)
    measureTimedValue { }
}

fun measureTotalTimeElapsed(
    iterations: Int,
    operation: (Int) -> Unit,
): Long =
    measureTimeMillis {
        repeat(iterations, operation)
    }
