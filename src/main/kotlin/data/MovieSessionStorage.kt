package data

import domain.MovieSession
import validation.model.Result

interface MovieSessionStorage {
    fun save(movieSession: MovieSession)
    fun get(id: Long): Result
    fun getAll(): List<MovieSession>
    fun update(movieSession: MovieSession): Result
}