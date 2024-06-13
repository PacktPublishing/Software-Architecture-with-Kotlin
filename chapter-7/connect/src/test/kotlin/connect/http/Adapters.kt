package connect.http

import connect.HouseholdApiAction
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom

interface HouseholdApi {
    operator fun <R : Any> invoke(action: HouseholdApiAction<R>): R

    companion object
}

val token = "fakeToken"

fun HouseholdApi.Companion.Http(client: HttpHandler) =
    object : HouseholdApi {
        private val http =
            SetBaseUriFrom(Uri.of("http://localhost:9000"))
                .then(
                    Filter { next -> { next(it.header("Authorization", "Bearer $token")) } },
                ).then(client)

        override fun <R : Any> invoke(action: HouseholdApiAction<R>) = action.fromResponse(http(action.toRequest()))
    }

fun HouseholdApi.getHousehold(householdName: String) = invoke(GetHousehold(householdName))
