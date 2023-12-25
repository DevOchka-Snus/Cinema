package service

import domain.MovieSession
import domain.Ticket
import validation.model.Result

interface TicketService {
    fun sellTicket(session: MovieSession, seatNumber: Int, clientId: Long): Ticket
    fun refundTicket(ticketId: Long): Result
    fun getAll(): List<Ticket>
    fun checkTicket(id: Long): Result
    fun getAllByClientId(clientId: Long): List<Ticket>
}