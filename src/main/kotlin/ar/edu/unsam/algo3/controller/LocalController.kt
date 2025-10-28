package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.service.LocalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(
    origins = ["http://localhost:5173"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.GET, RequestMethod.PUT] //, RequestMethod.P, RequestMethod.DELETE, RequestMethod.OPTIONS]
)
class LocalController(private val localService: LocalService) {

    @GetMapping("/local/{id}")
    fun obtenerLocalPorId(@PathVariable id: Int): LocalDTO {
        return localService.obtenerLocalPorId(id)
    }

    @PutMapping("/local")
    fun actualizarLocal(@RequestBody localDTO: LocalDTO): ResponseEntity<Any> {
        return try {
            val actualizado = localService.actualizarLocalDesdeDTO(localDTO)
            ResponseEntity.ok(actualizado)
        } catch (e: IllegalArgumentException) {
            // Devolver 422 cuando recibe datos inválidos
            ResponseEntity.unprocessableEntity().body(e.message)
        } catch (e: Exception) {
            // Devolver 500 cuando recibe otros errores no controlados
            ResponseEntity.status(500).body("Error interno del servidor. Consulte con su administrador de confianza, o rece.")
        }
}
}