package busqueda;

import modelo.Usuario;

import java.util.List;

/**
 * Busqueda secuencial clasica: recorre la lista completa comparando uno por
 * uno hasta encontrar la clave (o llegar al final). Es el "antes" de la
 * historia de la semana: sirve para demostrar por que O(n) se vuelve
 * problematico cuando N crece a 1 000 000.
 */
public class BusquedaSecuencial {
    
    private BusquedaSecuencial(){
        
        
    }
    
    public static Usuario buscarPorDni(
        List <Usuario> usuarios, long dni){
        
        for (Usuario usuario : usuarios){
            if(usuario.getDni() == dni){
                return usuario;
            }
        }
        return null;
    }
    

    /** Igual que buscarPorDni, pero cuenta cuantas comparaciones hizo. */
    public static ResultadoBusqueda buscarConComparaciones(List<Usuario> usuarios, long dni) {
        int comparaciones = 0;
        for(Usuario usuario : usuarios){
            comparaciones++;
            if (usuario.getDni() == dni){
                return new ResultadoBusqueda(usuario, comparaciones);
            }
        }
        // TODO (Ejercicio 3): recorre "usuarios", incrementando "comparaciones"
        // en cada paso, hasta encontrar el dni o terminar la lista.
        return new ResultadoBusqueda(null, comparaciones);
    }
    
    public static class ResultadoBusqueda{
        private final Usuario usuario;
        private final int comparaciones;
        
        public ResultadoBusqueda(Usuario usuario, int comparaciones){
            this.usuario = usuario;
            this.comparaciones = comparaciones;
        }
        public Usuario getUsuario(){
            return usuario;
        }
        public int getComparaciones(){
            return comparaciones;
        }
    }
}
 
