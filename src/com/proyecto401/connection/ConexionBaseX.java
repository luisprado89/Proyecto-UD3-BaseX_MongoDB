package com.proyecto401.connection;

import org.basex.examples.api.BaseXClient;

public class ConexionBaseX {

    /**
     * Conecta con BaseX y abre la base de datos "productos".
     */
    public static BaseXClient conexionBaseX() {
        try {
            // Datos por defecto BaseX (ajusta si usas otros)
            String host = "localhost";
            int port = 1984;
            String user = "admin";
            String password = "abc123";

            BaseXClient session = new BaseXClient(host, port, user, password);

            // Abrir BD
            session.execute("open productos");

            System.out.println("Conectado a BaseX (BD: productos)");
            return session;

        } catch (Exception e) {
            System.out.println("Error conectando a BaseX: " + e.getMessage());
            return null;
        }
    }
}
