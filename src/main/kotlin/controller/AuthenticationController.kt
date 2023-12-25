package controller

import domain.Role

interface AuthenticationController {
    fun register(role: Role)
    fun login(role: Role): Long
    fun logout(): Boolean
}
