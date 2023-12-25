package data

import com.fasterxml.jackson.databind.ObjectMapper

abstract class Storage(
    private val path: String
)