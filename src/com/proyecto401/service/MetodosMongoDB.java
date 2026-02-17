package com.proyecto401.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.proyecto401.model.SesionCliente;
import org.basex.examples.api.BaseXClient;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import static com.proyecto401.util.ConsoleIO.*;

public class MetodosMongoDB {

    private static final String COL_CLIENTES = "clientes";

    private MongoCollection<Document> coleccionClientes(MongoDatabase db) {
        return db.getCollection(COL_CLIENTES);
    }

    // =========================
    // OPCIÓN 8: Crear cliente
    // Formato enunciado:
    // carrito: [ {producto_id, nombre, cantidad, precio_unitario}, ... ]
    // pedidos: [ {pedido_id, productos:[...], total, fecha_pedido}, ... ]
    // =========================
    public void crearCliente(Scanner sc, MongoDatabase db) {
        try {
            MongoCollection<Document> col = coleccionClientes(db);

            String nombre = pedirString(sc, "Nombre: ");
            String email = pedirString(sc, "Email: ");
            String direccion = pedirString(sc, "Dirección: ");

            Document cliente = new Document("nombre", nombre)
                    .append("email", email)
                    .append("direccion", direccion)
                    // ✅ Enunciado: carrito es ARRAY
                    .append("carrito", List.of())
                    // ✅ Campo extra (no rompe el enunciado; útil para tu menú)
                    .append("carrito_fecha_actualizacion", new Date())
                    .append("pedidos", List.of());

            col.insertOne(cliente);
            System.out.println("✅ Cliente creado correctamente.");

        } catch (Exception e) {
            System.out.println("❌ Error creando cliente (email duplicado?): " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 9: Identificar cliente por email
    // =========================
    public void identificarClientePorEmail(Scanner sc, MongoDatabase db, SesionCliente sesion) {
        MongoCollection<Document> col = coleccionClientes(db);

        String email = pedirString(sc, "Email del cliente: ");

        Document cliente = col.find(eq("email", email)).first();

        if (cliente == null) {
            System.out.println("❌ No existe cliente con ese email.");
            return;
        }

        sesion.setCliente(cliente.getObjectId("_id"), email);
        System.out.println("✅ Cliente activo: " + email);
    }

    // =========================
    // OPCIÓN 10: Borrar cliente
    // =========================
    public void borrarCliente(Scanner sc, MongoDatabase db, SesionCliente sesion) {
        MongoCollection<Document> col = coleccionClientes(db);

        String email = pedirString(sc, "Email del cliente a borrar: ");

        Document res = col.findOneAndDelete(eq("email", email));

        if (res == null) {
            System.out.println("❌ Cliente no encontrado.");
        } else {
            if (sesion.hayClienteActivo() && email.equals(sesion.getEmail())) {
                sesion.limpiar();
            }
            System.out.println("✅ Cliente eliminado.");
        }
    }

    // =========================
    // OPCIÓN 11: Modificar campo del cliente (activo)
    // =========================
    public void modificarCampoCliente(Scanner sc, MongoDatabase db, SesionCliente sesion) {
        if (!sesion.hayClienteActivo()) {
            System.out.println("⚠️ No hay cliente activo.");
            return;
        }

        System.out.println("Campo a modificar:");
        System.out.println("1. nombre");
        System.out.println("2. direccion");

        int op = pedirInt(sc, "Opción: ");

        String campo = (op == 1) ? "nombre" : (op == 2) ? "direccion" : null;
        if (campo == null) {
            System.out.println("❌ Opción no válida.");
            return;
        }

        String valor = pedirString(sc, "Nuevo valor: ");

        coleccionClientes(db).updateOne(
                eq("_id", sesion.getClienteId()),
                set(campo, valor)
        );

        System.out.println("✅ Campo actualizado.");
    }

    // =========================
    // OPCIÓN 12: Añadir producto al carrito (cliente activo)
    // - Producto debe existir en XML (BaseX) por ID
    // - Inserta item en carrito[] (array)
    // =========================
    public void anadirProductoAlCarrito(Scanner sc, MongoDatabase db, BaseXClient baseX, SesionCliente sesion) {
        try {
            if (!sesion.hayClienteActivo()) {
                System.out.println("⚠️ No hay cliente activo (opción 9).");
                return;
            }

            String seguir;
            do {
                int idProd = pedirInt(sc, "ID del producto: ");
                int cantidad = pedirInt(sc, "Cantidad: ");

                // ✅ OJO: usa exactamente el nombre de tu BD BaseX (en tu proyecto es 'productos')
                String xq = "let $p := /productos/producto[id=" + idProd + "] "
                        + "return concat(normalize-space(string($p/nombre)), '|', normalize-space(string($p/precio)))";

                String resultado;
                try (BaseXClient.Query q = baseX.query(xq)) {
                    resultado = q.more() ? q.next() : null;
                }

                if (resultado == null || resultado.isBlank() || resultado.startsWith("|")) {
                    System.out.println("❌ Producto no existe en XML (ID " + idProd + ").");
                } else {
                    String[] datos = resultado.split("\\|");
                    String nombre = datos[0];
                    double precio = Double.parseDouble(datos[1]);

                    Document item = new Document("producto_id", idProd)
                            .append("nombre", nombre)
                            .append("cantidad", cantidad)
                            .append("precio_unitario", precio);

                    coleccionClientes(db).updateOne(
                            eq("_id", sesion.getClienteId()),
                            combine(
                                    // ✅ Enunciado: carrito es un array
                                    push("carrito", item),
                                    // ✅ Campo auxiliar de control (opcional)
                                    set("carrito_fecha_actualizacion", new Date())
                            )
                    );

                    System.out.println("✅ Producto añadido al carrito.");
                }

                seguir = pedirString(sc, "¿Quieres añadir otro producto? (S/N): ");
            } while (seguir.equalsIgnoreCase("S"));

        } catch (Exception e) {
            System.out.println("❌ Error añadiendo producto al carrito: " + e.getMessage());
        }
    }

    // =========================
    // OPCIÓN 13: Mostrar carrito (cliente activo)
    // - Lee carrito[] (array)
    // - Total según tu criterio anterior: sum(precio_unitario) SIN multiplicar por cantidad
    // =========================
    public void mostrarCarrito(MongoDatabase db, SesionCliente sesion) {
        if (!sesion.hayClienteActivo()) {
            System.out.println("⚠️ No hay cliente activo.");
            return;
        }

        MongoCollection<Document> col = coleccionClientes(db);

        Document cliente = col.find(eq("_id", sesion.getClienteId())).first();
        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado.");
            return;
        }

        List<Document> items = cliente.getList("carrito", Document.class);
        if (items == null || items.isEmpty()) {
            System.out.println("(Carrito vacío)");
            return;
        }

        System.out.println("\n--- Carrito ---");
        for (Document d : items) {
            System.out.println(
                    d.getInteger("producto_id") + " | " +
                            d.getString("nombre") + " | Cant: " +
                            d.getInteger("cantidad") + " | Precio: " +
                            d.get("precio_unitario")
            );
        }

        // Total según enunciado (según lo que vienes usando): sum(precio_unitario) sin multiplicar por cantidad
        AggregateIterable<Document> res = col.aggregate(Arrays.asList(
                new Document("$match", new Document("_id", sesion.getClienteId())),
                new Document("$project", new Document("total",
                        new Document("$ifNull", Arrays.asList(
                                new Document("$sum", "$carrito.precio_unitario"),
                                0
                        ))
                ))
        ));

        Document tot = res.first();
        Object total = (tot != null) ? tot.get("total") : 0;

        System.out.println("TOTAL: " + total);
    }

    // =========================
    // OPCIÓN 14: Mostrar pedidos (cliente activo)
    // =========================
    public void mostrarPedidos(MongoDatabase db, SesionCliente sesion) {
        if (!sesion.hayClienteActivo()) {
            System.out.println("⚠️ No hay cliente activo.");
            return;
        }

        Document cliente = coleccionClientes(db)
                .find(eq("_id", sesion.getClienteId()))
                .first();

        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado.");
            return;
        }

        List<Document> pedidos = cliente.getList("pedidos", Document.class);
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("(Sin pedidos)");
            return;
        }

        System.out.println("\n--- Pedidos ---");
        for (Document p : pedidos) {
            ObjectId pid = p.getObjectId("pedido_id");
            Object fecha = p.get("fecha_pedido");
            Object total = p.get("total");

            System.out.println("Pedido " + pid + " | Total: " + total + " | Fecha: " + fecha);

            List<Document> prods = p.getList("productos", Document.class);
            if (prods != null && !prods.isEmpty()) {
                for (Document pr : prods) {
                    System.out.println("   - " + pr.getInteger("producto_id") + " | " +
                            pr.getString("nombre") + " | Cant: " + pr.getInteger("cantidad") +
                            " | Precio: " + pr.get("precio_unitario"));
                }
            }
        }
    }

