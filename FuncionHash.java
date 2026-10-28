package busqueda;

/**
 * Funciones de dispersion (hash) usadas en toda la semana 6.
 *
 * CLAVE -> FUNCION HASH -> POSICION (0 .. m-1)
 *
 * Completa el metodo hash() usando el METODO DE LA DIVISION:
 *      h(clave) = clave % m
 */
public class FuncionHash {
    
    
    private FuncionHash (){
        
    }
    
    public static int hash(long clave, int m){
        if (m <=0){
            
            throw new IllegalArgumentException(
            "El tamaño de la tabla debe ser mayor que 0"
            );
            
        }
            return (int ) Math.floorMod(clave, m);

    }
    
    public static int hashTexto(String texto, int m){
        
        if(texto == null){
            throw new IllegalArgumentException("La clave no puede ser null");
    }

    return (int ) Math.floorMod(texto.hashCode(), m);

        
    }
}
