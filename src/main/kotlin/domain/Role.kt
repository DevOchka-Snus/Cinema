package domain

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    CLIENT,
    WORKER
}
