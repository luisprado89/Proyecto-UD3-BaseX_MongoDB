package com.proyecto401.connection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    /**
     * Conecta a MongoDB y devuelve la base de datos "tienda".
     * Reutiliza la conexión si ya estaba creada.
     */
    public static MongoDatabase conexionMongoDB() {
        if (database != null) return database;

        // Cambia aquí si tu Mongo no está en localhost o usas usuario/clave
        String uri = "mongodb://localhost:27017";

        mongoClient = new MongoClient(new MongoClientURI(uri));
        database = mongoClient.getDatabase("tienda");

        System.out.println("Conectado a MongoDB (BD: tienda)");
        return database;
    }

    /**
     * Cierra la conexión con MongoDB (MongoClient).
     */
    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexión con MongoDB cerrada correctamente.");
        }
    }
}
