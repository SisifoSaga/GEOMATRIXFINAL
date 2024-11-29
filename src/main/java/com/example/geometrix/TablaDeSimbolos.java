package com.example.geometrix;
import java.util.HashMap;
import java.util.Map;

public class TablaDeSimbolos {
    private Map<String, String> tabla;  // Mapa para almacenar el símbolo y su identificador único
    private int contador;               // Contador para generar identificadores únicos

    public TablaDeSimbolos() {
        tabla = new HashMap<>();
        contador = 1;  // Comienza con A01
    }

    // Método para agregar un símbolo a la tabla
    public void agregarSimbolo(String simbolo, String tipo) {
        // Si el símbolo ya existe, reutiliza su valor actual
        if (!tabla.containsKey(simbolo)) {
            String valor = "A" + String.format("%02d", contador++);
            tabla.put(simbolo, valor);
        }
    }

    // Método para obtener el valor del símbolo
    public String obtenerValor(String simbolo) {
        return tabla.getOrDefault(simbolo, "Desconocido");
    }

    // Método para obtener el tipo de símbolo
    public String obtenerTipo(String simbolo) {
        if (tabla.containsKey(simbolo)) {
            return tipoDeSimbolo(simbolo);  // Determina el tipo según la lógica definida
        }
        return "Desconocido";
    }

    // Método para imprimir la tabla de símbolos
    public void imprimirTabla() {
        System.out.println("Símbolo | Tipo | Valor");
        for (Map.Entry<String, String> entry : tabla.entrySet()) {
            System.out.println(entry.getKey() + " | " + obtenerTipo(entry.getKey()) + " | " + entry.getValue());
        }
    }

    // Método para determinar el tipo de un símbolo
    private String tipoDeSimbolo(String simbolo) {
        if (simbolo.equals("crea") || simbolo.equals("entero")) {
            return "PALABRA_RESERVADA";
        } else if (simbolo.matches("\\d+")) {
            return "NUMERO";
        } else {
            return "IDENTIFICADOR";
        }
    }

    public static void main(String[] args) {
        TablaDeSimbolos tabla = new TablaDeSimbolos();

        // Agregar símbolos a la tabla (en este caso reutilizando valores para los que ya existen)
        tabla.agregarSimbolo("crea", "PALABRA_RESERVADA");
        tabla.agregarSimbolo("entero", "PALABRA_RESERVADA");
        tabla.agregarSimbolo("tamaño", "IDENTIFICADOR");
        tabla.agregarSimbolo("100", "NUMERO");

        // Repetir un símbolo
        tabla.agregarSimbolo("entero", "PALABRA_RESERVADA");
        tabla.agregarSimbolo("tamaño", "IDENTIFICADOR");

        // Imprimir la tabla de símbolos
        tabla.imprimirTabla();
    }
}
