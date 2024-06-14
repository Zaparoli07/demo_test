package com.example.demo.vo

data class Address(
    val street: String? = null,
    val number: String? = null,
    val complement: String? = null,
    val district: String? = null,
    val cep: String? = null
) {
    init {
        requireNotNull(street) {
            "Street must be informed."
        }
        requireNotNull(number) {
            "Number must be informed."
        }
    }
}
