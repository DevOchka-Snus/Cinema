package service

import domain.Movie
import validation.model.Result

interface MovieService {
    fun create(movie: Movie)
    fun get(id: Long): Result
    fun getAll(): List<Movie>
    fun getAllForUpdate(): List<Movie>
    fun update(movie: Movie): Result
}