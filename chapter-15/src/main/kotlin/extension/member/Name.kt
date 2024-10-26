package extension.member

data class Name(val value: String) {
    fun toJson(): String = "{\"name\":\"$value\"}"
}