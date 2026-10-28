package app;

import busqueda.FuncionHash;
import busqueda.TablaHash;
import datos.LectorCSV;
import modelo.Usuario;
import rendimiento.Benchmark;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Principal {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {

            mostrarMenu();

            System.out.print("Seleccione un ejercicio: ");
            opcion = teclado.nextInt();

            System.out.println();

            try {

                switch (opcion) {

                    case 1:
                        ejercicio1_funcionHash();
                        break;

                    case 2:
                        ejercicio2_colisiones();
                        break;

                    case 3:
                        ejercicio3_secuencialVsHash(
                                "data/usuarios_10000.csv"
                        );
                        break;

                    case 4:
                        ejercicio4_listaDelMillon();
                        break;

                    case 5:
                        ejercicio5_fraudHash();
                        break;

                    case 6:
                        ejecutarTodos();
                        break;

                    case 0:
                        System.out.println(
                                "Programa finalizado."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opcion no valida."
                        );
                }

            } catch (Exception e) {

                System.out.println();
                System.out.println(
                        "ERROR DURANTE LA EJECUCION:"
                );

                System.out.println(
                        e.getMessage()
                );

                e.printStackTrace();
            }

            if (opcion != 0) {

                System.out.println();
                System.out.println(
                        "Presione ENTER para volver al menu..."
                );

                teclado.nextLine();
                teclado.nextLine();
            }

        } while (opcion != 0);
    }

    // =========================================================
    // MENU
    // =========================================================
    private static void mostrarMenu() {

        System.out.println();
        System.out.println(
                "=================================================="
        );

        System.out.println(
                "        FRAUDHASH - SEMANA 6 - DOCENTE"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println(
                "1. Ejercicio 1 - Funcion Hash"
        );

        System.out.println(
                "2. Ejercicio 2 - Colisiones"
        );

        System.out.println(
                "3. Ejercicio 3 - Secuencial vs Hash (CSV 10K)"
        );

        System.out.println(
                "4. Ejercicio 4 - La lista del millon"
        );

        System.out.println(
                "5. Ejercicio 5 - FraudHash"
        );

        System.out.println(
                "6. Ejecutar TODOS"
        );

        System.out.println(
                "0. Salir"
        );

        System.out.println(
                "=================================================="
        );
    }

    // =========================================================
    // EJERCICIO 1
    // FUNCION HASH
    // =========================================================
    private static void ejercicio1_funcionHash() {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " EJERCICIO 1 - ¿DONDE CAE MI CLAVE?"
        );

        System.out.println(
                "=================================================="
        );

        int m = 7;

        long[] claves = {
            23, 44, 7, 18, 55,
            9, 30, 14, 61, 2
        };

        boolean[] ocupada = new boolean[m];

        int colisiones = 0;

        System.out.println();
        System.out.println(
                "Funcion: h(clave) = clave % " + m
        );

        System.out.println();

        System.out.printf(
                "%-10s %-18s %-12s %-15s%n",
                "CLAVE",
                "OPERACION",
                "POSICION",
                "ESTADO"
        );

        System.out.println(
                "-------------------------------------------------------"
        );

        for (long clave : claves) {

            int posicion
                    = FuncionHash.hash(
                            clave,
                            m
                    );

            boolean hayColision
                    = ocupada[posicion];

            if (hayColision) {
                colisiones++;
            }

            System.out.printf(
                    "%-10d %-18s %-12d %-15s%n",
                    clave,
                    clave + " % " + m,
                    posicion,
                    hayColision
                            ? "COLISION"
                            : "LIBRE"
            );

            ocupada[posicion] = true;
        }

        System.out.println();
        System.out.println(
                "Colisiones detectadas: "
                + colisiones
        );

        System.out.println();
        System.out.println(
                "CONCLUSION:"
        );

        System.out.println(
                "Dos claves diferentes pueden producir"
                + " la misma posicion."
        );

        System.out.println(
                "A esto lo denominamos COLISION."
        );

        System.out.println();

        System.out.println(
                "Pregunta para el Ejercicio 2:"
        );

        System.out.println(
                "¿Que hacemos cuando dos claves quieren"
                + " ocupar la misma posicion?"
        );
    }

    // =========================================================
    // EJERCICIO 2
    // COLISIONES
    // =========================================================
    private static void ejercicio2_colisiones() {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " EJERCICIO 2 - COLISIONES"
        );

        System.out.println(
                "=================================================="
        );

        long[] claves = {
            10, 17, 24, 3
        };

        // -------------------------------------------------
        // EXPLORACION LINEAL
        // -------------------------------------------------
        System.out.println();
        System.out.println(
                ">>> EXPLORACION LINEAL"
        );

        TablaHash lineal
                = new TablaHash(
                        7,
                        TablaHash.Modo.LINEAL
                );

        insertarClavesPrueba(
                lineal,
                claves
        );

        lineal.imprimir();

        System.out.println();

        System.out.println(
                "Colisiones LINEAL: "
                + lineal.getColisiones()
        );

        // -------------------------------------------------
        // EXPLORACION CUADRATICA
        // -------------------------------------------------
        System.out.println();
        System.out.println(
                ">>> EXPLORACION CUADRATICA"
        );

        TablaHash cuadratica
                = new TablaHash(
                        7,
                        TablaHash.Modo.CUADRATICA
                );

        insertarClavesPrueba(
                cuadratica,
                claves
        );

        cuadratica.imprimir();

        System.out.println();

        System.out.println(
                "Colisiones CUADRATICA: "
                + cuadratica.getColisiones()
        );

        System.out.println();
        System.out.println(
                "CONCLUSION:"
        );

        System.out.println(
                "Cuando existe una colision podemos buscar"
                + " otra posicion mediante exploracion."
        );
    }

    private static void insertarClavesPrueba(
            TablaHash tabla,
            long[] claves) {

        int id = 1;

        for (long clave : claves) {

            Usuario usuario
                    = new Usuario(
                            id,
                            "usuario" + id,
                            clave,
                            "usuario" + id
                            + "@correo.com",
                            "Lima"
                    );

            tabla.insertar(usuario);

            id++;
        }
    }

    // =========================================================
    // EJERCICIO 3
    // CSV 10 000 - SECUENCIAL VS HASH
    // =========================================================
    private static void ejercicio3_secuencialVsHash(
            String rutaCsv)
            throws IOException {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " EJERCICIO 3 - SECUENCIAL VS HASH"
        );

        System.out.println(
                "=================================================="
        );

        System.out.println();

        System.out.println(
                "Archivo utilizado:"
        );

        System.out.println(
                rutaCsv
        );

        // -------------------------------------------------
        // LEER CSV
        // -------------------------------------------------
        List<Usuario> usuarios
                = LectorCSV.leer(
                        rutaCsv
                );

        System.out.println();

        System.out.println(
                "Usuarios cargados: "
                + usuarios.size()
        );

        // -------------------------------------------------
        // CREAR TABLA HASH
        // -------------------------------------------------
        int capacidad
                = siguientePrimo(
                        usuarios.size() * 2 + 1
                );

        TablaHash tabla
                = new TablaHash(
                        capacidad,
                        TablaHash.Modo.LINEAL
                );

        for (Usuario usuario : usuarios) {

            tabla.insertar(usuario);
        }

        System.out.println(
                "Capacidad tabla Hash: "
                + tabla.getCapacidad()
        );

        System.out.println(
                "Colisiones al construirla: "
                + tabla.getColisiones()
        );

        // -------------------------------------------------
        // USUARIOS DE PRUEBA
        // -------------------------------------------------
        long dniInicio
                = usuarios.get(0)
                        .getDni();

        long dniMedio
                = usuarios.get(
                        usuarios.size() / 2
                ).getDni();

        long dniFinal
                = usuarios.get(
                        usuarios.size() - 1
                ).getDni();

        long dniInexistente
                = 999999999L;

        // -------------------------------------------------
        // CABECERA
        // -------------------------------------------------
        System.out.println();

        System.out.printf(
                "%-12s | %-14s | %13s | %15s | %s%n",
                "METODO",
                "USUARIO",
                "COMPARACIONES",
                "TIEMPO",
                "RESULTADO"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        // -------------------------------------------------
        // INICIO
        // -------------------------------------------------
        compararCaso(
                "INICIO",
                usuarios,
                tabla,
                dniInicio
        );

        // -------------------------------------------------
        // MEDIO
        // -------------------------------------------------
        compararCaso(
                "MEDIO",
                usuarios,
                tabla,
                dniMedio
        );

        // -------------------------------------------------
        // FINAL
        // -------------------------------------------------
        compararCaso(
                "FINAL",
                usuarios,
                tabla,
                dniFinal
        );

        // -------------------------------------------------
        // INEXISTENTE
        // -------------------------------------------------
        compararCaso(
                "NO EXISTE",
                usuarios,
                tabla,
                dniInexistente
        );

        System.out.println();
        System.out.println(
                "CONCLUSION:"
        );

        System.out.println(
                "La busqueda secuencial puede necesitar"
                + " recorrer hasta N elementos."
        );

        System.out.println(
                "Hash intenta acceder directamente a la"
                + " posicion correspondiente."
        );

        System.out.println();

        System.out.println(
                "Secuencial: O(n)"
        );

        System.out.println(
                "Hash: O(1) promedio"
        );
    }

    private static void compararCaso(
            String etiqueta,
            List<Usuario> usuarios,
            TablaHash tabla,
            long dni) {

        Benchmark.Resultado secuencial
                = Benchmark.medirSecuencial(
                        usuarios,
                        dni
                );

        Benchmark.Resultado hash
                = Benchmark.medirHash(
                        tabla,
                        dni
                );

        Benchmark.imprimirFila(
                "Secuencial",
                etiqueta,
                secuencial
        );

        Benchmark.imprimirFila(
                "Hash",
                etiqueta,
                hash
        );

        System.out.println();
    }

    // =========================================================
    // EJERCICIO 4
    // LISTA DEL MILLON
    // =========================================================
    private static void ejercicio4_listaDelMillon()
            throws IOException {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " EJERCICIO 4 - LA LISTA DEL MILLON"
        );

        System.out.println(
                "=================================================="
        );

        String[] rutas = {
            "data/usuarios_1000.csv",
            "data/usuarios_10000.csv",
            "data/usuarios_100000.csv",
            "data/usuarios_1M.csv"
        };

        System.out.println();

        System.out.printf(
                "%-12s | %-15s | %-15s | %-15s | %-15s%n",
                "N",
                "SEC. COMP.",
                "HASH COMP.",
                "SEC. ns",
                "HASH ns"
        );

        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        for (String ruta : rutas) {

            List<Usuario> usuarios
                    = LectorCSV.leer(
                            ruta
                    );

            int capacidad
                    = siguientePrimo(
                            usuarios.size() * 2 + 1
                    );

            TablaHash tabla
                    = new TablaHash(
                            capacidad,
                            TablaHash.Modo.LINEAL
                    );

            for (Usuario usuario : usuarios) {

                tabla.insertar(usuario);
            }

            // Buscar deliberadamente el último.
            // Esto permite observar claramente O(n)
            // en la búsqueda secuencial.
            long dni
                    = usuarios.get(
                            usuarios.size() - 1
                    ).getDni();

            Benchmark.Resultado sec
                    = Benchmark.medirSecuencial(
                            usuarios,
                            dni
                    );

            Benchmark.Resultado hash
                    = Benchmark.medirHash(
                            tabla,
                            dni
                    );

            System.out.printf(
                    "%-12d | %-15d | %-15d | %-15d | %-15d%n",
                    usuarios.size(),
                    sec.comparaciones,
                    hash.comparaciones,
                    sec.tiempoNs,
                    hash.tiempoNs
            );
        }

        System.out.println();
        System.out.println(
                "¿QUE PASA CUANDO N CRECE?"
        );

        System.out.println();

        System.out.println(
                "La busqueda secuencial incrementa"
                + " aproximadamente su trabajo con N."
        );

        System.out.println();

        System.out.println(
                "La busqueda Hash mantiene pocas"
                + " comparaciones en promedio."
        );

        System.out.println();

        System.out.println(
                "Secuencial -> O(n)"
        );

        System.out.println(
                "Hash       -> O(1) promedio"
        );
    }

    // =========================================================
    // EJERCICIO 5
    // FRAUDHASH
    // =========================================================
    private static void ejercicio5_fraudHash()
            throws IOException {

        System.out.println(
                "=================================================="
        );

        System.out.println(
                " EJERCICIO 5 - FRAUDHASH"
        );

        System.out.println(
                "=================================================="
        );

        String ruta
                = "data/usuarios_10000.csv";

        List<Usuario> usuarios
                = LectorCSV.leer(
                        ruta
                );

        System.out.println();

        System.out.println(
                "Usuarios analizados: "
                + usuarios.size()
        );

        // Utilizamos tres estructuras Hash.
        // Cada una controla una clave diferente.
        Set<String> usernames
                = new HashSet<>();

        Set<Long> dnis
                = new HashSet<>();

        Set<String> emails
                = new HashSet<>();

        int sospechosos = 0;

        System.out.println();
        System.out.println(
                "Buscando cuentas sospechosas..."
        );

        for (Usuario usuario : usuarios) {

            String username
                    = usuario.getUsername()
                            .trim()
                            .toLowerCase();

            String email
                    = usuario.getEmail()
                            .trim()
                            .toLowerCase();

            long dni
                    = usuario.getDni();

            boolean usernameDuplicado
                    = !usernames.add(
                            username
                    );

            boolean dniDuplicado
                    = !dnis.add(
                            dni
                    );

            boolean emailDuplicado
                    = !emails.add(
                            email
                    );

            if (usernameDuplicado
                    || dniDuplicado
                    || emailDuplicado) {

                sospechosos++;

                System.out.println();

                System.out.println(
                        "CUENTA SOSPECHOSA:"
                );

                System.out.println(
                        usuario
                );

                if (usernameDuplicado) {

                    System.out.println(
                            " -> Username duplicado"
                    );
                }

                if (dniDuplicado) {

                    System.out.println(
                            " -> DNI duplicado"
                    );
                }

                if (emailDuplicado) {

                    System.out.println(
                            " -> Email duplicado"
                    );
                }
            }
        }

        System.out.println();
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Total de cuentas sospechosas: "
                + sospechosos
        );

        System.out.println(
                "=============================================="
        );

        System.out.println();

        System.out.println(
                "COMPLEJIDAD:"
        );

        System.out.println(
                "Comprobar cada clave mediante Hash:"
        );

        System.out.println(
                "O(1) promedio."
        );

        System.out.println();

        System.out.println(
                "Procesar los N usuarios completos:"
        );

        System.out.println(
                "O(n) promedio."
        );
    }

    // =========================================================
    // EJECUTAR TODOS
    // =========================================================
    private static void ejecutarTodos()
            throws IOException {

        ejercicio1_funcionHash();

        separador();

        ejercicio2_colisiones();

        separador();

        ejercicio3_secuencialVsHash(
                "data/usuarios_10000.csv"
        );

        separador();

        ejercicio4_listaDelMillon();

        separador();

        ejercicio5_fraudHash();
    }

    private static void separador() {

        System.out.println();
        System.out.println();
        System.out.println(
                "##################################################"
        );
        System.out.println();
    }

    // =========================================================
    // UTILIDADES
    // =========================================================
    private static int siguientePrimo(int numero) {

        if (numero <= 2) {
            return 2;
        }

        if (numero % 2 == 0) {
            numero++;
        }

        while (!esPrimo(numero)) {

            numero += 2;
        }

        return numero;
    }

    private static boolean esPrimo(int numero) {

        if (numero < 2) {
            return false;
        }

        if (numero == 2) {
            return true;
        }

        if (numero % 2 == 0) {
            return false;
        }

        for (int i = 3;
                (long) i * i <= numero;
                i += 2) {

            if (numero % i == 0) {

                return false;
            }
        }

        return true;
    }
}
