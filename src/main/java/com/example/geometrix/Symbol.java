package com.example.geometrix;

public class Symbol {

    // Atributos principales de un símbolo
    private int id;                  // Identificador único del símbolo
    private String name;             // Nombre del símbolo (por ejemplo, "CUADRADO1")
    private String type;             // Tipo del símbolo (por ejemplo, "Figura", "Palabra Reservada")
    private String value;            // Valor asociado al símbolo (puede ser una dimensión, color, etc.)

    // Constructor parametrizado
    public Symbol(int id, String name, String type, String value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
