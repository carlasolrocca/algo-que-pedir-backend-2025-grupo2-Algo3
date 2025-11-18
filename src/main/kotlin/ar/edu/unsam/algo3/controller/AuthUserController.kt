package ar.edu.unsam.algo3.ar.edu.unsam.algo3.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ar.edu.unsam.algo3.service.AuthUserService



@RestController
@CrossOrigin("*")
class AuthUserController(private val authUserService : AuthUserService) {}