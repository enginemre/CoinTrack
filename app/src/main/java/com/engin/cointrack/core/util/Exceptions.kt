package com.engin.cointrack.core.util

import java.io.IOException

sealed class Exceptions : IOException() {
    data class NetworkException(override val message: String) : Exceptions()
    data class UnknownException(override val message: String) : Exceptions()
}