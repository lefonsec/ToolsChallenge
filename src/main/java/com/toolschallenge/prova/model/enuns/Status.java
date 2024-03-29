package com.toolschallenge.prova.model.enuns;

public enum Status {
    AUTORIZADO("AUTORIZADO"), NEGADO("NEGADO"), CANCELADO("CANCELADO");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
