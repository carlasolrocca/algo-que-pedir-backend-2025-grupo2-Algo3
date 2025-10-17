/*package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.EnumEstadosPedido
import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.Usuario
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorios.PedidoRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import org.springframework.beans.factory.InitializingBean
import java.time.*

@Service
class PedidosBootstrap(
    val pedidoRepositorio : PedidoRepositorio,
    val platoRepositorio : PlatoRepositorio,
    val usuarioRepositorio : UsuarioRepositorio
) : InitializingBean {


    private lateinit var plato1 : Plato
    private lateinit var plato2 : Plato
    private lateinit var plato3 : Plato



    val tomate = Ingrediente("Tomate", 2.0, EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS)
    val queso = Ingrediente("Queso", 3.5, EnumGrupoAlimenticio.LACTEOS)
    val pollo  = Ingrediente("Pollo", 4.0, EnumGrupoAlimenticio.PROTEINAS, true)
    val pan = Ingrediente("Pan", 2.0, EnumGrupoAlimenticio.CEREALES_Y_TUBERCULOS)
    val lechuga = Ingrediente("Lechuga", 1.0, EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS)





    fun crearPlatos(){
        // platoRepositorio.clearInit() --PENDIENTE IMPLEMENTACION--

        plato1 = Plato(local= local1, nombre = "Sanguche de tomate y queso", valorBase = 10.0).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(queso)
            agregarIngrediente(pan)
        }

        plato2 = Plato(local= local1, nombre = "Pollo con ensalada", valorBase = 12.0).apply {
            agregarIngrediente(pollo)
            agregarIngrediente(lechuga)
            agregarIngrediente(tomate)
        }

        plato3 = Plato(local= local2, nombre = "Ensalada simple", valorBase = 8.0).apply {
            agregarIngrediente(lechuga)
            agregarIngrediente(tomate)
        }

        platoRepositorio.apply {
            create(plato1)
            create(plato2)
            create(plato3)
        }
    }



    override fun afterPropertiesSet() {
        crearUsuarios()
        crearPlatos()
        crearPedidos()
    }
}*/