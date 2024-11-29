package com.example.geometrix;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LexicalAnalyzerGUI extends Application {

    // Tabla de símbolos (con columnas para "Token" y "Tipo")
    private final ObservableList<Symbol> symbolTableData = FXCollections.observableArrayList();
    private TableView<Symbol> symbolTable = new TableView<>(symbolTableData);
    private TextArea codeInputArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Crear el layout principal
        BorderPane root = new BorderPane();

        // Crear el VBox para la entrada de código
        VBox inputArea = createInputArea();

        // Crear el VBox para la tabla de símbolos
        VBox symbolTableArea = createSymbolTableArea();

        // Organizar los VBox en el layout principal
        root.setLeft(inputArea);
        root.setRight(symbolTableArea);

        // Crear la escena y mostrarla
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("Analizador Léxico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para crear el VBox que contiene el área de entrada de código y el botón
    private VBox createInputArea() {
        // Configurar la caja de texto para el código
        codeInputArea.setWrapText(true);
        codeInputArea.setPrefHeight(300);

        // Crear el botón para procesar el código
        Button processButton = new Button("Procesar Código");
        processButton.setOnAction(e -> processCode());

        // Organizar los componentes en un VBox
        VBox inputArea = new VBox(10, new Label("Ingrese su código:"), codeInputArea, processButton);
        return inputArea;
    }

    // Método para crear el VBox que contiene la tabla de símbolos
    private VBox createSymbolTableArea() {
        // Configurar la tabla de símbolos
        TableColumn<Symbol, String> tokenColumn = new TableColumn<>("Token");
        tokenColumn.setCellValueFactory(cellData -> cellData.getValue().tokenProperty());

        TableColumn<Symbol, String> typeColumn = new TableColumn<>("Tipo");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        symbolTable.getColumns().addAll(tokenColumn, typeColumn);

        // Crear el VBox para la tabla de símbolos
        VBox symbolTableArea = new VBox(10, new Label("Tabla de Símbolos:"), symbolTable);
        symbolTableArea.setPrefWidth(300);  // Establecer un ancho para la tabla

        return symbolTableArea;
    }

    // Método para procesar el código y actualizar la tabla de símbolos
    private void processCode() {
        // Limpiar la tabla de símbolos
        symbolTableData.clear();

        // Simulación del análisis léxico
        String code = codeInputArea.getText();
        String[] tokens = tokenizeCode(code);

        // Añadir cada token a la tabla de símbolos
        for (String token : tokens) {
            String type = determineTokenType(token);
            symbolTableData.add(new Symbol(token, type));
        }

        // Refrescar la tabla de símbolos después de agregar los nuevos datos
        symbolTable.refresh();
    }

    // Método para dividir el código en tokens
    private String[] tokenizeCode(String code) {
        // Expresión regular que cubre números, palabras clave, identificadores y operadores
        return code.split("(?=[-+*/=;(){}])|(?<=[-+*/=;(){}])|\\s+");
    }

    // Método para determinar el tipo de token
    private String determineTokenType(String token) {
        // Clasificación de tokens basados en las reglas de GeoMatrix

        // Palabras reservadas de acciones
        if (token.equalsIgnoreCase("CREAR") ||
                token.equalsIgnoreCase("CALCULAR") ||
                token.equalsIgnoreCase("TRANSFORMAR") ||
                token.equalsIgnoreCase("MODIFICAR")) {
            return "Palabra Reservada (Acción)";
        }

        // Palabras reservadas de figuras geométricas
        if (token.equalsIgnoreCase("CUADRADO") ||
                token.equalsIgnoreCase("RECTANGULO") ||
                token.equalsIgnoreCase("CIRCULO") ||
                token.equalsIgnoreCase("TRIANGULO")) {
            return "Palabra Reservada (Figura)";
        }

        // Palabras reservadas de operaciones
        if (token.equalsIgnoreCase("AREA") ||
                token.equalsIgnoreCase("PERIMETRO")) {
            return "Palabra Reservada (Operación)";
        }

        // Palabras reservadas de atributos
        if (token.equalsIgnoreCase("COLOR") ||
                token.equalsIgnoreCase("POSICION") ||
                token.equalsIgnoreCase("DIMENSION")) {
            return "Palabra Reservada (Atributo)";
        }

        // Colores representados como números
        if (token.matches("[1-4]")) {
            return "Número (Color)";
        }

        // Coordenadas o dimensiones (números enteros o negativos)
        if (token.matches("-?\\d+")) {
            return "Número (Parámetro)";
        }

        // Operadores y símbolos
        if (token.matches("[=;,(){}]")) {
            return "Símbolo";
        }

        // Identificadores (letras mayúsculas y números, hasta 15 caracteres)
        if (token.matches("[A-Z][A-Z0-9]{0,14}")) {
            return "Identificador";
        }

        // Si no coincide con ningún caso
        return "Desconocido";
    }


    // Clase para representar un símbolo en la tabla de símbolos
    public static class Symbol {
        private final SimpleStringProperty token;
        private final SimpleStringProperty type;

        public Symbol(String token, String type) {
            this.token = new SimpleStringProperty(token);
            this.type = new SimpleStringProperty(type);
        }

        public String getToken() {
            return token.get();
        }

        public void setToken(String token) {
            this.token.set(token);
        }

        public SimpleStringProperty tokenProperty() {
            return token;
        }

        public String getType() {
            return type.get();
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }
    }
}
