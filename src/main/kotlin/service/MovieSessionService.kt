package service

import domain.MovieSession
import validation.model.Result

interface MovieSessionService {
    fun create(movieSession: MovieSession): Result
    fun get(id: Long): Result
    fun getAll(): List<MovieSession>
    fun getAllForUpdate(): List<MovieSession>
    fun getAllForBuyingTicket(): List<MovieSession>
    fun update(movieSession: MovieSession): Result
}