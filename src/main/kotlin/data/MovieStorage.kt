package data

import domain.Movie
import validation.model.Result

interface MovieStorage {
    fun save(movie: Movie)
    fun get(id: Long): Result
    fun getAll(): List<Movie>
    fun update(movie: Movie): Result
}