package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.ErrorException
import ar.edu.unsam.algo3.Usuario
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import ar.edu.unsam.algo3.utils.HashUtils
import org.springframework.http.ResponseEntity
import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.dto.InfoUsuarioResponse
import ar.edu.unsam.algo3.dto.LoginRequestUsuario
import ar.edu.unsam.algo3.dto.RegisterRequestUsuario
import ar.edu.unsam.algo3.dto.toInfoUsuarioDTO
import org.uqbar.geodds.Point


@Service
class AuthUsuarioService(private val usuarioRepositorio : UsuarioRepositorio){
    fun existeUser(usuario: String): Boolean{
        return usuarioRepositorio.search(usuario).isNotEmpty()
    }

    fun registrarUsuario(dataUsuario: RegisterRequestUsuario): Usuario {
        if(dataUsuario.password != dataUsuario.confirmarPassword){
            throw ErrorException.BusinessException("Las contraseñas no coinciden")
        }

        if(existeUser(dataUsuario.usuario)){
            throw ErrorException.BusinessException("El usuario ${dataUsuario.usuario} ya existe")
        }

        val passwordHasheada = HashUtils.hash53(dataUsuario.password)

        //El unico valor que no es real es el del Point pero calle y altura lo recibe del Formulario
        val nuevaDireccionUsuario = Direccion(
            calle = dataUsuario.calle,
            altura = dataUsuario.altura.toInt(),
            ubicacion = Point(31.4, 20.5)
        )

        val nuevoUsuarioRegistrado = Usuario(
            nombre= dataUsuario.nombre,
            apellido= dataUsuario.apellido,
            usuario= dataUsuario.usuario,
            password = passwordHasheada,
            direccion = nuevaDireccionUsuario
        )

        usuarioRepositorio.create(nuevoUsuarioRegistrado)
        return nuevoUsuarioRegistrado
    }

    fun loginUsuario(dataLogin : LoginRequestUsuario): Usuario {
        val usuarioEncontrado = usuarioRepositorio.search(dataLogin.usuario).firstOrNull()
            ?: throw ErrorException.BusinessException("Usuario o contraseña incorrectos")

        val passwordHasheada = HashUtils.hash53(dataLogin.password)

        if(usuarioEncontrado.password != passwordHasheada){
            throw ErrorException.BusinessException("Usuario o contraseña incorrectas")
        }

        return usuarioEncontrado
    }

    //Retorna toda la lista de usuarios registrados en la app
    fun obtenerTodosLosUsuarios(): List<InfoUsuarioResponse> {
        return usuarioRepositorio.findAll().map { usuario -> usuario.toInfoUsuarioDTO() }
    }
}
