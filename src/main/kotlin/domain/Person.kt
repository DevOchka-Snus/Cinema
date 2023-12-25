package domain

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Long,
    val login: String,
    val password: String,
    val role: Role
)
