package ar.edu.unsam.algo3.ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.Direccion
import ar.edu.unsam.algo3.EnumEstadosPedido
import ar.edu.unsam.algo3.EnumGrupoAlimenticio
import ar.edu.unsam.algo3.Ingrediente
import ar.edu.unsam.algo3.Local
import ar.edu.unsam.algo3.MedioDePago
import ar.edu.unsam.algo3.Pedido
import ar.edu.unsam.algo3.Plato
import ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.repositorios.IngredienteRepositorio
import ar.edu.unsam.algo3.repositorios.LocalRepositorio
import ar.edu.unsam.algo3.repositorios.PedidoRepositorio
import ar.edu.unsam.algo3.repositorios.PlatoRepositorio
import ar.edu.unsam.algo3.repositorios.UsuarioRepositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.uqbar.geodds.Point
import java.time.LocalTime

@Service
class AppBootstrap(
    val ingredienteRepositorio: IngredienteRepositorio,
    val platoRepositorio: PlatoRepositorio,
    val pedidoRepositorio: PedidoRepositorio,
    val usuarioRepositorio: UsuarioRepositorio,
    val localRepositorio: LocalRepositorio
    ): InitializingBean {

    private lateinit var tomate: Ingrediente
    private lateinit var pechugaDePollo: Ingrediente
    private lateinit var arroz: Ingrediente
    private lateinit var leche: Ingrediente
    private lateinit var palta: Ingrediente
    private lateinit var queso: Ingrediente
    private lateinit var medallonDeCarne: Ingrediente
    private lateinit var lechuga: Ingrediente
    private lateinit var chocolate: Ingrediente
    private lateinit var glaseado: Ingrediente
    private lateinit var bizcocho: Ingrediente
    private lateinit var manteca: Ingrediente

    fun crearIngredientes() {
        ingredienteRepositorio.clearInit()

        tomate = Ingrediente(
            "Tomate",
            0.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        pechugaDePollo = Ingrediente(
            "Pechuga de pollo",
            3.00,
            EnumGrupoAlimenticio.PROTEINAS,
            true
        )
        arroz = Ingrediente(
            "Arroz",
            1.00,
            EnumGrupoAlimenticio.CEREALES_Y_TUBERCULOS,
            false
        )
        leche = Ingrediente(
            "Leche",
            2.00,
            EnumGrupoAlimenticio.LACTEOS,
            true
        )
        palta = Ingrediente(
            "Palta",
            1.50,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        queso = Ingrediente(
            "Queso",
            2.00,
            EnumGrupoAlimenticio.LACTEOS,
            true
        )
        medallonDeCarne = Ingrediente(
            "Medallón de carne",
            3.50,
            EnumGrupoAlimenticio.PROTEINAS,
            true
        )
        lechuga = Ingrediente(
            "Lechuga",
            1.00,
            EnumGrupoAlimenticio.FRUTAS_Y_VERDURAS,
            false
        )
        chocolate = Ingrediente(
            "Chocolate",
            3.00,
            EnumGrupoAlimenticio.AZUCARES_Y_DULCES,
            false
        )
        glaseado = Ingrediente(
            "Glaseado",
            1.25,
            EnumGrupoAlimenticio.AZUCARES_Y_DULCES,
            false
        )
        bizcocho = Ingrediente(
            "Bizcocho",
            4.00,
            EnumGrupoAlimenticio.GRASAS_Y_ACEITES,
            true
        )
        manteca = Ingrediente(
            "Manteca",
            1.00,
            EnumGrupoAlimenticio.GRASAS_Y_ACEITES,
            true
        )
        ingredienteRepositorio.apply {
            create(tomate)
            create(pechugaDePollo)
            create(arroz)
            create(leche)
            create(palta)
            create(queso)
            create(medallonDeCarne)
            create(lechuga)
            create(chocolate)
            create(glaseado)
            create(bizcocho)
            create(manteca)
        }
    }

    private lateinit var local1: Local
    private lateinit var local2: Local
    private lateinit var localMoe: Local

    fun crearLocales() {
        localRepositorio.clearInit()

        localMoe = Local(
            "Taberna de Moe",
            Direccion("Av. Siempre Viva", 742, Point(9.808327, -89.643204)),
            "https://www.clarin.com/img/2017/10/05/SkWTevV3-_1200x0.jpg",
            10.0,
            5.0
        ).apply {
            agregarMedioDePago(MedioDePago.EFECTIVO)
            agregarMedioDePago(MedioDePago.TRANSFERENCIA_BANCARIA)
        }
        local1 = Local(
            "Local Plato 1 y Plato 2",
            Direccion("Av. Libertador",2300)
        )
        local2 = Local(
            "Local Plato 3",
            Direccion("Calle Verdadera", 456)
        )

        localRepositorio.apply{
            create(localMoe)
            create(local1)
            create(local2)
        }
    }

    private lateinit var ensalada: Plato
    private lateinit var arrozConLeche: Plato
    private lateinit var veggieSalad: Plato
    private lateinit var pizzaVegetariana: Plato
    private lateinit var hamburguesaConQueso: Plato
    private lateinit var pastelDeChocolate: Plato

    fun crearPlatos() {
        platoRepositorio.clearInit()

         ensalada = Plato(
            nombre = "Ensalada Primavera",
            descripcion = "Nutritiva ensalada fresca",
            valorBase = 10.0,
            esdeAutor = true,
             local = local1
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(pechugaDePollo)
            agregarIngrediente(arroz)
        }

         arrozConLeche = Plato(
            nombre = "Arroz con Leche",
            descripcion = "Riquisimo postre de nuestras abuelas",
            valorBase = 7.5,
            esdeAutor = false,
             local = local1
        ).apply {
            agregarIngrediente(leche)
            agregarIngrediente(arroz)
        }

         veggieSalad = Plato(
            nombre = "Veggie Salad",
            descripcion = "Una ensalada sin maltrato animal",
            valorBase = 18.0,
            esdeAutor = true
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(palta)
            agregarIngrediente(arroz)
        }

        pizzaVegetariana = Plato(
            nombre = "Pizza Vegetariana",
            descripcion = "Pizza vegetariana con ingredientes variados",
            valorBase = 14.25,
            esdeAutor = false
        ).apply {
            agregarIngrediente(tomate)
            agregarIngrediente(queso)
        }

        hamburguesaConQueso = Plato(
            nombre = "Hamburguesa con Queso",
            descripcion = "Hamburguesa clásica con queso y papas fritas",
            valorBase = 10.50,
            esdeAutor = false,
            local = local2
        ).apply {
            agregarIngrediente(medallonDeCarne)
            agregarIngrediente(queso)
            agregarIngrediente(tomate)
            agregarIngrediente(lechuga)
        }

        pastelDeChocolate = Plato(
            nombre = "Pastel de Chocolate",
            descripcion = "Pastel de chocolate rico con glaseado",
            valorBase = 6.50,
            esdeAutor = true
        ).apply {
            agregarIngrediente(bizcocho)
            agregarIngrediente(chocolate)
            agregarIngrediente(glaseado)
        }

        platoRepositorio.apply {
            create(ensalada)
            create(arrozConLeche)
            create(veggieSalad)
            create(pizzaVegetariana)
            create(hamburguesaConQueso)
            create(pastelDeChocolate)
        }
    }

    private lateinit var sofia: Usuario
    private lateinit var micaela: Usuario
    private lateinit var jose: Usuario
    private lateinit var miguel: Usuario

    val direccion1 = Direccion("Av. Siempre Viva", 555)
    val direccion2 = Direccion("Calle Falsa", 123)
    val direccion3 = Direccion("Cucha Cucha", 45)
    val direccion4 = Direccion("Av. Rigoleau", 333)

    fun crearUsuarios() {
        usuarioRepositorio.clearInit()

        sofia = Usuario(
            nombre = "Sofía",
            apellido = "Miller",
            username = "smiller2005",
            direccion = direccion1
        )

        micaela = Usuario(
            nombre = "Micaela",
            apellido = "Moreno",
            username = "mmoreno2005",
            direccion = direccion2
        )

        jose = Usuario(
            nombre = "Jose",
            apellido = "Gomez",
            username = "jgomez1998",
            direccion = direccion3
        )

        miguel = Usuario(
            nombre = "Miguel",
            apellido = "Manso",
            username = "mmanso2002",
            direccion = direccion4
        )

        usuarioRepositorio.apply{
            create(sofia)
            create(micaela)
            create(jose)
            create(miguel)
        }
    }

    private lateinit var pedido1: Pedido
    private lateinit var pedido2: Pedido

    fun crearPedidos() {
         pedidoRepositorio.clearInit()

        pedido1 = Pedido(
            cliente = sofia,
            local = local1,
            estadoDelPedido = EnumEstadosPedido.PENDIENTE,
            medioDePago = MedioDePago.QR,
            horarioPedido = LocalTime.of(12,30)
        ).apply {
            agregarPlatoAlPedido(ensalada)
            agregarPlatoAlPedido(arrozConLeche)
        }
        pedido2 = Pedido(
            cliente = micaela,
            local = local2,
            estadoDelPedido = EnumEstadosPedido.ENTREGADO,
            medioDePago = MedioDePago.EFECTIVO,
            horarioPedido = LocalTime.of(15,45)
        ).apply {
            agregarPlatoAlPedido(hamburguesaConQueso)
        }

        pedidoRepositorio.apply{
            create(pedido1)
            create(pedido2)
        }
    }

    override fun afterPropertiesSet() {
        this.crearIngredientes()
        this.crearLocales()
        this.crearPlatos()
        this.crearUsuarios()
        this.crearPedidos()
    }
}