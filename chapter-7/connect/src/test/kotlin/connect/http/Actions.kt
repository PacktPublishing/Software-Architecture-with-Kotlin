package connect.http

import connect.Household
import connect.HouseholdApiAction
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.format.Jackson.auto

val householdLens = Body.auto<Household>().toLens()

data class GetHousehold(
    val householdName: String,
) : HouseholdApiAction<Household> {
    override fun toRequest(): Request = Request(Method.GET, "/households/$householdName")

    override fun fromResponse(response: Response): Household = householdLens(response)
}
