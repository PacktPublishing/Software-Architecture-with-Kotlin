package extension.repository

import extension.Name
import extension.dto.toJson

internal fun Name.toInsertSql(): String =
    "insert into"

fun foo2() {
    Name("asd").toJson()
}

