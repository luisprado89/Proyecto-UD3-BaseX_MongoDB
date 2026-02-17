package com.proyecto401;

import com.mongodb.client.MongoDatabase;
import com.proyecto401.connection.ConexionBaseX;
import com.proyecto401.connection.ConexionMongoDB;
import com.proyecto401.model.SesionCliente;
import com.proyecto401.service.MetodosMongoDB;
import com.proyecto401.service.MetodosXML;
import org.basex.examples.api.BaseXClient;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.proyecto401.util.ConsoleIO.pedirInt;

public class ApplicationMenu {

    private static final Scanner SC = new Scanner(System.in);
    private static final MetodosXML metodosXML = new MetodosXML();
    private static final MetodosMongoDB metodosMongoDB = new MetodosMongoDB();

    private static BaseXClient conexionBaseX;
    private static MongoDatabase conexionMongoDB;

    private static final SesionCliente sesion = new SesionCliente();

    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);// para evitar los logs rojos

        int opcion;

        try {
            conexionBaseX = ConexionBaseX.conexionBaseX();       // open productos
            conexionMongoDB = ConexionMongoDB.conexionMongoDB(); // use tienda

            if (conexionBaseX == null || conexionMongoDB == null) {
                System.out.println("❌ No se pudo establecer conexión con las bases de datos.");
                System.out.println("Saliendo del programa.");
                return;
            }

            do {
                imprimirMenu();
                opcion = pedirInt(SC, "Selecciona una opción: ");

                switch (opcion) {

                    // ===== XML (BaseX) =====
                    case 1 -> metodosXML.modificarElementoPorID(SC, conexionBaseX);
                    case 2 -> metodosXML.eliminarProductoPorID(SC, conexionBaseX);
                    case 3 -> metodosXML.consulta1_productosOrdenAlfabetico(conexionBaseX);
                    case 4 -> metodosXML.consulta2_disponibilidadMayorQueX(SC, conexionBaseX);
                    case 5 -> metodosXML.consulta3_masCaroPorCategoria(conexionBaseX);
                    case 6 -> metodosXML.consulta4_descripcionContieneSubcadena(SC, conexionBaseX);
                    case 7 -> metodosXML.consulta5_stockTotalYPorcentajePorCategoria(conexionBaseX);

                    // ===== MongoDB =====
                    case 8 -> metodosMongoDB.crearCliente(SC, conexionMongoDB);
                    case 9 -> metodosMongoDB.identificarClientePorEmail(SC, conexionMongoDB, sesion);
                    case 10 -> metodosMongoDB.borrarCliente(SC, conexionMongoDB, sesion);
                    case 11 -> metodosMongoDB.modificarCampoCliente(SC, conexionMongoDB, sesion);

                    case 12 -> {
                        if (!sesion.hayClienteActivo()) {
                            System.out.println("⚠️ Primero selecciona un cliente (opción 9).");
                        } else {
                            metodosMongoDB.anadirProductoAlCarrito(SC, conexionMongoDB, conexionBaseX, sesion);
                        }
                    }

                    case 13 -> {
                        if (!sesion.hayClienteActivo()) {
                            System.out.println("⚠️ Primero selecciona un cliente (opción 9).");
                        } else {
                            metodosMongoDB.mostrarCarrito(conexionMongoDB, sesion);
                        }
                    }

                    case 14 -> {
                        if (!sesion.hayClienteActivo()) {
                            System.out.println("⚠️ Primero selecciona un cliente (opción 9).");
                        } else {
                            metodosMongoDB.mostrarPedidos(conexionMongoDB, sesion);
                        }
                    }

                    case 15 -> {
                        if (!sesion.hayClienteActivo()) {
                            System.out.println("⚠️ Primero selecciona un cliente (opción 9).");
                        } else {
                            metodosMongoDB.pagarCarrito(SC, conexionMongoDB, sesion);
                        }
                    }

                    case 16 -> metodosMongoDB.consulta16_totalCarritoPorCliente(conexionMongoDB);
                    case 17 -> metodosMongoDB.consulta17_totalGastadoPorCliente(conexionMongoDB);

                    case 0 -> System.out.println("Saliendo del programa.");

                    default -> System.out.println("Opción no válida, intenta de nuevo.");
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println("Error de conexión BaseX y/o MongoDB: " + e.getMessage());
        } finally {

            if (conexionBaseX != null) {
                try {
                    conexionBaseX.close();
                    System.out.println("Conexión con BaseX cerrada correctamente.");
                } catch (Exception e) {
                    System.out.println("Error cerrando la conexión con BaseX: " + e.getMessage());
                }
            }

            try {
                ConexionMongoDB.cerrarConexion();
            } catch (Exception e) {
                System.out.println("Error cerrando la conexión con MongoDB: " + e.getMessage());
            }

            // No cierro SC porque cierra System.in y puede dar problemas en IntelliJ
        }
    }

    private static void imprimirMenu() {
        System.out.println("\n===============================");
        System.out.println("TIENDA (BaseX XML + MongoDB)");
        System.out.println("===============================");
        System.out.println("Cliente activo: " + (sesion.hayClienteActivo() ? sesion.getEmail() : "NINGUNO"));

        System.out.println("\n===== MENÚ BASE DE DATOS XML =====");
        System.out.println("1. Modificar valor de un elemento según ID");
        System.out.println("2. Eliminar producto según ID");
        System.out.println("3. Consulta 1: Productos ordenados por nombre");
        System.out.println("4. Consulta 2: Productos con disponibilidad > X");
        System.out.println("5. Consulta 3: Producto más caro por categoría");
        System.out.println("6. Consulta 4: Buscar productos por descripción (subcadena)");
        System.out.println("7. Consulta 5: Stock total por categoría y porcentaje");

        System.out.println("\n===== MENÚ BASE DE DATOS MONGODB =====");
        System.out.println("8. Crear un nuevo cliente");
        System.out.println("9. Identificar cliente según el email (activar cliente)");
        System.out.println("10. Borrar un cliente");
        System.out.println("11. Modificar el valor de un campo del cliente");
        System.out.println("12. Añadir producto al carrito del cliente");
        System.out.println("13. Mostrar el carrito del cliente");
        System.out.println("14. Mostrar pedidos del cliente");
        System.out.println("15. Pagar el carrito del cliente (crear pedido)");
        System.out.println("16. Consulta 1: Total de cada carrito por cliente (orden asc)");
        System.out.println("17. Consulta 2: Total gastado por cada cliente (pedidos)");
        System.out.println("0. Salir");
        System.out.println("--------------------------------------------------------------------");

    }
}