    // =========================
    // OPCIÓN 15: Pagar carrito (crear pedido y vaciar carrito)
    // - Crea pedido con productos = carrito[]
    // - total = sum(precio_unitario) sin multiplicar por cantidad (como venías haciendo)
    // =========================
    public void pagarCarrito(Scanner sc, MongoDatabase db, SesionCliente sesion) {
        if (!sesion.hayClienteActivo()) {
            System.out.println("⚠️ No hay cliente activo.");
            return;
        }

        MongoCollection<Document> col = coleccionClientes(db);

        Document cliente = col.find(eq("_id", sesion.getClienteId())).first();
        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado.");
            return;
        }

        List<Document> items = cliente.getList("carrito", Document.class);
        if (items == null || items.isEmpty()) {
            System.out.println("⚠️ Carrito vacío.");
            return;
        }

        System.out.println("\n--- Carrito a pagar ---");
        for (Document d : items) {
            System.out.println(d.getString("nombre") + " | " + d.get("precio_unitario"));
        }

        double total = items.stream()
                .mapToDouble(d -> {
                    Object v = d.get("precio_unitario");
                    if (v instanceof Number n) return n.doubleValue();
                    return Double.parseDouble(String.valueOf(v));
                })
                .sum();

        System.out.println("TOTAL: " + total);

