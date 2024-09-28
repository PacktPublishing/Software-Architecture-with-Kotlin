package com.example.demo

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.UUID
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.MILLISECONDS)
class MicrobenchmarkingTest {
    private var data = emptyList<UUID>()

    @Setup
    fun setUp() {
        data = (1..2).map { UUID.randomUUID() }
    }

    @Benchmark
    fun combineUUIDBenchmark(): UUID = data.reduce { one, two -> one + two }

    private operator fun UUID.plus(another: UUID): UUID {
        val mostSignificant = mostSignificantBits xor another.mostSignificantBits
        val leastSignficant = leastSignificantBits xor another.leastSignificantBits
        return UUID(mostSignificant, leastSignficant)
    }
}
