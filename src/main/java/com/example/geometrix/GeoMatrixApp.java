package com.example.geometrix;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeoMatrixApp extends Application {
    private Map<String, Shape> predefinedShapes = new HashMap<>();
    private ObservableList<Symbol> symbolList = FXCollections.observableArrayList();
    private TableView<Symbol> symbolTable;
    private StackPane displayPane;
    private TextArea errorStackArea;
    private Map<String, Integer> symbolIds = new HashMap<>();
    private int nextId = 1;

    // Historial de comandos
    private final ObservableList<String> commandHistory = FXCollections.observableArrayList();
    private int historyIndex = -1;  // Indice para navegar por el historial

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GeoMatrix Interpreter");

        // Inicializar componentes
        symbolTable = createSymbolTable();
        displayPane = new StackPane();
        displayPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        displayPane.setPrefSize(400, 400);  // Visualización cuadrada

        // Configurar la terminal (TextArea)
        errorStackArea = new TextArea();
        errorStackArea.setPromptText("Escribe tu código aquí...");
        errorStackArea.setWrapText(true);
        errorStackArea.setPrefWidth(600);  // Hacer la terminal más ancha
        errorStackArea.setPrefHeight(150);  // Ajustar el alto de la terminal para que no sea tan largo

        // Configurar comportamiento al presionar teclas en el TextArea (historial y ejecución)
        errorStackArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume(); // Evitar salto de línea
                String code = errorStackArea.getText().trim(); // Obtener el comando ingresado
                if (!code.isEmpty()) {
                    executeCode(code); // Procesar el comando ingresado
                    errorStackArea.clear(); // Limpiar la terminal para nuevos comandos
                    addToHistory(code);  // Agregar el comando al historial
                }
            } else if (event.getCode() == KeyCode.UP) {
                event.consume(); // Evitar que se procese el evento normalmente
                navigateHistory(true);  // Navegar hacia arriba en el historial
            } else if (event.getCode() == KeyCode.DOWN) {
                event.consume(); // Evitar que se procese el evento normalmente
                navigateHistory(false);  // Navegar hacia abajo en el historial
            }
        });

        // Crear la imagen de encabezado
        Image encabezadoImage = new Image(getClass().getResourceAsStream("/encabezado.png"));
        ImageView encabezadoImageView = new ImageView(encabezadoImage);
        encabezadoImageView.setFitWidth(1000);  // Ajustar el tamaño si es necesario
        encabezadoImageView.setPreserveRatio(true);  // Mantener las proporciones

        // Crear el botón para abrir el manual de usuario
        Button btnManual = new Button("Abrir Manual de Usuario");
        btnManual.setOnAction(e -> {
            UserManual userManual = new UserManual();
            userManual.show();
        });

        // Crear los paneles
        VBox symbolPanel = new VBox(10, new Label("Tabla de Símbolos"), symbolTable);
        symbolPanel.setPadding(new Insets(10));
        symbolPanel.setPrefWidth(300);  // Ancho fijo de la tabla de símbolos

        VBox displayPanel = new VBox(10, new Label("Visualización:"), displayPane);
        displayPanel.setPadding(new Insets(10));

        // Cambiar el tamaño de la terminal y hacerla más ancha
        VBox errorPanel = new VBox(10, new Label("Terminal:"), errorStackArea);
        errorPanel.setPadding(new Insets(10));

        // Cambiar el orden para que la tabla de símbolos esté al lado derecho de la visualización
        HBox mainLayout = new HBox(10, displayPanel, symbolPanel);
        mainLayout.setHgrow(displayPanel, Priority.ALWAYS);  // La visualización ocupa el máximo espacio disponible

        // Crear el layout principal con la visualización y la tabla de símbolos
        VBox rootLayout = new VBox(10, encabezadoImageView, btnManual, mainLayout, errorPanel);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);  // El HBox ocupa toda la altura disponible

        // Configurar la escena
        Scene scene = new Scene(rootLayout, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para agregar un comando al historial
    private void addToHistory(String command) {
        // Evitar agregar duplicados en el historial
        if (commandHistory.isEmpty() || !commandHistory.get(commandHistory.size() - 1).equals(command)) {
            commandHistory.add(command);
        }
        historyIndex = commandHistory.size(); // Resetear al final del historial
    }

    // Método para navegar por el historial
    private void navigateHistory(boolean up) {
        if (up) {
            // Navegar hacia atrás en el historial
            if (historyIndex > 0) {
                historyIndex--;
            }
        } else {
            // Navegar hacia adelante en el historial
            if (historyIndex < commandHistory.size() - 1) {
                historyIndex++;
            }
        }

        // Establecer el comando actual en el TextArea
        if (historyIndex >= 0 && historyIndex < commandHistory.size()) {
            errorStackArea.setText(commandHistory.get(historyIndex));
        }
    }




    private void executeCode(String code) {
        String[] lines = code.split("\n");

        for (String line : lines) {
            line = line.trim(); // Limpia espacios iniciales y finales

            // Ignorar líneas de salida que comiencen con "RESULTADO:" o "ERROR:"
            if (line.startsWith("RESULTADO:") || line.startsWith("ERROR:")) {
                continue; // Salta estas líneas
            }

            // Validar que las figuras estén en mayúsculas
            if (line.contains("CREAR") || line.contains(".POSICION") || line.contains(".DIMENSION") || line.contains(".COLOR")) {
                String figureName = extractFigureName(line);  // Esta función extraerá el nombre de la figura (por ejemplo, CREAR.CUADRADO)

                if (figureName != null && !figureName.equals(figureName.toUpperCase())) {
                    logError("Error: El nombre de la figura debe estar en mayúsculas: " + figureName);
                    return;  // No procesar si el nombre de la figura está en minúsculas
                }
            }

            tokenizeAndAddToSymbolTable(line);

            try {
                // Añadir el comando al historial
                if (!line.isEmpty()) {
                    addToHistory(line);  // Agrega el comando al historial
                }

                // Procesar el comando según su tipo
                if (line.contains("=") && line.contains("CREAR")) {
                    processCreateCommand(line);
                } else if (line.startsWith("CALCULAR.AREA")) {
                    processCalculateAreaCommand(line);
                } else if (line.startsWith("CALCULAR.PERIMETRO")) {
                    processCalculatePerimeterCommand(line);
                } else if (line.contains(".ESCALAR") || line.contains(".ROTAR")) {
                    processTransformCommand(line);
                } else if (line.contains(".POSICION")) {  // Comando de posición
                    processModifyPositionCommand(line);
                } else if (line.contains(".DIMENSION")) {  // Comando de dimensiones
                    processModifyDimensionCommand(line);
                } else if (line.startsWith("ELIMINAR.")) {
                    processDeleteCommand(line); // Aquí se llama a processDeleteCommand
                } else if (line.contains(".COLOR")) {  // Comando de color
                    processModifyColorCommand(line);  // Llamada al procesador de color
                } else if (!line.isEmpty()) {
                    logError("Error: Comando no reconocido: " + line);
                }
            } catch (Exception e) {
                logError("Error al procesar línea: " + line + " (" + e.getMessage() + ")");
            }
        }
    }

    // Función para extraer el nombre de la figura del comando
    private String extractFigureName(String line) {
        String[] parts = line.split("\\.");
        if (parts.length > 1) {
            return parts[1].split("\\(")[0].trim();  // Extraer la figura antes del paréntesis
        }
        return null;
    }



    private void processModifyColorCommand(String line) {
        try {
            // Extraemos el identificador y el valor de color
            String[] parts = line.split("\\.");
            String identifier = parts[0].trim();
            String[] colorParts = parts[1].replace("COLOR(", "").replace(")", "").split(",");

            if (colorParts.length != 1) {
                logError("Error: Comando de color mal estructurado.");
                return;
            }

            String colorValue = colorParts[0].trim();

            // Limpiar el valor del color, eliminando posibles caracteres extra como ';'
            colorValue = colorValue.replace(";", "").trim();

            // Verificar que la figura exista
            if (predefinedShapes.containsKey(identifier)) {
                Shape shape = predefinedShapes.get(identifier);

                // Llamar a la función que modifica el color
                modifyColor(shape, colorValue);
            } else {
                logError("Figura no encontrada: " + identifier);
            }
        } catch (Exception e) {
            logError("Error al procesar COMANDO .COLOR: " + e.getMessage());
        }
    }




    private void processCreateCommand(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            logError("Sintaxis incorrecta: Falta '=' o hay demasiados '=' en la línea.");
            return;
        }

        String identifier = parts[0].trim();

        // Validar identificador
        if (!isValidIdentifier(identifier)) {
            logError("Error: Identificador no válido: " + identifier);
            return;
        }

        // Validar que el identificador no esté en uso
        if (predefinedShapes.containsKey(identifier)) {
            logError("Error: El identificador " + identifier + " ya está en uso.");
            return;  // No permitir crear una nueva figura con el mismo identificador
        }

        String command = parts[1].trim().replace(";", "");

        if (command.startsWith("CREAR")) {
            try {
                String[] commandParts = command.split("\\(");
                if (commandParts.length != 2) {
                    logError("Sintaxis incorrecta: Falta '(' o ')' en el comando.");
                    return;
                }

                String shapeType = commandParts[0].split("\\.")[1].trim();
                String[] params = commandParts[1].replace(")", "").split(",");

                // Validar parámetros según el tipo de figura
                int expectedParams = getExpectedParameterCount(shapeType);
                if (params.length != expectedParams) {
                    logError("Número incorrecto de parámetros para " + shapeType + ": Se esperaban " +
                            expectedParams + " parámetros.");
                    return;
                }

                // Procesar parámetros comunes
                int size1 = Integer.parseInt(params[0].trim());
                int size2 = (shapeType.equalsIgnoreCase("RECTANGULO")) ?
                        Integer.parseInt(params[1].trim()) : size1;
                int colorCode = Integer.parseInt(params[shapeType.equalsIgnoreCase("RECTANGULO") ? 2 : 1].trim());
                int posX = Integer.parseInt(params[shapeType.equalsIgnoreCase("RECTANGULO") ? 3 : 2].trim());
                int posY = Integer.parseInt(params[shapeType.equalsIgnoreCase("RECTANGULO") ? 4 : 3].trim());

                // Validar coordenadas
                if (!isValidCoordinate(posX) || !isValidCoordinate(posY)) {
                    logError("Coordenadas fuera de rango (-100 a 100).");
                    return;
                }

                // Validar color
                Color color = getColorByCode(colorCode);
                if (color == null) {
                    logError("Código de color no válido.");
                    return;
                }

                // Crear figura
                Shape shape = createShape(shapeType, size1, size2, color);

                if (shape == null) {
                    logError("Tipo de figura no reconocido: " + shapeType);
                    return;
                }

                // Configurar posición y registrar figura
                shape.setTranslateX(posX);
                shape.setTranslateY(posY);
                predefinedShapes.put(identifier, shape);
                displayPane.getChildren().add(shape);
                addToSymbolTable(identifier, "Figura", shapeType);

            } catch (NumberFormatException e) {
                logError("Parámetros inválidos: Se esperaban valores numéricos enteros. " +
                        e.getMessage());
            } catch (Exception e) {
                logError("Error al procesar comando 'CREAR': " + e.getMessage());
            }
        } else {
            logError("Comando no reconocido: " + command);
        }
    }


    // Validar si el identificador es válido
    private boolean isValidIdentifier(String identifier) {
        // Regla 1: El identificador debe comenzar con una letra mayúscula (A-Z)
        if (!Character.isUpperCase(identifier.charAt(0))) {
            return false;
        }

        // Regla 2: Solo puede contener letras mayúsculas (A-Z) y números (0-9)
        if (!identifier.matches("[A-Z0-9]+")) {
            return false;
        }

        // Regla 3: Longitud máxima de 15 caracteres
        if (identifier.length() > 15) {
            return false;
        }

        // Regla 4: No se permiten caracteres especiales ni espacios
        if (identifier.contains(" ") || identifier.contains("_") || identifier.contains("-") || identifier.contains("#")) {
            return false;
        }

        // Regla 5: No puede coincidir con palabras reservadas
        List<String> reservedWords = Arrays.asList("CREAR", "AREA", "RECTANGULO", "CUADRADO", "CIRCULO", "TRIANGULO", "PERIMETRO", "DIMENSION", "POSICION", "COLOR");
        if (reservedWords.contains(identifier)) {
            return false;
        }

        return true;
    }


    // Validar si la línea contiene solo caracteres válidos
    private boolean isValidLine(String line) {
        String regex = "[a-zA-Z0-9().,=;\\s]+";
        return line.matches(regex);
    }

    // Validar identificadores

    // Añadir funcionalidad para ELIMINAR.<identificador>;
    private void processDeleteCommand(String line) {
        try {
            String identifier = line.split("\\.")[1].replace(";", "").trim();
            if (predefinedShapes.containsKey(identifier)) {
                Shape shape = predefinedShapes.remove(identifier);
                displayPane.getChildren().remove(shape);
                symbolIds.remove(identifier);
                symbolList.removeIf(symbol -> symbol.getName().equals(identifier));
                logResult("Figura eliminada: " + identifier);
            } else {
                logError("Figura no encontrada: " + identifier);
            }
        } catch (Exception e) {
            logError("Error al procesar ELIMINAR: " + e.getMessage());
        }
    }

    // Retorna la cantidad esperada de parámetros según el tipo de figura
    private int getExpectedParameterCount(String shapeType) {
        switch (shapeType.toUpperCase()) {
            case "RECTANGULO":
                return 5; // ancho, alto, color, X, Y
            case "CUADRADO":
            case "CIRCULO":
            case "TRIANGULO":
                return 4; // lado/radio, color, X, Y
            default:
                return 0; // Tipo desconocido
        }
    }


    private void processCalculateAreaCommand(String line) {
        try {
            // Limpia espacios extra en toda la línea
            line = line.replace(";", "").trim();
            String identifier = extractIdentifier(line);

            Shape shape = predefinedShapes.get(identifier);
            if (shape != null) {
                double area = calculateArea(shape);
                logResult("Área de " + identifier + ": " + area);
                addToSymbolTable(identifier + ".AREA", "Cálculo", String.valueOf(area));
            } else {
                logError("Figura no encontrada: " + identifier);
            }
        } catch (Exception e) {
            logError("Error al procesar CALCULAR.AREA: " + e.getMessage());
        }
    }

    private void processCalculatePerimeterCommand(String line) {
        try {
            // Limpia espacios y caracteres innecesarios en la línea
            line = line.replace(";", "").trim();
            String identifier = extractIdentifier(line);

            // Verificar que la figura exista
            if (!predefinedShapes.containsKey(identifier)) {
                logError("Figura no encontrada: " + identifier);
                return;
            }

            // Obtener la figura y calcular el perímetro
            Shape shape = predefinedShapes.get(identifier);
            double perimeter = calculatePerimeter(shape);
            logResult("Perímetro de " + identifier + ": " + perimeter);

            // Registrar el resultado en la tabla de símbolos
            addToSymbolTable(identifier + ".PERIMETRO", "Cálculo", String.valueOf(perimeter));
        } catch (Exception e) {
            logError("Error al procesar CALCULAR.PERIMETRO: " + e.getMessage());
        }
    }



    private void processTransformCommand(String line) {
        try {
            // Limpia la línea y divide el comando
            line = line.replace(";", "").trim();
            String[] parts = line.split("\\.");
            if (parts.length != 2) {
                logError("Error: Comando TRANSFORMAR mal estructurado.");
                return;
            }

            String identifier = parts[0].trim(); // El identificador de la figura
            String transformation = parts[1].split("\\(")[0].trim(); // La transformación (ESCALAR o ROTAR)
            String valueString = parts[1].split("\\(")[1].replace(")", "").trim();

            // Validar que el valor sea un número entero válido
            if (!valueString.matches("-?\\d+")) {
                logError("Error: Valor de transformación no válido: " + valueString);
                return;
            }
            int value = Integer.parseInt(valueString);

            // Verificar que la figura exista
            if (!predefinedShapes.containsKey(identifier)) {
                logError("Figura no encontrada: " + identifier);
                return;
            }

            // Obtener la figura y aplicar la transformación
            Shape shape = predefinedShapes.get(identifier);

            if (transformation.equalsIgnoreCase("ESCALAR")) {
                if (value <= 0) {
                    logError("Error: El valor para ESCALAR debe ser mayor que 0.");
                    return;
                }
                scaleShape(shape, value);
                logResult(identifier + " escalado por un factor de " + value);
            } else if (transformation.equalsIgnoreCase("ROTAR")) {
                // Validar que el valor de rotación esté entre 1 y 360 grados
                if (value < 1 || value > 360) {
                    logError("Error: El valor de rotación debe estar entre 1 y 360 grados.");
                    return; // No aplicar la rotación si el valor está fuera del rango
                }
                rotateShape(shape, value);
                logResult(identifier + " rotado " + value + " grados");
            } else {
                logError("Transformación no reconocida: " + transformation);
            }

        } catch (Exception e) {
            logError("Error al procesar TRANSFORMAR: " + e.getMessage());
        }
    }



    private void rotateShape(Shape shape, int angle) {
        // Incrementar el ángulo actual de rotación
        shape.setRotate(shape.getRotate() + angle);
        logResult("Figura rotada " + angle + " grados");
    }


    private void scaleShape(Shape shape, int scaleFactor) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            rectangle.setWidth(rectangle.getWidth() * scaleFactor);
            rectangle.setHeight(rectangle.getHeight() * scaleFactor);
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            circle.setRadius(circle.getRadius() * scaleFactor);
        } else if (shape instanceof Polygon) { // Triángulo equilátero
            Polygon triangle = (Polygon) shape;
            double scale = (double) scaleFactor;
            for (int i = 0; i < triangle.getPoints().size(); i++) {
                triangle.getPoints().set(i, triangle.getPoints().get(i) * scale);
            }
        } else {
            logError("ESCALAR no soportado para esta figura.");
        }
    }


    private void processModifyPositionCommand(String line) {
        try {
            // Eliminar el punto y coma y extraer la posición
            String[] parts = line.split("\\.");
            String identifier = parts[0].trim();
            String[] positionParts = parts[1].replace("POSICION(", "").replace(")", "").split(",");

            if (positionParts.length != 2) {
                logError("Error: Comando de posición mal estructurado.");
                return;
            }

            // Limpiar posibles caracteres extraños como ';' y convertir a números
            double newX = Double.parseDouble(positionParts[0].trim().replace(";", ""));
            double newY = Double.parseDouble(positionParts[1].trim().replace(";", ""));

            if (!isValidCoordinate(newX) || !isValidCoordinate(newY)) {
                logError("Error: Coordenadas fuera de rango (-100 a 100).");
                return;
            }

            // Verificar que la figura exista
            if (predefinedShapes.containsKey(identifier)) {
                Shape shape = predefinedShapes.get(identifier);
                shape.setTranslateX(newX);
                shape.setTranslateY(newY);
                logResult("Posición de la figura " + identifier + " modificada a (" + newX + ", " + newY + ")");
            } else {
                logError("Figura no encontrada: " + identifier);
            }

        } catch (Exception e) {
            logError("Error al procesar COMANDO .POSICION: " + e.getMessage());
        }
    }

    private void processModifyDimensionCommand(String line) {
        try {
            // Eliminar el punto y coma y extraer las dimensiones
            String[] parts = line.split("\\.");
            String identifier = parts[0].trim();
            String[] dimensionParts = parts[1].replace("DIMENSION(", "").replace(")", "").split(",");

            if (dimensionParts.length != 2) {
                logError("Error: Comando de dimensiones mal estructurado.");
                return;
            }

            // Limpiar posibles caracteres extraños como ';' y convertir a números
            double newWidth = Double.parseDouble(dimensionParts[0].trim().replace(";", ""));
            double newHeight = Double.parseDouble(dimensionParts[1].trim().replace(";", ""));

            // Verificar que la figura exista
            if (predefinedShapes.containsKey(identifier)) {
                Shape shape = predefinedShapes.get(identifier);

                // Modificar las dimensiones según el tipo de figura
                if (shape instanceof Rectangle) {
                    Rectangle rectangle = (Rectangle) shape;
                    rectangle.setWidth(newWidth);
                    rectangle.setHeight(newHeight);
                    logResult("Dimensiones de la figura " + identifier + " modificadas a (" + newWidth + ", " + newHeight + ")");
                } else if (shape instanceof Circle) {
                    Circle circle = (Circle) shape;
                    circle.setRadius(newWidth); // Suponiendo que solo tiene un tamaño para círculos
                    logResult("Dimensiones de la figura " + identifier + " modificadas a radio: " + newWidth);
                } else {
                    logError("Dimensiones no soportadas para esta figura.");
                }
            } else {
                logError("Figura no encontrada: " + identifier);
            }

        } catch (Exception e) {
            logError("Error al procesar COMANDO .DIMENSION: " + e.getMessage());
        }
    }




    private void modifyColor(Shape shape, String valueString) {
        try {
            // Limpiar el valor del color, en caso de que aún tenga caracteres no deseados
            valueString = valueString.replace(";", "").trim();

            int colorCode = Integer.parseInt(valueString);
            Color newColor = getColorByCode(colorCode);
            String colorName = getColorNameByCode(colorCode);

            if (newColor == null) {
                logError("Error: Código de color no válido: " + colorCode);
                return;
            }

            shape.setFill(newColor);
            logResult("Color de la figura modificado a " + colorName);
        } catch (NumberFormatException e) {
            logError("Error: Valor de color no válido: " + valueString);
        }
    }



    // Método para validar coordenadas
    private boolean isValidCoordinate(double value) {
        return value >= -100 && value <= 100;
    }


    // Método para crear la figura con el nombre validado en mayúsculas
    private Shape createShape(String shapeType, int size1, int size2, Color color) {
        // Convertir el tipo de figura a mayúsculas antes de procesarlo
        shapeType = shapeType.toUpperCase();

        switch (shapeType) {
            case "CUADRADO":
                return new Rectangle(size1, size1, color);
            case "RECTANGULO":
                return new Rectangle(size1, size2, color);
            case "CIRCULO":
                return new Circle(size1, color);
            case "TRIANGULO":
                // Crear triángulo equilátero
                Polygon triangle = new Polygon();
                double height = (Math.sqrt(3) / 2) * size1; // Altura del triángulo equilátero
                triangle.getPoints().addAll(
                        0.0, 0.0,                    // Vértice inferior izquierdo
                        size1 * 1.0, 0.0,            // Vértice inferior derecho
                        size1 / 2.0, -height         // Vértice superior
                );
                triangle.setFill(color);
                return triangle;
            default:
                logError("Figura desconocida: " + shapeType);
                return null;
        }
    }
    private String getColorNameByCode(int colorCode) {
        switch (colorCode) {
            case 1:
                return "Rojo";
            case 2:
                return "Azul";
            case 3:
                return "Amarillo";
            case 4:
                return "Verde";
            default:
                return "Color desconocido";
        }
    }

    private Color getColorByCode(int colorCode) {
        switch (colorCode) {
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            default:
                return null;
        }
    }


    private double calculateArea(Shape shape) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return rectangle.getWidth() * rectangle.getHeight();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * Math.pow(circle.getRadius(), 2);
        } else if (shape instanceof Polygon) { // Triángulo equilátero
            Polygon triangle = (Polygon) shape;
            double side = triangle.getLayoutBounds().getWidth(); // Lado del triángulo
            return (Math.sqrt(3) / 4) * Math.pow(side, 2); // Fórmula del área
        }
        return 0;
    }


    private double calculatePerimeter(Shape shape) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return 2 * (rectangle.getWidth() + rectangle.getHeight());
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return 2 * Math.PI * circle.getRadius();
        } else if (shape instanceof Polygon) { // Triángulo equilátero
            Polygon triangle = (Polygon) shape;
            double side = triangle.getLayoutBounds().getWidth(); // Lado del triángulo
            return 3 * side; // Fórmula del perímetro
        }
        return 0;
    }


    private void logError(String message) {
        Platform.runLater(() -> errorStackArea.appendText("ERROR: " + message + "\n"));
    }

    private void logResult(String message) {
        Platform.runLater(() -> errorStackArea.appendText("RESULTADO: " + message + "\n"));
    }

    private void addToSymbolTable(String symbolName, String type, String value) {
        int id;
        if (symbolIds.containsKey(symbolName)) {
            id = symbolIds.get(symbolName);
        } else {
            id = nextId++;
            symbolIds.put(symbolName, id);
        }

        Symbol symbol = new Symbol(id, symbolName, type, value);

        Platform.runLater(() -> {
            symbolList.removeIf(s -> s.getName().equals(symbolName)); // Evita duplicados
            symbolList.add(symbol); // Agrega el símbolo actualizado
            symbolTable.setItems(symbolList); // Vincula la lista actualizada a la tabla
        });
    }





    private String extractIdentifier(String line) {
        return line.split("\\(")[1].replace(")", "").trim();
    }

    private TableView<Symbol> createSymbolTable() {
        TableView<Symbol> table = new TableView<>();

        TableColumn<Symbol, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));

        TableColumn<Symbol, String> nameColumn = new TableColumn<>("Símbolo");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<Symbol, String> typeColumn = new TableColumn<>("Tipo");
        typeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType()));

        TableColumn<Symbol, String> valueColumn = new TableColumn<>("Valor");
        valueColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));

        table.getColumns().addAll(idColumn, nameColumn, typeColumn, valueColumn);

        table.setItems(symbolList); // Vincula la lista observable a la tabla

        return table;
    }

    private void tokenizeAndAddToSymbolTable(String line) {
        // Patrón para identificar tokens
        String regex = "\\w+|\\d+|\\.|=|\\(|\\)|,|;";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String token = matcher.group();

            String type = getTokenType(token); // Determina el tipo de token
            String value = token.matches("-?\\d+") ? token : ""; // Asigna valor solo si es un número

            // Agregar el token a la tabla de símbolos
            addToSymbolTable(token, type, value);
        }
    }

    private String getTokenType(String token) {
        if (token.equals("=")) return "OPERADOR_ASIGNACION";
        if (token.equals(".")) return "OPERADOR_PUNTO";
        if (token.equals("(")) return "PARENTESIS_IZQUIERDO";
        if (token.equals(")")) return "PARENTESIS_DERECHO";
        if (token.equals(",")) return "COMA";
        if (token.equals(";")) return "PUNTO_Y_COMA";
        if (token.matches("-?\\d+")) return "NUMERO";
        if (token.matches("\\w+")) {
            // Verificar si es palabra reservada
            if (token.equalsIgnoreCase("CREAR") || token.equalsIgnoreCase("CALCULAR") ||
                    token.equalsIgnoreCase("ESCALAR") || token.equalsIgnoreCase("ROTAR") ||
                    token.equalsIgnoreCase("AREA") || token.equalsIgnoreCase("PERIMETRO") ||
                    token.equalsIgnoreCase("COLOR") || token.equalsIgnoreCase("POSICION") ||
                    token.equalsIgnoreCase("DIMENSION")) {
                return "PALABRA_RESERVADA";
            } else {
                return "IDENTIFICADOR";
            }
        }
        return "DESCONOCIDO";
    }



    public static void main(String[] args) {
        launch(args);
    }
}
