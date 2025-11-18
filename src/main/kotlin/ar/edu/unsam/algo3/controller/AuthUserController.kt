package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dto.AuthResponse
import ar.edu.unsam.algo3.dto.AuthResponseUsuario
import ar.edu.unsam.algo3.dto.LoginRequest
import ar.edu.unsam.algo3.dto.RegisterRequest
import ar.edu.unsam.algo3.service.AuthUsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class AuthUserController(private val authUserService : AuthUsuarioService) {
    @PostMapping("/api/registro")
    fun registro(@RequestBody dataRegistro : RegisterRequest) : ResponseEntity<AuthResponseUsuario> {

        authUserService.registrarUsuario(dataRegistro)

        val response = AuthResponseUsuario(
            success = true,
            message= "Usuario registrado con exito!",
            usuario = dataRegistro.usuario
        )

        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/api/login")
    fun login(@RequestBody dataLogin : LoginRequest) : ResponseEntity<AuthResponseUsuario> {
        val usuarioLogueado = authUserService.loginUsuario(dataLogin)

        val response = AuthResponseUsuario(
            success = true,
            message = "Login exitoso!",
            usuario = usuarioLogueado.usuario
        )

        return ResponseEntity(response, HttpStatus.OK)
    }
}