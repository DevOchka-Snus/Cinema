package data.impl

import data.Storage
import data.TicketStorage
import domain.OutputModel
import domain.Ticket
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.io.File

class TicketStorageImpl(
    private val path: String
): Storage(path), TicketStorage {

    override fun save(ticket: Ticket) {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val tickets = Json.decodeFromString<MutableList<Ticket>>(jsonText).toMutableList()
        tickets.add(ticket)
        val jsonString = Json.encodeToString(tickets)
        jsonFile.writeText(jsonString)
    }

    override fun get(id: Long): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val loadedTickets: MutableList<Ticket> = Json.decodeFromString<MutableList<Ticket>>(jsonText).toMutableList()
        val ticket = loadedTickets.find { it.id == id }
        if (ticket == null) {
            return Error(OutputModel("Билет не найден"))
        }
        return Success(ticket)
    }

    override fun getAll(): List<Ticket> {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        return Json.decodeFromString<List<Ticket>>(jsonText).toList()
    }

    override fun delete(id: Long): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val loadedTickets: MutableList<Ticket> = Json.decodeFromString<MutableList<Ticket>>(jsonText).toMutableList()
        val ticket = loadedTickets.find { it.id == id }
        if (ticket == null) {
            return Error(OutputModel("Билет не найден"))
        }
        loadedTickets.remove(ticket)
        val updatedJsonText = Json.encodeToString(loadedTickets)
        jsonFile.writeText(updatedJsonText)
        return Success(ticket)
    }
}