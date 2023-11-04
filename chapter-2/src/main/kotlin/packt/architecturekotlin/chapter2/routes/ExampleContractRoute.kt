package packt.architecturekotlin.chapter2.routes

import org.http4k.contract.ContractRoute
import org.http4k.contract.meta
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto

data class NameAndMessage(val name: String, val message: String)

// the body lens here is imported as an extension function from the Jackson instance
val nameAndMessageLens = Body.auto<NameAndMessage>().toLens()

object ExampleContractRoute {
    // this specifies the route contract, including examples of the input and output body objects - they will
    // get exploded into JSON schema in the OpenAPI docs
    private val spec = "/echo" meta {
        summary = "echoes the name and message sent to it"
        receiving(nameAndMessageLens to NameAndMessage("jim", "hello!"))
        returning(OK, nameAndMessageLens to NameAndMessage("jim", "hello!"))
    } bindContract POST

    // note that because we don't have any dynamic parameters, we can use a HttpHandler instance instead of a function
    private val echo: HttpHandler = { request: Request ->
        val received: NameAndMessage = nameAndMessageLens(request)
        Response(OK).with(nameAndMessageLens of received)
    }

    operator fun invoke(): ContractRoute = spec to echo
}
