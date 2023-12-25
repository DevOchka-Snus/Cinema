package data.impl

import data.MovieSessionStorage
import data.Storage
import domain.MovieSession
import domain.OutputModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.io.File
import java.time.LocalDateTime

class MovieSessionStorageImpl(
    private val path: String
): Storage(path), MovieSessionStorage {
    override fun save(movieSession: MovieSession) {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val movieSessions: MutableList<MovieSession> = Json.decodeFromString<MutableList<MovieSession>>(jsonText).toMutableList()
        movieSessions.add(movieSession)
        val jsonString = Json.encodeToString(movieSessions)
        jsonFile.writeText(jsonString)
    }

    override fun get(id: Long): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val movieSessions: MutableList<MovieSession> = Json.decodeFromString<MutableList<MovieSession>>(jsonText).toMutableList()
        val movieSession = movieSessions.find { it.id == id }
        if (movieSession == null) {
            return Error(OutputModel("сеанс не найден"))
        }
        return Success(movieSession)
    }

    override fun getAll(): List<MovieSession> {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val movieSessions: MutableList<MovieSession> = Json.decodeFromString<MutableList<MovieSession>>(jsonText).toMutableList()
        return movieSessions.filter {
            it.sessionBegin.isAfter(LocalDateTime.now())
        }
    }

    override fun update(movieSession: MovieSession): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val movieSessions: MutableList<MovieSession> = Json.decodeFromString<MutableList<MovieSession>>(jsonText).toMutableList()
        val movieSessionToUpdate = movieSessions.find { it.id == movieSession.id }
        if (movieSessionToUpdate == null) {
            return Error(OutputModel("сеанс не найден"))
        }
        movieSessions.remove(movieSessionToUpdate)
        movieSessions.add(movieSession)
        val jsonString = Json.encodeToString(movieSessions)
        jsonFile.writeText(jsonString)
        return Success(movieSession)
    }
}