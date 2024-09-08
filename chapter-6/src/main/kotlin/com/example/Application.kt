package com.example

import com.example.plugins.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/") {
                call.respond(HttpStatusCode.OK, "Hello")
            }
            get("/households") {
                call.respond(householdByNames.values)
            }
            get("/households/{name}") {
                val household = call.parameters["name"]?.let {
                    ReadHouseholdByName(it)
                }
                if (household == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    call.respond(HttpStatusCode.OK, household)
                }
            }
            put("/households/{name}") {
                val householdName = call.parameters["name"]
                if (householdName == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val household = call.receive<Household>()
                    val previous = UpsertHousehold(household)
                    val result = if (previous == null) {
                        HttpStatusCode.Created
                    } else {
                        HttpStatusCode.OK
                    }
                    call.respond(result, household)
                }
            }
            delete("/households/{name}") {
                val householdName = call.parameters["name"]
                if (householdName == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val previous = DeleteHousehold(householdName)
                    if (previous == null) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.OK, previous)
                    }
                }
            }
        }
    }.start(wait = true)
}
