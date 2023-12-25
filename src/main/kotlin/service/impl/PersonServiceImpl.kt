package service.impl

import data.PersonStorage
import domain.Person
import domain.OutputModel
import domain.Role
import service.PersonService
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.util.Base64

class PersonServiceImpl(
    private val personStorage: PersonStorage
): PersonService {
    override fun register(person: Person): Result {
        val result = personStorage.get(person.login, person.password, person.role)
        if (result is Error) {
            personStorage.save(person)
            return Success(person)
        } else {
            return Error(OutputModel("Данные логин и пароль уже заняты"))
        }
    }

    override fun login(login: String, password: String, role: Role): Result {
        val encodedPassword: String = Base64.getEncoder().encodeToString(password.toByteArray())
        return personStorage.get(login, encodedPassword, role)
    }

}