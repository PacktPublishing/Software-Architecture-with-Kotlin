package connect.http

import connect.Household
import org.http4k.client.JavaHttpClient

fun main() {
    val householdApi = HouseholdApi.Http(JavaHttpClient())
    val household: Household = householdApi.getHousehold("Whittington")
    println(household)
}