        String conf = pedirString(sc, "¿Confirmar compra? (S/N): ");
        if (!conf.equalsIgnoreCase("S")) {
            System.out.println("❌ Compra cancelada.");
            return;
        }

        Document pedido = new Document("pedido_id", new ObjectId())
                .append("productos", items)
                .append("total", total)
                .append("fecha_pedido", new Date());

        col.updateOne(
                eq("_id", sesion.getClienteId()),
                combine(
                        push("pedidos", pedido),
                        // ✅ Vaciar carrito (array)
                        set("carrito", List.of()),
                        set("carrito_fecha_actualizacion", new Date())
                )
        );

        System.out.println("✅ Pedido realizado. Total: " + total);
    }

    // =========================
    // OPCIÓN 16: Total carrito por cliente (agregación)
    // - total = sum(carrito.precio_unitario)
    // - orden asc
    // =========================
    public void consulta16_totalCarritoPorCliente(MongoDatabase db) {
        MongoCollection<Document> col = coleccionClientes(db);

        AggregateIterable<Document> res = col.aggregate(Arrays.asList(
                new Document("$project", new Document("email", 1)
                        .append("total", new Document("$ifNull", Arrays.asList(
                                new Document("$sum", "$carrito.precio_unitario"),
                                0
                        )))
                ),
                new Document("$sort", new Document("total", 1))
        ));

        System.out.println("\n--- Total de carrito por cliente (orden asc) ---");
        for (Document d : res) {
            System.out.println(d.getString("email") + " | Total: " + d.get("total"));
        }
    }

    // =========================
    // OPCIÓN 17: Total gastado por cliente (agregación)
    // - total = sum(pedidos.total)
    // =========================
    public void consulta17_totalGastadoPorCliente(MongoDatabase db) {
        MongoCollection<Document> col = coleccionClientes(db);

        AggregateIterable<Document> res = col.aggregate(Arrays.asList(
                new Document("$project", new Document("email", 1)
                        .append("total", new Document("$ifNull", Arrays.asList(
                                new Document("$sum", "$pedidos.total"),
                                0
                        )))
                )
        ));

        System.out.println("\n--- Total gastado por cliente ---");
        for (Document d : res) {
            System.out.println(d.getString("email") + " | Total: " + d.get("total"));
        }
    }
}
