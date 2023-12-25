package controller

interface ClientController {
    fun buyTicket(clientId: Long)
    fun refundTicket(clientId: Long)
    fun printAllTicketById(clientId: Long)
}