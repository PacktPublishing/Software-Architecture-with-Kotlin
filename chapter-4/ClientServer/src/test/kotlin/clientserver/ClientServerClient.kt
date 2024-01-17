package clientserver

import clientserver.formats.Party
import clientserver.formats.ServiceContract
import clientserver.formats.serviceContractLens
import org.http4k.client.OkHttp
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.PUT
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintResponse
import org.http4k.format.Jackson.asJsonObject
import org.http4k.format.Jackson.json

fun main() {
    val client: HttpHandler = OkHttp()

    val printingClient: HttpHandler = PrintResponse().then(client)

    val response: Response = printingClient(Request(GET, "http://localhost:9000/ping"))

    println(response.bodyString())

    val initialContractDraftedByHouseholdA =
        ServiceContract(
            id = 1,
            partyA = Party("A", "Plumbing"),
            partyB = Party("B", "Cleaning"),
        )

    printingClient(
        Request(PUT, "http://localhost:9000/contracts/1").with(
            householdHeader of "A",
            Body.json().toLens() of
                initialContractDraftedByHouseholdA.asJsonObject(),
        ),
    )

    val contractReceivedByB =
        serviceContractLens(
            printingClient(
                Request(GET, "http://localhost:9000/contracts/1?household=B"),
            ),
        )

    val contractRevisedByB =
        contractReceivedByB.copy(
            partyB = contractReceivedByB.partyB.copy(service = "Babysitting"),
        )

    printingClient(
        Request(PUT, "http://localhost:9000/contracts/1").with(
            householdHeader of "B",
            Body.json().toLens() of
                contractRevisedByB.asJsonObject(),
        ),
    )

    val contractReceivedByA =
        serviceContractLens(
            printingClient(
                Request(GET, "http://localhost:9000/contracts/1?household=A"),
            ),
        )

    printingClient(Request(PATCH, "http://localhost:9000/contracts/1/agreedAt").header("household", "A"))
    println("Household A agreed with the service contract of ID ${contractReceivedByA.id}")

    val revisedContractReceivedByB =
        serviceContractLens(
            printingClient(
                Request(GET, "http://localhost:9000/contracts/1?household=B"),
            ),
        )

    if (revisedContractReceivedByB.partyA.agreedAt != null) {
        printingClient(Request(PATCH, "http://localhost:9000/contracts/1/agreedAt").header("household", "B"))
        println("Household B agreed with the service contract of ID ${revisedContractReceivedByB.id}")
    }
}
