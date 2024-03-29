package clientserver

import clientserver.formats.ServiceContract
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.PUT
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.FORBIDDEN
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.NO_CONTENT
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.format.Jackson.auto
import org.http4k.lens.Header
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

val serviceContractLens = Body.auto<ServiceContract>().toLens()
val householdHeader = Header.required("household")

val app: HttpHandler =
    routes(
        "/contracts/{id}" bind GET to { request ->
            val household = householdHeader(request)
            val id = request.path("id")?.toInt()
            val contract = id?.let { contracts[it] }

            if (contract == null) {
                Response(NOT_FOUND).body("Service contract of ID $id not found")
            } else if (contract.partyA.householdName != household && contract.partyB.householdName != household) {
                Response(FORBIDDEN).body("Household $household is not allowed to see the service contract of ID $id")
            } else {
                Response(OK).with(serviceContractLens of contract)
            }
        },
        "/contracts/{id}" bind PUT to { request ->
            val household = householdHeader(request)
            val lens = Body.auto<ServiceContract>().toLens()
            val id = request.path("id")?.toInt()
            val contract = lens(request)

            if (id == null || id != contract.id) {
                Response(BAD_REQUEST).body("Service contract ID in the payload and the URI path do not match")
            } else if (contract.partyA.householdName == contract.partyB.householdName) {
                Response(BAD_REQUEST).body("Service contract must have two different household: $household")
            } else if (contract.partyA.householdName != household && contract.partyB.householdName != household) {
                Response(FORBIDDEN).body("Household $household is not allowed to update the service contract of ID $id")
            } else {
                val previous = contracts.put(contract.id, contract)
                val status = if (previous == null) CREATED else OK

                println("Number of service contracts stored = ${contracts.size}")
                Response(status).with(serviceContractLens of contract)
            }
        },
        "/contracts/{id}/agreedAt" bind PATCH to { request ->
            val household = householdHeader(request)
            val id = request.path("id")?.toInt()
            val contract = id?.let { contracts[id] }

            if (contract == null) {
                Response(NOT_FOUND).body("Service contract of ID $id not found")
            } else if (contract.partyA.householdName != household && contract.partyB.householdName != household) {
                Response(FORBIDDEN).body("Household $household is not allowed to see the service contract of ID $id")
            } else {
                val now = Instant.now()
                val revisedContract =
                    if (contract.partyA.householdName == household) {
                        contract.copy(partyA = contract.partyA.copy(agreedAt = now))
                    } else {
                        contract.copy(partyB = contract.partyB.copy(agreedAt = now))
                    }

                contracts[contract.id] = revisedContract

                println("Service contracts agreed = $revisedContract")
                Response(NO_CONTENT).with(serviceContractLens of contract)
            }
        },
    )

val contracts = ConcurrentHashMap<Int, ServiceContract>()

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)
    val server = printingApp.asServer(Undertow(9000)).start()
    println("Server started on " + server.port())
}
