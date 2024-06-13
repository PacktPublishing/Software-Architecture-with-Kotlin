package connect

import org.http4k.core.Request
import org.http4k.core.Response

interface HouseholdApiAction<R> {
    fun toRequest(): Request

    fun fromResponse(response: Response): R
}
