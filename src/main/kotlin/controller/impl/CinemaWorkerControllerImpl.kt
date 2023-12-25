package controller.impl

import controller.CinemaWorkerController
import domain.*
import service.MovieService
import service.MovieSessionService
import service.TicketService
import validation.Validator
import validation.model.Error
import java.util.*

class CinemaWorkerControllerImpl(
    private val validator: Validator,
    private val movieService: MovieService,
    private val movieSessionService: MovieSessionService,
    private val ticketService: TicketService
): CinemaWorkerController {
    override fun create(type: Type) {
        when (type) {
            Type.MOVIE -> createMovie()
            Type.SESSION -> createMovieSession()
        }
    }

    private fun createMovieSession() {
        val id = UUID.randomUUID().mostSignificantBits
        val movies = movieService.getAll()
        if (movies.isEmpty()) {
            println("У вас нет фильмов, для которых можно создать сеанса")
            return
        }
        val movie = chooseMovie(movies)
        val cinemaHall = CinemaHall(mutableListOf())
        do {
            val timePair = getSessionBeginAndEnd()
            movie.isMutable = false
            val result = movieSessionService.create(
                MovieSession(
                    id,
                    movie,
                    cinemaHall,
                    timePair.first,
                    timePair.second,
                    true
                )
            )
            if (result is Error) {
                println(result.outputModel.message)
                continue
            }
            println("Сеанс добавлен")
            return
        } while (true)
    }

    private fun getSessionBeginAndEnd(): Pair<SessionDateTime, SessionDateTime> {
        do {
            val sessionBegin = validator.validateTime("Введите время начала сеанса в формате 'yyyy MM dd HH:mm'")
            val sessionEnd = validator.validateTime("Введите время конца сеанса в формате 'yyyy MM dd HH:mm'")
            if (sessionEnd.isBefore(sessionBegin)) {
                println("Начало сеанса должно быть раньше конца")
                continue
            }
            return Pair(sessionBegin, sessionEnd)
        } while (true)
    }

    private fun chooseMovie(movies: List<Movie>): Movie {
        println(" Спиcoк доступных фильмов:")
        for (movie in movies) {
            println(movie)
        }
        do {
            val id = validator.validateLong("Выберите фильм и введите его номер")
            for (movie in movies) {
                if (movie.id == id) {
                    return movie
                }
            }
            println("Нет фильма с таким номером")
        } while (true)
    }

    private fun createMovie() {
        val name = validator.validateString("Введите название фильма:")
        val genre = validator.validateString("Введите жанр фильма")
        val director = validator.validateString("Введите режиссера фильма")
        movieService.create(
            Movie(
                UUID.randomUUID().mostSignificantBits,
                name,
                genre,
                director,
                true
            )
        )
        println("Фильм добавлен")
    }

    override fun update(type: Type) {
        when (type) {
            Type.MOVIE -> updateMovie()
            Type.SESSION -> updateSession()
        }
    }

    private fun updateSession() {
        val sessions = movieSessionService.getAllForUpdate()
        if (sessions.isEmpty()) {
            println("У вас не сеансов для обновления")
            return
        }
        val session = chooseMovieSession(sessions)
        val movies = movieService.getAll()
        val movie = chooseMovie(movies)
        movie.isMutable = false
        val timePair = getSessionBeginAndEnd()
        do {
            val updated = MovieSession(
                session.id,
                movie,
                session.cinemaHall,
                timePair.first,
                timePair.second,
                true
            )
            var result = movieSessionService.update(updated)
            if (result is Error) {
                println(result.outputModel.message)
                continue
            }
            println("Сеанс отредактирован")
            return
        } while (true)
    }

    private fun chooseMovieSession(sessions: List<MovieSession>): MovieSession {
        println("Список доступных сеансов")
        for (movieSession in sessions) {
            println(movieSession)
        }

        do {
            var id = validator.validateLong("Выберите сеанс и введите его номер")
            for (movieSession in sessions) {
                if (movieSession.id == id) {
                    return movieSession
                }
            }
            println("Нет сеанса с таким номером")
        } while (true)
    }

    private fun updateMovie() {
        val movies = movieService.getAllForUpdate()
        if (movies.isEmpty()) {
            println("У вас не фильмов для редактирования")
            return
        }
        val movie = chooseMovie(movies)
        val name = validator.validateString("Введите новое название фильма:")
        val genre = validator.validateString("Введите новый жанр фильма")
        val director = validator.validateString("Введите нового режиссера фильма")
        val updatedMovie = Movie(
            movie.id,
            name,
            genre,
            director,
            true
        )
        movieService.update(updatedMovie)
        println("Фильм отредактирован")
    }

    override fun checkTicket() {
        do {
            var id = validator.validateLong("Введите номер билета")
            var result = ticketService.checkTicket(id)
            if (result is Error) {
                println(result.outputModel.message)
                continue
            }
            println("Билет все еще действителен")
            return
        } while (true)
    }
}