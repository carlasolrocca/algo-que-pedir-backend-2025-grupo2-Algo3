package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Usuario

data class ClienteInfoDTO (
    val nombre : String,
    val username : String,
)

fun Usuario.toDTO() : ClienteInfoDTO =
    ClienteInfoDTO(
        nombre = this.devolverNombreCompleto(),
        username = this.username,
    )