package com.proyecto401.model;

import org.bson.types.ObjectId;

public class SesionCliente {

    private ObjectId clienteId;
    private String email;

    public boolean hayClienteActivo() {
        return clienteId != null;
    }

    public void setCliente(ObjectId clienteId, String email) {
        this.clienteId = clienteId;
        this.email = (email == null) ? null : email.trim();
    }

    public void limpiar() {
        this.clienteId = null;
        this.email = null;
    }

    public ObjectId getClienteId() {
        return clienteId;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailParaMenu() {
        return hayClienteActivo() ? email : "NINGUNO";
    }

    @Override
    public String toString() {
        if (!hayClienteActivo()) return "NINGUNO";
        return email + " (" + clienteId + ")";
    }
}
