package extension.dto

import extension.Name

internal fun Name.toJson(): String =
    "{\"name\":\"$value\"}"

object NameJsonConverter {
    fun toJson(name: Name): String = "{\"name\":\"${name.value}\"}"
}