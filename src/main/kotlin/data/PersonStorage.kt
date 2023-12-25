package data

import domain.Person
import domain.Role
import validation.model.Result

interface PersonStorage {
    fun save(person: Person)
    fun get(login: String, password: String, role: Role): Result
}