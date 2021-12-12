package org.acme.resource

import org.acme.handler.ChatHandler
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
class ChatResource {
    val logger = LoggerFactory.getLogger(ChatResource::class.java)
    val sessions: ConcurrentHashMap<String, Session> = ConcurrentHashMap<String, Session>()

    @OnOpen
    fun onOpen(session: Session, @PathParam("username") username: String) {
//        logger.info("user {} is onOpen", username)
        sessions.put(username, session)
    }

    @OnClose
    fun onClose(session: Session, @PathParam("username") username: String) {
        logger.info("user {} is onClose", username)
    }

    @OnError
    fun onError(session: Session, @PathParam("username") username: String, throwable: Throwable) {
//        logger.info("user {} is onError", username)
    }

    @OnMessage
    fun onMessage(message: String, @PathParam("username") username: String) {
//        logger.info("user {} is onMessage, message is {}", username, message)
        if (message.equals("_ready_", ignoreCase = true)) {
            broadcast("User " + username + " joined")
        } else {
            broadcast(">> " + username + ": " + message)
        }

    }

    fun broadcast(message: String) {
        sessions.forEach { user, session ->
            session.asyncRemote.sendObject(message, ChatHandler)
        }
    }


}