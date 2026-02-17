package com.proyecto401.service;

import org.basex.examples.api.BaseXClient;

import java.util.Scanner;

import static com.proyecto401.util.ConsoleIO.*;

public class MetodosXML {

    // =========================
    // OPCIÓN 1: Modificar valor de un elemento según ID
    // (Incluye: especificaciones como nodo XML completo)
    // =========================
    public void modificarElementoPorID(Scanner sc, BaseXClient conexionBaseX) {
        try {
            int id = pedirInt(sc, "Introduce el ID del producto: ");

            // Comprobar si existe el producto
            if (!existeProducto(conexionBaseX, id)) {
                System.out.println("❌ No existe ningún producto con ID " + id);
                return;
            }

            System.out.println("¿Qué campo quieres modificar?");
            System.out.println("1. nombre");
            System.out.println("2. descripcion");
            System.out.println("3. precio");
            System.out.println("4. disponibilidad");
            System.out.println("5. categoria");
            System.out.println("6. fabricante");
            System.out.println("7. especificaciones (nodo XML)");
            int opcion = pedirInt(sc, "Elige opción: ");

            String xq;

            switch (opcion) {
                case 1 -> {
                    String nuevo = pedirString(sc, "Nuevo valor para 'nombre': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/nombre/text() " +
                            "with '" + escapeApos(nuevo) + "'";
                }
                case 2 -> {
                    String nuevo = pedirString(sc, "Nuevo valor para 'descripcion': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/descripcion/text() " +
                            "with '" + escapeApos(nuevo) + "'";
                }
                case 3 -> {
                    double nuevo = pedirDouble(sc, "Nuevo valor para 'precio': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/precio/text() " +
                            "with " + nuevo;
                }
                case 4 -> {
                    int nuevo = pedirInt(sc, "Nuevo valor para 'disponibilidad': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/disponibilidad/text() " +
                            "with " + nuevo;
                }
                case 5 -> {
                    String nuevo = pedirString(sc, "Nuevo valor para 'categoria': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/categoria/text() " +
                            "with '" + escapeApos(nuevo) + "'";
                }
                case 6 -> {
                    String nuevo = pedirString(sc, "Nuevo valor para 'fabricante': ");
                    xq = "replace value of node /productos/producto[id=" + id + "]/fabricante/text() " +
                            "with '" + escapeApos(nuevo) + "'";
                }
                case 7 -> {
                    // Reemplazar el nodo completo <especificaciones>...</especificaciones>
                    System.out.println("Introduce el XML del nodo <especificaciones>...</especificaciones>");
                    System.out.println("Termina con una línea vacía (ENTER).");

                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String linea = sc.nextLine();
                        if (linea.isBlank()) break;
                        sb.append(linea).append("\n");
                    }

                    String nuevoNodo = sb.toString().trim();

                    // Si el usuario no escribe nada, ponemos nodo vacío
                    if (nuevoNodo.isBlank()) {
                        nuevoNodo = "<especificaciones></especificaciones>";
                    }

                    // Validación mínima: que empiece por <especificaciones
                    if (!nuevoNodo.startsWith("<especificaciones")) {
                        System.out.println("❌ Debe empezar por <especificaciones> ... </especificaciones>.");
                        return;
                    }

                    // OJO: aquí no usamos comillas porque insert node necesita parsear XML
                    xq = "replace node /productos/producto[id=" + id + "]/especificaciones " +
                            "with " + nuevoNodo;
                }
                default -> {
                    System.out.println("⚠️ Opción no válida.");
                    return;
                }
            }

            conexionBaseX.execute("xquery " + xq);
            System.out.println("✅ Producto " + id + " actualizado correctamente.");

        } catch (Exception e) {
            System.out.println("❌ Error en modificarElementoPorID: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 2: Eliminar producto según ID
    // =========================
    public void eliminarProductoPorID(Scanner sc, BaseXClient conexionBaseX) {
        try {
            int id = pedirInt(sc, "Introduce el ID del producto a eliminar: ");

            if (!existeProducto(conexionBaseX, id)) {
                System.out.println("❌ No existe ningún producto con ID " + id);
                return;
            }

            String xq = "delete node /productos/producto[id = " + id + "]";
            conexionBaseX.execute("xquery " + xq);

            System.out.println("✅ Producto con ID " + id + " eliminado.");

        } catch (Exception e) {
            System.out.println("❌ Error en eliminarProductoPorID: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 3: Consulta 1 - Productos orden alfabético por nombre
    // Campos: id, nombre, precio, disponibilidad, categoria
    // =========================
    public void consulta1_productosOrdenAlfabetico(BaseXClient conexionBaseX) {
        try {
            String xq = ""
                    + "for $p in /productos/producto "
                    + "order by $p/nombre "
                    + "return concat("
                    + "'ID: ', normalize-space($p/id), ' | ', "
                    + "'Nombre: ', normalize-space($p/nombre), ' | ', "
                    + "'Precio: ', normalize-space($p/precio), ' | ', "
                    + "'Disponibilidad: ', normalize-space($p/disponibilidad), ' | ', "
                    + "'Categoria: ', normalize-space($p/categoria)"
                    + ")";

            System.out.println("\n--- Consulta 1: Productos ordenados por nombre ---");
            ejecutarConsultaYMostrar(conexionBaseX, xq);

        } catch (Exception e) {
            System.out.println("❌ Error en consulta1: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 4: Consulta 2 - Productos con disponibilidad > X
    // =========================
    public void consulta2_disponibilidadMayorQueX(Scanner sc, BaseXClient conexionBaseX) {
        try {
            int x = pedirInt(sc, "Introduce X (disponibilidad mínima): ");

            String xq = ""
                    + "for $p in /productos/producto "
                    + "where xs:integer(normalize-space($p/disponibilidad)) > " + x + " "
                    + "order by $p/nombre "
                    + "return concat("
                    + "'ID: ', normalize-space($p/id), ' | ', "
                    + "'Nombre: ', normalize-space($p/nombre), ' | ', "
                    + "'Precio: ', normalize-space($p/precio), ' | ', "
                    + "'Disponibilidad: ', normalize-space($p/disponibilidad), ' | ', "
                    + "'Categoria: ', normalize-space($p/categoria)"
                    + ")";

            System.out.println("\n--- Consulta 2: Productos con disponibilidad > " + x + " ---");
            ejecutarConsultaYMostrar(conexionBaseX, xq);

        } catch (Exception e) {
            System.out.println("❌ Error en consulta2: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 5: Consulta 3 - Producto más caro por categoría
    // =========================
    public void consulta3_masCaroPorCategoria(BaseXClient conexionBaseX) {
        try {
            String xq = ""
                    + "for $c in distinct-values(/productos/producto/categoria) "
                    + "let $prods := /productos/producto[categoria = $c] "
                    + "let $max := max($prods/precio ! xs:decimal(normalize-space(.))) "
                    + "let $p := ($prods[xs:decimal(normalize-space(precio)) = $max])[1] "
                    + "order by $c "
                    + "return concat("
                    + "'Categoria: ', $c, ' | ', "
                    + "'Nombre: ', normalize-space($p/nombre), ' | ', "
                    + "'Precio: ', normalize-space($p/precio)"
                    + ")";

            System.out.println("\n--- Consulta 3: Producto más caro por categoría ---");
            ejecutarConsultaYMostrar(conexionBaseX, xq);

        } catch (Exception e) {
            System.out.println("❌ Error en consulta3: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 6: Consulta 4 - descripcion contiene subcadena
    // Orden: fabricante inverso al alfabeto
    // =========================
    public void consulta4_descripcionContieneSubcadena(Scanner sc, BaseXClient conexionBaseX) {
        try {
            String sub = pedirString(sc, "Introduce subcadena a buscar en descripcion: ");

            String xq = ""
                    + "for $p in /productos/producto "
                    + "where contains(lower-case(normalize-space(string($p/descripcion))), lower-case('" + escapeApos(sub) + "')) "
                    + "order by $p/fabricante descending "
                    + "return concat("
                    + "'Nombre: ', normalize-space($p/nombre), ' | ', "
                    + "'Fabricante: ', normalize-space($p/fabricante)"
                    + ")";

            System.out.println("\n--- Consulta 4: Descripción contiene '" + sub + "' (fabricante Z-A) ---");
            ejecutarConsultaYMostrar(conexionBaseX, xq);

        } catch (Exception e) {
            System.out.println("❌ Error en consulta4: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 7: Consulta 5 - Stock total por categoría y % sobre total
    // =========================
    public void consulta5_stockTotalYPorcentajePorCategoria(BaseXClient conexionBaseX) {
        try {
            String xq = ""
                    + "let $prods := /productos/producto "
                    + "let $total := sum($prods/disponibilidad ! xs:integer(normalize-space(.))) "
                    + "for $c in distinct-values($prods/categoria) "
                    + "let $stock := sum($prods[categoria = $c]/disponibilidad ! xs:integer(normalize-space(.))) "
                    + "let $pct := if ($total = 0) then 0 else round-half-to-even(($stock div $total) * 100, 2) "
                    + "order by $c "
                    + "return concat("
                    + "'Categoria: ', $c, ' | ', "
                    + "'Stock: ', $stock, ' | ', "
                    + "'Porcentaje: ', $pct, '%'"
                    + ")";

            System.out.println("\n--- Consulta 5: Stock total y porcentaje por categoría ---");
            ejecutarConsultaYMostrar(conexionBaseX, xq);

        } catch (Exception e) {
            System.out.println("❌ Error en consulta5: " + e.getMessage());
        }
    }

    // =========================================================
    // Helpers
    // =========================================================

    private void ejecutarConsultaYMostrar(BaseXClient conexionBaseX, String xq) throws Exception {
        boolean hayResultados = false;
        try (BaseXClient.Query query = conexionBaseX.query(xq)) {
            while (query.more()) {
                hayResultados = true;
                System.out.println(query.next());
            }
        }
        if (!hayResultados) System.out.println("(Sin resultados)");
    }

    private boolean existeProducto(BaseXClient conexionBaseX, int id) {
        try {
            String xq = "exists(/productos/producto[id = " + id + "])";
            try (BaseXClient.Query q = conexionBaseX.query(xq)) {
                String res = q.more() ? q.next() : "false";
                return "true".equalsIgnoreCase(res);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
