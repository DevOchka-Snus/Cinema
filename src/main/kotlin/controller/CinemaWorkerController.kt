package controller

import domain.Type

interface CinemaWorkerController {
    fun create(type: Type)
    fun update(type: Type)
    fun checkTicket()
}