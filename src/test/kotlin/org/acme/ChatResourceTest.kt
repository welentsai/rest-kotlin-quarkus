package org.acme

import io.quarkus.test.common.http.TestHTTPResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URI
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit
import javax.websocket.*

@QuarkusTest
class ChatResourceTest {

    val MESSAGES: LinkedBlockingDeque<String> = LinkedBlockingDeque<String>()

    @TestHTTPResource("/chat/stu")
    val uri: URI = URI.create("")

    @Test
    fun testWebsocketChat() {

        val session: Session = ContainerProvider.getWebSocketContainer().connectToServer(Client(), uri)
        session.use {
            Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));
            Assertions.assertEquals("User stu joined", MESSAGES.poll(10, TimeUnit.SECONDS));
            session.getAsyncRemote().sendText("hello world");
            Assertions.assertEquals(">> stu: hello world", MESSAGES.poll(10, TimeUnit.SECONDS));
        }
    }

    @ClientEndpoint
    inner class Client {

        @OnOpen
        fun open(session: Session) {
            println("Connected !!")
            MESSAGES.add("CONNECT");
            session.getAsyncRemote().sendText("_ready_")
        }

        @OnMessage
        fun message(msg: String) {
            println("Message = " + msg)
            MESSAGES.add(msg);
        }
    }
}