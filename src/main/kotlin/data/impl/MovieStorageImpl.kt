package data.impl

import data.MovieStorage
import data.Storage
import domain.Movie
import domain.OutputModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.io.File

class MovieStorageImpl(
    private val path: String
) : Storage(path), MovieStorage {

    override fun save(movie: Movie) {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val movies: MutableList<Movie> = Json.decodeFromString<MutableList<Movie>>(jsonText).toMutableList()
        movies.add(movie)
        val jsonString = Json.encodeToString(movies)
        jsonFile.writeText(jsonString)
    }

    override fun get(id: Long): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val loadedMovies: MutableList<Movie> = Json.decodeFromString<MutableList<Movie>>(jsonText).toMutableList()
        if (loadedMovies.isEmpty()) {
            return Error(OutputModel("Нет такого фильма"))
        }
        val targetMovie = loadedMovies.find { it.id == id }
        if (targetMovie == null) {
            return Error(OutputModel("Нет такого фильма"))
        }
        return Success(targetMovie)
    }

    override fun update(movie: Movie): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val loadedMovies: MutableList<Movie> = Json.decodeFromString<MutableList<Movie>>(jsonText).toMutableList()
        val movieToUpdate = loadedMovies.find { it.id == movie.id }
        if (movieToUpdate == null) {
            return Error(OutputModel("Нет такого фильма"))
        } else if (!movieToUpdate.isMutable) {
            return Error(OutputModel("Фильм нельзя редактировать"))
        }
        loadedMovies.remove(movieToUpdate)
        loadedMovies.add(movie)
        val jsonString = Json.encodeToString(loadedMovies)
        jsonFile.writeText(jsonString)
        return Success(movie)
    }

    override fun getAll(): List<Movie> {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        return Json.decodeFromString<List<Movie>>(jsonText).toList()
    }
}