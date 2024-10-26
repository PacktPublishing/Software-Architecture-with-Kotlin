package extension.nonlocal

import extension.Name

fun Name.toJson(): String =
    "{\"name\":\"$value\"}"
