package ar.edu.unsam.algo2.servicios
import ar.edu.unsam.algo2.RepositorioException
import ar.edu.unsam.algo2.repositorios.Repositorio
import ar.edu.unsam.algo2.repositorios.TipoRepositorio
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

interface IServiceIngredientes{
    fun getIngredientes() : String
}

interface IServiceLocales{
    fun getLocales() : String
}

interface IExternalService {
    fun get(): String
}

class ServicioRepositorios<T : TipoRepositorio>(
    val service: IExternalService,
    val repositorio: Repositorio<T>,
    val clazz: Class<T>
) {

    fun esExistente(elemento:T): Boolean{
        val id = elemento.id ?: return false
        repositorio.getById(id)
        return true
    }

    fun actualizarRepositorio(){
        val elementosExternos = service.get();

        val elementosParseados: List<T> = jacksonObjectMapper()
            .readValue(elementosExternos,
                jacksonObjectMapper().typeFactory.constructCollectionType(List::class.java, clazz))

        try {
            elementosParseados.forEach {
                if(esExistente(it)) {
                    repositorio.update(it)
                } else {
                    repositorio.create(it)
                }
            }
        } catch (e: RepositorioException.NotFoudException) {
            throw e
        }
    }
}