package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Usuario
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import ar.edu.unsam.algo3.utils.HashUtils

@Service
class AuthUsuarioService(private val usuarioRepositorio : UsuarioRepositorio){
    fun existeUser(username: String): Boolean{
        return usuarioRepositorio.search(username).isNotEmpty()
    }
}
