package service.impl

import data.MovieSessionStorage
import domain.Movie
import domain.MovieSession
import domain.OutputModel
import domain.SeatStatus
import service.MovieService
import service.MovieSessionService
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.time.LocalDateTime

class MovieSessionServiceImpl(
    private val movieSessionStorage: MovieSessionStorage,
    private val movieService: MovieService
): MovieSessionService {
    override fun create(movieSession: MovieSession): Result {
        val sessions = getAll()
        for (session in sessions) {
            if (!movieSession.sessionEnd.isBefore(session.sessionBegin)
                && !session.sessionEnd.isBefore(movieSession.sessionBegin)) {
                return Error(OutputModel("Пересечение с другим сеансом"))
            }
        }
        val result = movieService.update(movieSession.movie)
        if (result is Error) {
            return result
        }
        movieSessionStorage.save(movieSession)
        return Success(movieSession)
    }

    override fun get(id: Long): Result {
        return movieSessionStorage.get(id)
    }

    override fun getAll(): List<MovieSession> {
        return movieSessionStorage.getAll()
    }

    override fun getAllForUpdate(): List<MovieSession> {
        return movieSessionStorage.getAll().filter { it.isMutable }
    }

    override fun getAllForBuyingTicket(): List<MovieSession> {
        return movieSessionStorage.getAll().filter {
            movieSession -> movieSession.sessionEnd.isAfter(LocalDateTime.now()) &&
                movieSession.cinemaHall.seatList.filter { it == SeatStatus.FREE }.any() }
    }

    override fun update(movieSession: MovieSession): Result {
        if (movieSession.movie.isMutable) {
            movieSession.movie.isMutable = false
            movieService.update(movieSession.movie)
        }
        return movieSessionStorage.update(movieSession)
    }
}