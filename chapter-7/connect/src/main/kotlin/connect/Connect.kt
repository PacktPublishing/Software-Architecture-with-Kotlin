package connect

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

val householdLens = Body.auto<Household>().toLens()

val app: HttpHandler =
    routes(
        "/households/{name}" bind GET to { request ->
            val householdName = request.path("name")!!.toString()
            Response(OK).with(householdLens of Household(name = householdName, emailAddress = "same.address@domain.com"))
        },
    )

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}
