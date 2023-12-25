package data.impl

import data.PersonStorage
import data.Storage
import domain.OutputModel
import domain.Person
import domain.Role
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import validation.model.Error
import validation.model.Result
import validation.model.Success
import java.io.File

class PersonStorageImpl(
    private val path: String
): Storage(path), PersonStorage {
    override fun save(person: Person) {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val persons = Json.decodeFromString<MutableList<Person>>(jsonText).toMutableList()
        persons.add(person)
        val jsonString = Json.encodeToString(persons)
        jsonFile.writeText(jsonString)
    }

    override fun get(login: String, password: String, role: Role): Result {
        val jsonFile = File(path)
        val jsonText = jsonFile.readText()
        val persons = Json.decodeFromString<MutableList<Person>>(jsonText).toMutableList()
        val person = persons.find {
            it.login == login && it.password == password && it.role == role
        }
        if (person == null) {
            return Error(OutputModel("Учетная запись не найдена"))
        }
        return Success(person)
    }

}