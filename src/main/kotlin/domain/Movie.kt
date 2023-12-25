package domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie (
    val id: Long,
    val name: String,
    val genre: String,
    val director: String,
    var isMutable: Boolean
) {
    override fun toString(): String {
        return "Номер фильма: $id\nНазвание: $name\nЖанр: $genre\nРежиссер: $director"
    }
}
