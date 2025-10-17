package ar.edu.unsam.algo3.service

import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.dto.LocalDTO
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.LocalRepositorio

@Service
class LocalService(private val localRepositorio: LocalRepositorio) {

    fun obtenerLocalPorId(id: Int): LocalDTO {
        val local = localRepositorio.getById(id)
        return LocalDTO(
            id = local.id!!,
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
    }

    fun actualizarLocalDesdeDTO(localDTO: LocalDTO): LocalDTO {
        val local = localRepositorio.getById(localDTO.id) // obtener el local existente
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
        localDTO.mediosDePago.forEach { medio -> local.agregarMedioDePago(medio) }

        return obtenerLocalPorId(local.id!!)
    }

} // Fin clase LocalService