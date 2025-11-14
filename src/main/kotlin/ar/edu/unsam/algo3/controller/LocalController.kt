package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.dto.LocalClienteDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.PlatoClienteDTO
import ar.edu.unsam.algo3.dto.toDto
import ar.edu.unsam.algo3.service.LocalService
import ar.edu.unsam.algo3.dto.toClienteDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.map

@RestController
@CrossOrigin(
    origins = ["http://localhost:5173"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS]
)
class LocalController(private val localService: LocalService) {

    @GetMapping("/localAdmin/{id}")
    fun obtenerLocalPorId(@PathVariable id: Int): LocalDTO {
        return localService.obtenerLocalPorId(id).toDto()
    }

    @PutMapping("/local")
    fun actualizarLocal(@RequestBody localDTO: LocalDTO): LocalDTO {
        println("DTO recibido: ${localDTO}")
        return localService.actualizarLocalDesdeDTO(localDTO).toDto()
    }

    @GetMapping("/locales")
    fun obtenerTodosLosLocales(): List<LocalDTO> {
        return localService.obtenerTodosLosLocales().map { local -> local.toDto() }
    }

    @GetMapping("/local/{id}/platos")
    fun obtenerPlatosDelLocal(@PathVariable id: Int): List<PlatoClienteDTO> {
        val platosDelLocal = localService.obtenerPlatosDisponibles(id)

        return platosDelLocal.map{ it.toClienteDTO()}
    }

    @GetMapping("/local/{id}")
    fun obtenerLocalClientePorId(@PathVariable id: Int): LocalClienteDTO {
        return localService.obtenerLocalPorId(id).toClienteDTO()
    }
}