package com.example.demo

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.random.Random

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class MicrobenchmarkingTest {
    private var data = 0.0

    @Setup
    fun setUp() {
        data = Random.nextDouble()
    }

    @Benchmark
    fun sqrtBenchmark(): Double = sqrt(data)

    @Benchmark
    fun cosBenchmark(): Double = cos(data)
}
