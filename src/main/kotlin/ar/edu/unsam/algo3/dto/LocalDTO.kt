package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.MedioDePago
import jakarta.validation.constraints.*

data class LocalDTO(

    //Cambiar las validaciones a la clase del Local
    @field:NotBlank(message = "El nombre no puede estar vacío")
    val nombre: String,

    @field:NotBlank(message = "La URL de la imagen no puede estar vacía")
    val urlImagenLocal: String,

    @field:NotBlank(message = "La dirección no puede estar vacía")
    val direccion: String,

    @field:Min(value = 1, message = "La altura debe ser mayor a 0")
    val altura: Int,

    @field:DecimalMin(value = "-90.0", message = "Latitud mínima -90")
    @field:DecimalMax(value = "90.0", message = "Latitud máxima 90")
    val latitud: Double,

    @field:DecimalMin(value = "-180.0", message = "Longitud mínima -180")
    @field:DecimalMax(value = "180.0", message = "Longitud máxima 180")
    val longitud: Double,

    @field:DecimalMin(value = "0.0", message = "El porcentaje debe estar entre 0 y 100")
    @field:DecimalMax(value = "100.0", message = "El porcentaje debe estar entre 0 y 100")
    val porcentajeSobreCadaPlato: Double,

    @field:DecimalMin(value = "0.0", message = "El porcentaje debe estar entre 0 y 100")
    @field:DecimalMax(value = "100.0", message = "El porcentaje debe estar entre 0 y 100")
    val porcentajeRegaliasDeAutor: Double,

    val mediosDePago: Set<MedioDePago>
)