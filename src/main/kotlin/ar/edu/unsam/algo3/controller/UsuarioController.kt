package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dto.ClienteInfoDTO
import ar.edu.unsam.algo3.dto.LocalAPuntuarDTO
import ar.edu.unsam.algo3.dto.PuntuacionRequest
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.service.UsuarioService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(
    origins = ["http://localhost:5173"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS]
)
class UsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping("/usuario/{id}")
    fun getById(@PathVariable id: Int): ClienteInfoDTO = usuarioService.getById(id).toDTO()

    @GetMapping("/usuario/{id}/locales-a-puntuar")
    fun obtenerLocalesAPuntuar(@PathVariable id: Int): List<LocalAPuntuarDTO> {
        return usuarioService.obtenerLocalesAPuntuar(id)
    }

    @PostMapping("/usuario/{id}/puntuar-local/{idLocal}")
    fun puntuarLocal(
        @PathVariable id: Int,
        @PathVariable idLocal: Int,
        @RequestBody request: PuntuacionRequest
    ) {
        usuarioService.puntuarLocal(id, idLocal, request.puntuacion)
    }
}