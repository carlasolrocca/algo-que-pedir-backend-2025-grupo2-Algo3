package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.service.AuthUsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
class AuthUserController(private val authUserService : AuthUsuarioService) {}