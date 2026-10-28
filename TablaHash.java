package busqueda;

import modelo.Usuario;

public class TablaHash {

    public enum Modo {
        LINEAL,
        CUADRATICA
    }

    private Usuario[] tabla;
    private Modo modo;

    private int colisiones;
    private int cantidad;

    public TablaHash(int capacidad, Modo modo) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "Capacidad inválida"
            );
        }

        this.tabla = new Usuario[capacidad];
        this.modo = modo;
    }

    public int getCapacidad() {
        return tabla.length;
    }

    public int getColisiones() {
        return colisiones;
    }

    public int getCantidad() {
        return cantidad;
    }

    private int calcularPosicion(
            int posicionBase,
            int intento) {

        if (modo == Modo.LINEAL) {

            return Math.floorMod(
                    posicionBase + intento,
                    tabla.length
            );

        } else {

            return Math.floorMod(
                    posicionBase + intento * intento,
                    tabla.length
            );
        }
    }

    public boolean insertar(Usuario usuario) {

        int base
                = FuncionHash.hash(
                        usuario.getDni(),
                        tabla.length
                );

        for (int i = 0; i < tabla.length; i++) {

            int posicion
                    = calcularPosicion(base, i);

            if (tabla[posicion] == null) {

                tabla[posicion] = usuario;
                cantidad++;

                return true;
            }

            colisiones++;
        }

        return false;
    }

    public Usuario buscarPorDni(long dni) {

        int base
                = FuncionHash.hash(
                        dni,
                        tabla.length
                );

        for (int i = 0; i < tabla.length; i++) {

            int posicion
                    = calcularPosicion(base, i);

            Usuario usuario
                    = tabla[posicion];

            if (usuario == null) {
                return null;
            }

            if (usuario.getDni() == dni) {
                return usuario;
            }
        }

        return null;
    }

    public int buscarPorDniConComparaciones(long dni) {

        int base
                = FuncionHash.hash(
                        dni,
                        tabla.length
                );

        int comparaciones = 0;

        for (int i = 0; i < tabla.length; i++) {

            int posicion
                    = calcularPosicion(base, i);

            Usuario usuario
                    = tabla[posicion];

            comparaciones++;

            if (usuario == null) {
                return comparaciones;
            }

            if (usuario.getDni() == dni) {
                return comparaciones;
            }
        }

        return comparaciones;
    }

    public void imprimir() {

        for (int i = 0; i < tabla.length; i++) {

            System.out.print(i + " -> ");

            if (tabla[i] == null) {
                System.out.println("VACÍO");
            } else {
                System.out.println(
                        tabla[i].getDni()
                        + " | "
                        + tabla[i].getUsername()
                );
            }
        }
    }
}
