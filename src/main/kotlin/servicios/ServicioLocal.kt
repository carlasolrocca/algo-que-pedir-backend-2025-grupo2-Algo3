package ar.edu.unsam.algo3.service

import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.dto.LocalDTO
import org.springframework.stereotype.Service

@Service
class LocalService {

    private val local = Local(
        nombre = "Taberna de Moe",
        urlImagenLocal = "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
        direccion = Direccion(calle = "Av. Siempre Viva", altura = 742, ubicacion = Point(39.808327, -89.643204)),
        porcentajeSobreCadaPlato = 10.0,
        porcentajeRegaliasDeAutor = 5.0
    ).apply {
        agregarMedioDePago(MedioDePago.EFECTIVO)
        agregarMedioDePago(MedioDePago.TRANSFERENCIA_BANCARIA)
    }

    fun obtenerLocalDTO(): LocalDTO {
        return LocalDTO(
            id = local.id ?: 0,
            nombre = local.nombre,
            urlImagenLocal = local.urlImagenLocal,
            direccion = local.direccion.calle,
            altura = local.direccion.altura,
            latitud = local.direccion.ubicacion.latitude(),
            longitud = local.direccion.ubicacion.longitude(),
            porcentajeSobreCadaPlato = local.porcentajeSobreCadaPlato,
            porcentajeRegaliasDeAutor = local.porcentajeRegaliasDeAutor,
            mediosDePago = local.mediosDePago
        )

        //Cambiar esta responsabilidad al Controller, y modelarlo con un ID de Local
    }

fun actualizarLocalDesdeDTO(localDTO: LocalDTO): LocalDTO {

    local.nombre = localDTO.nombre
    local.urlImagenLocal = localDTO.urlImagenLocal
    local.direccion = Direccion(
        calle = localDTO.direccion,
        altura = localDTO.altura,
        ubicacion = Point(localDTO.latitud, localDTO.longitud)
    )
    local.porcentajeSobreCadaPlato = localDTO.porcentajeSobreCadaPlato
    local.porcentajeRegaliasDeAutor = localDTO.porcentajeRegaliasDeAutor

    local.mediosDePago.clear()
    localDTO.mediosDePago.forEach { medio ->
        local.agregarMedioDePago(medio)
    }

    return obtenerLocalDTO()
}
}