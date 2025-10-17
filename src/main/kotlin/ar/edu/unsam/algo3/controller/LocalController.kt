package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.service.LocalService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class LocalController(private val localService: LocalService) {

    @GetMapping("/local")
    fun obtenerLocal(): LocalDTO {
        return localService.obtenerLocalDTO()
    }

    @PutMapping("/local")
    fun actualizarLocal(@RequestBody localDTO: LocalDTO): LocalDTO {
        return localService.actualizarLocalDesdeDTO(localDTO)
    }
}