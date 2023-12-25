package data

import domain.Ticket
import validation.model.Result

interface TicketStorage {
    fun save(ticket: Ticket)
    fun get(id: Long): Result
    fun getAll(): List<Ticket>
    fun delete(id: Long): Result
}