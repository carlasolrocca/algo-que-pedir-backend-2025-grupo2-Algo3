package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.Usuario

data class ClienteInfoDTO (
    val nombre : String,
    val username : String,
    val direccion : String
)

fun Usuario.toDTO() : ClienteInfoDTO =
    ClienteInfoDTO(
        nombre = "${this.nombre} ${this.apellido}".trim(),
        username = this.username,
        direccion = this.direccion.devolverDireccionCompleta()
    )