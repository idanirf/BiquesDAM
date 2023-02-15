package es.dam.biques.microserviciousuarios.config.websocket

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.SubProtocolCapable
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.IOException
import java.time.LocalTime
import java.util.concurrent.CopyOnWriteArraySet

private val logger = KotlinLogging.logger {}


class WebSocketHandler(private val entity: String) : TextWebSocketHandler(), SubProtocolCapable, WebSocketSender {

    private val sessions: MutableSet<WebSocketSession> = CopyOnWriteArraySet()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "Connection established with the server." }
        logger.info { "Sesión: $session" }
        sessions.add(session)
        val message = TextMessage("Updates Web socket: $entity - Users API REST Spring Boot")
        logger.info { "Server send: $message" }
        session.sendMessage(message)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "Connection closed with the server: $status" }
        sessions.remove(session)
    }

    override fun sendMessage(message: String) {
        logger.info { "Send message of changes in $entity: $message" }
        sessions.forEach { session ->
            if (session.isOpen) {
                logger.info { "Server send: $message" }
                session.sendMessage(TextMessage(message))
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    @Throws(IOException::class)
    override fun sendPeriodicMessages() {
        for (session in sessions) {
            if (session.isOpen) {
                val broadcast = "server periodic message " + LocalTime.now()
                logger.info("Server sends: {}", broadcast)
                session.sendMessage(TextMessage(broadcast))
            }
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        logger.info { "Transport error with server ${exception.message}" }
    }

    override fun getSubProtocols(): List<String> {
        return listOf("subprotocol.demo.websocket")
    }
}
