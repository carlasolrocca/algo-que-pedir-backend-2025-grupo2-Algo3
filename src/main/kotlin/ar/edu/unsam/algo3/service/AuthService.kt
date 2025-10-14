package ar.edu.unsam.algo3.service

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter

@Service
class AuthService {

    private var mapUsers: MutableMap<String, String> = mutableMapOf()

    init{
        cargarUsers()
    }

    private fun cargarUsers() : Map<String, String>{

        try {
            val database = ClassPathResource("users.txt")
            database.inputStream.bufferedReader().use {reader ->
                reader.forEachLine { line ->
                    val params = line.trim().split(",")
                    if (params.size == 2) {
                        mapUsers[params[0]] = params[1]
                    }
                }
            }
        }
        catch (e: Exception){
            println(e)
        }
        return mapUsers
    }

    fun existeUser(username: String): Boolean{
        return mapUsers.containsKey(username)
    }

    fun crearUser(username: String, password: String): Boolean{
        return try {
            val hashPassword = hash53(password)
            mapUsers[username] = hashPassword

            val file = File("src/main/resources/users.txt")
            FileWriter(file, true).use { writer ->
                writer.write("$username,$hashPassword\n")
            }
            true
        }
        catch (e: Exception){
            println(e)
            false
        }
    }

    fun validarUser(usuario: String, password: String): Boolean {
        val hashedPassword = hash53(password)
        return mapUsers[usuario] == hashedPassword
    }

    //Funcion de hashing que devuelve un entero de 53 bits a partir de un string
    //Fuente: https://stackoverflow.com/questions/7616461/generate-a-hash-from-string-in-javascript/52171480#52171480
    private fun hash53(str: String, seed: Int = 0): String {
        var h1 = (0xdeadbeef.toInt() xor seed)
        var h2 = (0x41c6ce57.toInt() xor seed)

        for (i in str.indices) {
            val ch = str[i].code
            h1 = (h1 xor ch) * 2654435761.toInt()
            h2 = (h2 xor ch) * 1597334677
        }

        h1 = (h1 xor (h1 ushr 16)) * 2246822507.toInt()
        h1 = h1 xor ((h2 xor (h2 ushr 13)) * 3266489909.toInt())
        h2 = (h2 xor (h2 ushr 16)) * 2246822507.toInt()
        h2 = h2 xor ((h1 xor (h1 ushr 13)) * 3266489909.toInt())

        return (4294967296L * (2097151L and h2.toLong()) + (h1.toLong() and 0xFFFFFFFFL)).toString(16)
    }

}