package di

import controller.AuthenticationController
import controller.CinemaWorkerController
import controller.ClientController
import controller.impl.AuthenticationControllerImpl
import controller.impl.CinemaWorkerControllerImpl
import controller.impl.ClientControllerImpl
import data.MovieSessionStorage
import data.MovieStorage
import data.PersonStorage
import data.TicketStorage
import data.impl.MovieSessionStorageImpl
import data.impl.MovieStorageImpl
import data.impl.PersonStorageImpl
import data.impl.TicketStorageImpl
import service.MovieService
import service.MovieSessionService
import service.PersonService
import service.TicketService
import service.impl.MovieServiceImpl
import service.impl.MovieSessionServiceImpl
import service.impl.PersonServiceImpl
import service.impl.TickerServiceImpl
import validation.Validator
import validation.impl.ValidatorImpl
import view.UserInterface
import view.UserInterfaceImpl

object DI {
    private val validator:Validator = ValidatorImpl()

    private val personStorage: PersonStorage = PersonStorageImpl("./src/main/resources/storage/persons.json")
    private val movieStorage: MovieStorage = MovieStorageImpl("./src/main/resources/storage/movies.json")
    private val movieSessionStorage: MovieSessionStorage = MovieSessionStorageImpl("./src/main/resources/storage/movie_sessions.json")
    private val ticketStorage: TicketStorage = TicketStorageImpl("./src/main/resources/storage/tickets.json")

    private val personService: PersonService = PersonServiceImpl(personStorage)
    private val movieService: MovieService = MovieServiceImpl(movieStorage)
    private val movieSessionService: MovieSessionService = MovieSessionServiceImpl(movieSessionStorage, movieService)
    private val ticketService: TicketService = TickerServiceImpl(ticketStorage, movieSessionService)

    private val authenticationController: AuthenticationController = AuthenticationControllerImpl(personService, validator)
    private val cinemaWorkerController: CinemaWorkerController = CinemaWorkerControllerImpl(validator, movieService, movieSessionService, ticketService)
    private val clientController: ClientController = ClientControllerImpl(validator, movieSessionService, ticketService)

    val userInterface: UserInterface = UserInterfaceImpl(validator, authenticationController, clientController, cinemaWorkerController)
}