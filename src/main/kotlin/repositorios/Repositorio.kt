package ar.edu.unsam.algo3.repositorios

import ar.edu.unsam.algo3.*
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

abstract class TipoRepositorio {
    @JsonProperty("id") var id: Int? = null
}

open class Repositorio<T : TipoRepositorio>(
    private val searcher: SearchStrategy<T>
) {
    var idActual: Int = 0
    val memoria: MutableSet<T> = mutableSetOf<T>()

    fun create(objeto: T): T {
        // Lanza una excepcion del tipo IllegalArgumentException si no cumple la condicion
        require(objeto.id == null) { "El objeto ya esta creado" }
        val id = ++idActual
        objeto.id = id
        memoria.add(objeto)
        return objeto
    }

    fun verificarID(objeto: T): T {
        // Lanza una excepcion si el objeto no tiene ID
        requireNotNull(objeto.id) { "El objeto debe tener un ID" }

        // Se verifica que exista en memoria y se elimina
        return getById(objeto.id!!)
    }

    fun delete(objeto: T) {
        val objetoAEliminar = verificarID(objeto)
        memoria.remove(objetoAEliminar)
    }

    fun update(objeto: T): T {
        val objetoAActualizar = verificarID(objeto)
        delete(objetoAActualizar)
        memoria.add(objeto)
        return objeto
    }

    fun getById(id: Int): T {
        return memoria.find { it.id == id }
            ?: throw ErrorException.NotFoudException("No se encontró un objeto con id $id")
    }

    fun search(value: String): List<T> {
        return memoria.filter { searcher.matches(it, value) }
    }

    fun findAll(): List<T> {
        return memoria.toList()
    }

    //Actualiza repositorio a través de una lista
    fun agregarDesdeLista(objetos: List<T>) {
        objetos.forEach { objeto ->
            when {
                objeto.id == null -> create(objeto) //Si no tiene ID crea el objeto
                memoria.any { it.id == objeto.id } -> update(objeto) //Si ya tiene ID en memoria actualiza
                else -> memoria.add(objeto) //Si no está en el repo y tiene ID lo agrega
            }
        }
    }
}

// Hago esta clase para q Spring reconozca que esto es el repo
// y mockeo  Local y unos ingredientes para probar el plato
@Component
class PlatoRepositorio: Repositorio<Plato>(PlatoSearcher)

@Component
class IngredienteRepositorio: Repositorio<Ingrediente>(IngredienteSearcher) {
    init {
        create(Ingrediente(nombre = "Pollo"))
        create(Ingrediente(nombre = "Tomate"))
        create(Ingrediente(nombre = "Queso"))
    }
}

@Component
class LocalRepositorio: Repositorio<Local>(LocalSearcher)

@Component
open class PedidoRepositorio : Repositorio<Pedido>(PedidoSearcher)



// Singletons de algo2:
object Repositorios {
    val usuario = Repositorio<Usuario>(
        searcher = UsuarioSearcher,
    )
    val plato = Repositorio<Plato>(
        searcher = PlatoSearcher,
    )
    val local = Repositorio<Local>(
        searcher = LocalSearcher,
    )
    val delivery = Repositorio<Delivery>(
        searcher = DeliverySearcher
    )
    val ingrediente = Repositorio<Ingrediente>(
        searcher = IngredienteSearcher,
    )
    val cupon = Repositorio<Cupon>(
        searcher = CuponSearcher,
    )
    
}
