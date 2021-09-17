package com.example.water_nn.data.database.entity

inline fun <reified T : Enum<T>> safeValueOf(type: String?): T? {
    return type?.let { tryOrNull { java.lang.Enum.valueOf(T::class.java, type) } }
}

inline fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (ex: Throwable) {
        null
    }