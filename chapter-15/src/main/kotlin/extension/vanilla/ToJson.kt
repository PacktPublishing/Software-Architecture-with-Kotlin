package extension.vanilla

import extension.Name

internal fun toJson(name: Name): String = "{\"name\":\"${name.value}\"}"
