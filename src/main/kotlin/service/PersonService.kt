package service

import domain.Person
import domain.Role
import validation.model.Result

interface PersonService {
    fun register(person: Person): Result
    fun login(login: String, password: String, role: Role): Result
}