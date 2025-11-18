package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.dto.AuthResponse
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.dto.RegisterRequest
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import ar.edu.unsam.algo3.utils.HashUtils
import org.springframework.http.ResponseEntity
import ar.edu.unsam.algo3.Direccion
import org.uqbar.geodds.Point


@Service
class AuthUsuarioService(private val usuarioRepositorio : UsuarioRepositorio){
    fun existeUser(usuario: String): Boolean{
        return usuarioRepositorio.search(usuario).isNotEmpty()
    }

    fun registrarUsuario(dataUsuario: RegisterRequest): Usuario {
        if(dataUsuario.password != dataUsuario.confirmarPassword){
            throw ErrorException.BusinessException("Las contraseñas no coinciden")
        }

        if(existeUser(dataUsuario.usuario)){
            throw ErrorException.BusinessException("El usuario ${dataUsuario.usuario} ya existe`")
        }

        val passwordHasheada = HashUtils.hash53(dataUsuario.password)

        val nuevoUsuarioRegistrado = Usuario(
            nombre= "Prueba",                   //El usuario solo registra su username+pass, como tomas los datos de Nombre y etc?
            apellido= "Usuario",
            usuario= dataUsuario.usuario,
            password = passwordHasheada,
            direccion = Direccion("Casa usuario prueba", 456, Point(21.4, 17.3))
        )

        usuarioRepositorio.create(nuevoUsuarioRegistrado)
        return nuevoUsuarioRegistrado
    }
}
