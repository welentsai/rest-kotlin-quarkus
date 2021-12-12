package org.acme.handler

import javax.websocket.SendHandler
import javax.websocket.SendResult

object ChatHandler : SendHandler {
    override fun onResult(result: SendResult?) {
//        result?.exception?.let { println("Unable to send message: ${result?.exception}") }
        result?.exception?.also { println("Unable to send message: ${result?.exception}") }
    }
}