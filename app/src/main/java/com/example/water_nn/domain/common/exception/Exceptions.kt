package com.example.water_nn.domain.common.exception

open class MessageException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception() {
    override fun toString() = "MessageException: $message"
}

class MessageIdException(
    val messageId: Int,
    override val cause: Throwable? = null
) : Exception()

open class CodeException(open val code: Int) : Exception()

// todo: need to handle
//class PermissionException(val permission: Permission) : Exception()

class ConnectionException(override val cause: Exception?) : Exception()

open class ServerException(
    override val message: String? = null,
    val error: String? = null,
    val code: Int
) : Exception()

object AuthException : Exception()

class MapperException(override val message: String?) : Exception(message)