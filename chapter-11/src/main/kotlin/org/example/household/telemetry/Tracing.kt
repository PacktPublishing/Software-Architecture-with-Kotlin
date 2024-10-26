package org.example.household.telemetry

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor

val tracer: Tracer = run {
    val oltpEndpont = "http://localhost:8123"
    val otlpExporter = OtlpGrpcSpanExporter.builder()
        .setEndpoint(oltpEndpont)
        .build()
    val spanProcessor = SimpleSpanProcessor.create(otlpExporter)
    val tracerProvider = SdkTracerProvider.builder()
        .addSpanProcessor(spanProcessor)
        .build()
    OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .buildAndRegisterGlobal()
    GlobalOpenTelemetry.getTracer("example-tracer")
}

fun main() {
    val span: Span = tracer
        .spanBuilder("process data")
        .startSpan()
        .apply { setAttribute("data.source", "memory") }
    try {
        println("process finished")
    } catch (e: Exception) {
        span.recordException(e)
    } finally {
        span.end()
    }
}