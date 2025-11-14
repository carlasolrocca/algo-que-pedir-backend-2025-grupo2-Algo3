package ar.edu.unsam.algo3.controller;

import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto.UsuarioDTO
import ar.edu.unsam.algo3.ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.service.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
class UsuarioController(val usuarioService: UsuarioService) {
    @GetMapping("/usuario/{id}")
    fun usuarioPorId(@PathVariable id: Int) = usuarioService.getById(id)?.toDTO()

    @PutMapping("/usuario/{id}")
    fun actualizarUsuario(@PathVariable id: Int, @RequestBody usuarioBody: Usuario): UsuarioDTO =
        usuarioService.update(id, usuarioBody).toDTO()
}