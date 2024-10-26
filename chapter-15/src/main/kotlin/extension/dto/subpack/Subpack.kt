package extension.dto.subpack

import extension.Name
import extension.dto.toJson

fun foo() {
    Name("abc").toJson()
}