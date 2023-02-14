package es.dam.biques.microserviciousuarios.config.websocket


interface WebSocketSender {
    fun sendMessage(message: String)
    fun sendPeriodicMessages()
}