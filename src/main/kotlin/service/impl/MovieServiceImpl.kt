package service.impl

import data.MovieStorage
import domain.Movie
import domain.OutputModel
import service.MovieService
import validation.model.Error
import validation.model.Result
import validation.model.Success

class MovieServiceImpl(
    private val movieStorage: MovieStorage
): MovieService {
    override fun create(movie: Movie) {
        movieStorage.save(movie)
    }

    override fun get(id: Long): Result {
        return movieStorage.get(id)
    }

    override fun getAll(): List<Movie> {
        return movieStorage.getAll()
    }

    override fun getAllForUpdate(): List<Movie> {
        val allMovies = movieStorage.getAll()
        return allMovies.filter { it.isMutable }
    }

    override fun update(movie: Movie): Result {
        return movieStorage.update(movie)
    }
}