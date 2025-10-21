package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.Local
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.Repositorios
import ar.edu.unsam.algo3.utils.HashUtils

@Service
class AuthService {

    fun existeUser(username: String): Boolean{
        return Repositorios.local.search(username).isNotEmpty()
    }

    fun crearUser(username: String, password: String): Local?{
        return try {
            val hashPassword = HashUtils.hash53(password)
            val nuevoLocal = Local(usuario = username, password = hashPassword)
            Repositorios.local.create(nuevoLocal)
        }
        catch (e: Exception){
            println(e)
            null
        }
    }

    fun validarLocal(usuario: String, password: String): Local? {
        return try {
            val hashedPassword = HashUtils.hash53(password)
            val localUser = Repositorios.local.search(usuario).firstOrNull() ?: return null
            if (localUser.password == hashedPassword) {
                localUser
            } else {
                null
            }
        } catch (e: Exception) {
            println(e)
            null
        }
    }
}