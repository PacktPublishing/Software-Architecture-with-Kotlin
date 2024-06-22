package connect

import org.http4k.core.ContentType
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.contentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConnectTest {
    @Test
    fun `Get the household from Household API`() {
        assertEquals(
            Response(OK)
                .contentType(ContentType.APPLICATION_JSON)
                .body("{\"name\":\"Whittington\",\"emailAddress\":\"same.address@domain.com\"}"),
            app(Request(GET, "/households/Whittington")),
        )
    }
}
