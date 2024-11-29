package com.example.geometrix;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class UserManual extends Application {

    private Stage stage;
    private TextArea contentArea;
    private VBox root;  // El VBox se usa una sola vez
    private ListView<String> index;  // El ListView para el índice

    public UserManual() {
        stage = new Stage();
        stage.setTitle("Manual de Usuario");

        // Crear el contenido principal del manual
        root = new VBox(10);
        root.setPadding(new Insets(10));

        // Crear el índice interactivo (lista de secciones)
        index = new ListView<>();
        index.getItems().addAll(
                "1. Introducción",
                "2. Fundamentos del Lenguaje",
                "  2.1 Caracteres Permitidos",
                "  2.2 Palabras Reservadas",
                "  2.3 Identificadores y Parámetros",
                "3. Componentes del Intérprete",
                "  3.1 Análisis Léxico",
                "  3.2 Análisis Sintáctico",
                "  3.3 Análisis Semántico",
                "  3.4 Validaciones en el Plano Cartesiano",
                "4. Operaciones y Acciones Soportadas",
                "  4.1 Crear Figuras",
                "  4.2 Calcular Propiedades",
                "  4.3 Transformar Figuras",
                "  4.4 Modificar Parámetros",
                "  4.5 Eliminar Figura",
                "  About Us"
        );

        // Crear área para mostrar el contenido de cada sección
        contentArea = new TextArea();
        contentArea.setWrapText(true);
        contentArea.setEditable(false);
        contentArea.setPrefHeight(400);

        // Función para actualizar el contenido basado en la selección del índice
        index.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateContentArea(newValue);  // Actualiza el contenido según la selección
        });

        // Crear barra de búsqueda
        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Buscar...");
        Button searchButton = new Button("Buscar");
        searchBox.getChildren().addAll(searchField, searchButton);

        // Lógica de búsqueda (filtrar el índice)
        searchButton.setOnAction(e -> {
            String searchText = searchField.getText().toLowerCase();
            for (int i = 0; i < index.getItems().size(); i++) {
                if (index.getItems().get(i).toLowerCase().contains(searchText)) {
                    index.getSelectionModel().select(i);
                    break;
                }
            }
        });

        // Layout de la ventana principal
        HBox contentLayout = new HBox(10, index, contentArea);
        contentLayout.setHgrow(contentArea, Priority.ALWAYS);

        root.getChildren().addAll(searchBox, contentLayout);

        // Crear la escena solo una vez y asignar el VBox 'root' como raíz
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
    }

    // Método para mostrar el manual
    public void show() {
        stage.show();
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Llamar al método show() para mostrar el manual
        show();
    }

    // Actualiza el contenido del manual según la sección seleccionada
    private void updateContentArea(String section) {
        switch (section) {
            case "1. Introducción":
                contentArea.setText("GeoMatrix es un intérprete diseñado para permitir la creación, personalización, y manipulación de figuras geométricas básicas mediante el uso de un lenguaje formal definido. Este manual está diseñado para ayudar a los usuarios a comprender la estructura del lenguaje, las reglas de gramática y cómo interactuar con el intérprete.");
                break;
            case "2. Fundamentos del Lenguaje":
                contentArea.setText("El lenguaje GeoMatrix se utiliza para describir y manipular figuras geométricas en el plano cartesiano...");
                break;
            case "  2.1 Caracteres Permitidos":
                updateContentFor2_1();
                break;
            case "  2.2 Palabras Reservadas":
                updateContentFor2_2();
                break;
            case "  2.3 Identificadores y Parámetros":
                updateContentFor2_3();
                break;
            case "3. Componentes del Intérprete":
                contentArea.setText("El intérprete de GeoMatrix se compone de varias fases: análisis léxico, sintáctico y semántico...");
                break;
            case "  3.1 Análisis Léxico":
                updateContentFor3_1();
                break;
            case "  3.2 Análisis Sintáctico":
                updateContentFor3_2();
                break;
            case "  3.3 Análisis Semántico":
                updateContentFor3_3();
                break;
            case "  3.4 Validaciones en el Plano Cartesiano":
                updateContentFor3_4();
                break;
            case "4. Operaciones y Acciones Soportadas":
                contentArea.setText("GeoMatrix soporta varias operaciones y acciones para manipular figuras geométricas...");
                break;
            case "  4.1 Crear Figuras":
                updateContentFor4_1();
                break;
            case "  4.2 Calcular Propiedades":
                updateContentFor4_2();
                break;
            case "  4.3 Transformar Figuras":
                updateContentFor4_3();
                break;
            case "  4.4 Modificar Parámetros":
                updateContentFor4_4();
                break;
            case "  4.5 Eliminar Figura":
                updateContentFor4_5();
                break;
            case "  About Us":
                updateContentForAboutUs();
                break;
            default:
                contentArea.setText("Sección no encontrada.");
                break;
        }
    }

    private void updateContentFor2_1() {
        String content = "2.1 Caracteres Permitidos\n\n" +
                "El lenguaje de GeoMatrix permite el uso de los siguientes caracteres, organizados por categorías. A continuación, se explican las categorías y ejemplos de cada uno:\n\n" +

                "### 1. Letras\n" +
                "- **Ejemplos**: A, B, C, ..., Z\n" +
                "- **Descripción**: Las letras mayúsculas del alfabeto inglés son utilizadas en palabras reservadas e identificadores.\n\n" +

                "### 2. Números\n" +
                "- **Ejemplos**: 0, 1, 2, ..., 9\n" +
                "- **Descripción**: Los números enteros se utilizan en parámetros y valores de atributos. **Nota**: No se permiten números decimales ni valores negativos en las dimensiones.\n\n" +

                "### 3. Operadores\n" +
                "- **Ejemplos**: =, ., ;, (, ), ,\n" +
                "- **Descripción**: Son símbolos necesarios para definir expresiones, delimitar instrucciones y separar parámetros. A continuación se describen algunos de ellos:\n" +
                "  - **=**: Asignación de un valor a un identificador.\n" +
                "  - **.**: Conector entre palabras reservadas y atributos de las figuras.\n" +
                "  - **;**: Finalización de una instrucción.\n" +
                "  - **( )**: Delimitadores de parámetros en las instrucciones.\n" +
                "  - **,**: Separador de valores dentro de una lista.\n\n" +

                "### 4. Otros Símbolos\n" +
                "- **Ejemplos**: No permitidos\n" +
                "- **Descripción**: Algunos símbolos adicionales, como los caracteres **@**, **#**, **%**, **&**, no están permitidos en el lenguaje GeoMatrix.\n\n" +

                "### Ejemplos de Uso Correcto\n" +
                "1. **Código 1**:\n" +
                "   ```java\n" +
                "   CUAD1 = CREAR.CUADRADO(10, 1, 0, 0);\n" +
                "   ```\n" +
                "   - **Nota**: El parámetro `10` representa la longitud de los lados del cuadrado (todos iguales). Los demás parámetros corresponden al color (1) y la posición inicial `(0, 0)` en el plano cartesiano.\n\n" +

                "2. **Código 2**:\n" +
                "   ```java\n" +
                "   RECT1 = CREAR.RECTANGULO(10, 20, 1, 0, 0);\n" +
                "   ```\n" +
                "   - **Nota**: En el caso del rectángulo, el primer valor (`10`) representa el ancho, y el segundo (`20`) el alto. Los demás parámetros corresponden al color (1) y la posición inicial `(0, 0)` en el plano cartesiano.\n\n" +

                "### Detalles por Categoría\n" +
                "1. **Identificadores**:\n" +
                "- Deben comenzar con una **letra mayúscula** (A-Z).\n" +
                "- Pueden incluir **números** (0-9).\n" +
                "- **No se permiten** caracteres especiales, espacios ni letras minúsculas.\n" +
                "  **Ejemplos válidos**: `CUAD1`, `RECT2`.\n" +
                "  **Ejemplos inválidos**: `1FIGURA`, `figura1`, `CUAD_1`.\n\n" +

                "2. **Letras**:\n" +
                "- Utilizadas en palabras reservadas (e.g., `CREAR`, `CUADRADO`, `RECTANGULO`).\n\n" +

                "3. **Números**:\n" +
                "- Utilizados en parámetros y valores de atributos:\n" +
                "  - Deben ser números enteros.\n" +
                "  - No se permiten decimales, negativos (en dimensiones), ni valores fuera de rango.\n" +
                "  **Ejemplos válidos**: `10`, `20`.\n" +
                "  **Ejemplo inválido**: `10.5`, `-5`.\n\n" +

                "4. **Operadores**:\n" +
                "- Utilizados para delimitar instrucciones:\n" +
                "  - **=**: Asignación.\n" +
                "  - **.**: Conector entre palabras reservadas y atributos.\n" +
                "  - **;**: Finalización de una instrucción.\n" +
                "  - **( y )**: Delimitan parámetros.\n" +
                "  - **,**: Separador de valores en una lista.\n\n" +

                "### Notas Importantes\n" +
                "1. Los caracteres no listados, como tildes (á, é), caracteres especiales (`@`, `#`, etc.), o letras minúsculas, no son válidos en el lenguaje GeoMatrix.\n" +
                "2. **RECTÁNGULO**: Es un caso especial donde se especifican dos dimensiones (ancho y alto). Estas dimensiones pueden ser modificadas juntas o por separado usando el marcador `_`.\n";

        // Actualiza el TextArea con el contenido de la sección 2.1
        contentArea.setText(content);
    }


    private void updateContentFor2_2() {
        String content = "2.2 Palabras Reservadas\n\n" +
                "Las palabras reservadas en GeoMatrix tienen un significado específico y no pueden ser utilizadas como identificadores. Estas palabras están organizadas por categoría, como se detalla a continuación:\n\n" +

                "### 1. Acción\n" +
                "- **CREAR**: Inicia la creación de una figura geométrica.\n" +
                "- **CALCULAR**: Realiza un cálculo sobre una figura, como `AREA` o `PERIMETRO`.\n\n" +

                "### 2. Transformación\n" +
                "- **ESCALAR**: Aplica un escalado proporcional a una figura geométrica.\n" +
                "- **ROTAR**: Rota una figura geométrica un número específico de grados.\n\n" +

                "### 3. Cálculo\n" +
                "- **AREA**: Solicita el cálculo del área de una figura geométrica.\n" +
                "- **PERIMETRO**: Solicita el cálculo del perímetro de una figura geométrica.\n\n" +

                "### 4. Atributo\n" +
                "- **COLOR**: Cambia el color de una figura geométrica.\n" +
                "- **POSICION**: Cambia la posición de la figura en el plano cartesiano. Puede modificar **X**, **Y** o ambas.\n" +
                "- **DIMENSION**: Cambia las dimensiones de una figura geométrica, como el ancho, alto, o radio.\n" +
                "- **ELIMINAR**: Elimina completamente la figura del plano.\n\n" +

                "### Colores\n" +
                "Los colores en GeoMatrix son representados mediante valores numéricos enteros, del **1 al 4**, que corresponden a los colores primarios. Estos valores son utilizados como parámetros para definir o modificar el color de una figura geométrica.\n\n" +

                "#### Tabla de Colores:\n" +
                "- **Valor Numérico**: 1 | **Color Representado**: Rojo\n" +
                "- **Valor Numérico**: 2 | **Color Representado**: Azul\n" +
                "- **Valor Numérico**: 3 | **Color Representado**: Amarillo\n" +
                "- **Valor Numérico**: 4 | **Color Representado**: Verde\n";

        // Actualiza el TextArea con el contenido de la sección 2.2
        contentArea.setText(content);
    }


    private void updateContentFor2_3() {
        String content = "2.3 Identificadores y Parámetros\n\n" +
                "### Identificadores\n" +
                "Los identificadores en GeoMatrix son nombres utilizados para identificar figuras creadas. Deben cumplir las siguientes reglas:\n\n" +

                "1. **Iniciar con una letra mayúscula (A-Z):**\n" +
                "   - Ejemplo válido: **CUAD1**.\n" +
                "   - Ejemplo inválido: **1CUAD** (comienza con un número).\n\n" +

                "2. **Solo pueden contener letras mayúsculas (A-Z) y números enteros (0-9):**\n" +
                "   - Ejemplo válido: **RECT123**.\n" +
                "   - Ejemplo inválido: **rect123** (contiene letras minúsculas) o **RECT#123** (incluye un carácter especial).\n\n" +

                "3. **Longitud máxima de 15 caracteres:**\n" +
                "   - Ejemplo válido: **CIRCULO12345678**.\n" +
                "   - Ejemplo inválido: **IDENTIFICADORDEMASIADOLARGO** (excede los 15 caracteres).\n\n" +

                "4. **No se permiten caracteres especiales ni espacios:**\n" +
                "   - Ejemplo inválido: **CUADRADO_1** (contiene un guion bajo) o **CUAD RADO** (contiene un espacio).\n\n" +

                "5. **No pueden coincidir con palabras reservadas:**\n" +
                "   - Ejemplo inválido: **CREAR**, **AREA**, **RECTANGULO**.\n\n" +

                "#### Ejemplos de identificadores válidos:\n" +
                "- **FIGURA1**\n" +
                "- **CUADRADOX**\n" +
                "- **TRIANGULO3**\n\n" +

                "#### Ejemplos de identificadores inválidos:\n" +
                "- **figura1** (contiene minúsculas).\n" +
                "- **CIRCULO#** (incluye un carácter especial).\n" +
                "- **1RECTANGULO** (inicia con un número).\n\n" +

                "### Parámetros\n" +
                "Los parámetros definen las características de las figuras geométricas y deben cumplir las siguientes reglas dependiendo de su tipo:\n\n" +

                "#### 1. Dimensiones:\n" +
                "- Deben ser números enteros positivos (naturales).\n" +
                "- No se aceptan valores decimales, negativos ni caracteres no numéricos.\n" +
                "- Ejemplos válidos:\n" +
                "  - **CUADRADO:** (10) - Representa la longitud de los lados (todos iguales).\n" +
                "  - **RECTÁNGULO:** (10, 20) - El primer número es el ancho, el segundo es el alto.\n" +
                "  - **CÍRCULO:** (4) - Representa el radio.\n" +
                "  - **TRIÁNGULO:** (8) - Representa el lado del triángulo equilátero.\n" +
                "- Ejemplos inválidos:\n" +
                "  - (10.5) (valor decimal).\n" +
                "  - (-10) (valor negativo).\n" +
                "  - (10, 20, 30) (cantidad incorrecta de valores para el tipo de figura).\n\n" +

                "#### 2. Color:\n" +
                "- Debe ser un número entero del 1 al 4, donde cada valor representa un color primario:\n" +
                "  - **1**: Rojo\n" +
                "  - **2**: Azul\n" +
                "  - **3**: Amarillo\n" +
                "  - **4**: Verde\n" +
                "- Ejemplos válidos:\n" +
                "  - **1** (rojo).\n" +
                "- Ejemplos inválidos:\n" +
                "  - **5** (fuera del rango permitido).\n" +
                "  - **a** (no permitido).\n\n" +

                "#### 3. Posición en el plano cartesiano:\n" +
                "- Se representan con dos números enteros que indican las coordenadas (X, Y).\n" +
                "- Los valores pueden ser positivos, negativos o cero.\n" +
                "- Ejemplos válidos:\n" +
                "  - (0, 0) (posición en el origen).\n" +
                "  - (-10, 20) (posición en el cuadrante superior izquierdo).\n" +
                "- Ejemplos inválidos:\n" +
                "  - (10.5, -20) (valor decimal).\n" +
                "  - (-10) (falta un valor para el eje Y).\n\n" +

                "### Formato general de los parámetros:\n" +
                "(<dimensiones>, <color>, <coordenadaX>, <coordenadaY>)\n\n" +

                "#### Ejemplos completos de parámetros válidos:\n" +
                "- **CUADRADO:** (10, 1, 0, 0) - Un cuadrado con lados de 10, color rojo, en la posición (0, 0).\n" +
                "- **RECTÁNGULO:** (10, 20, 2, -5, 5) - Un rectángulo con ancho 10, alto 20, color azul, en la posición (-5, 5).\n" +
                "- **CÍRCULO:** (4, 3, 0, 0) - Un círculo con radio 4, color amarillo, en el origen (0, 0).\n" +
                "- **TRIÁNGULO:** (8, 4, 10, -10) - Un triángulo equilátero con lados de 8, color verde, en la posición (10, -10).\n\n" +

                "#### Ejemplos de parámetros inválidos:\n" +
                "- **CUADRADO:** (10, 1, -10) (falta un valor para el eje Y).\n" +
                "- **RECTÁNGULO:** (10.5, 20, 1, 0, 0) (dimensión inválida, contiene decimales).\n" +
                "- **CÍRCULO:** (-4, 3, 0, 0) (radio inválido, no se permiten negativos).\n" +
                "- **TRIÁNGULO:** (10, 1) (exceso de parámetros para un triángulo equilátero).";

        // Actualiza el TextArea con el contenido de la sección 2.3
        contentArea.setText(content);
    }


    private void updateContentFor3_1() {
        String content = "3.1 Análisis Léxico\n\n" +
                "El análisis léxico convierte el código fuente en una secuencia de tokens, clasificándolos según su tipo para facilitar el análisis sintáctico y semántico. Cada token corresponde a un elemento del lenguaje definido en GeoMatrix.\n\n" +

                "### Ejemplo de Análisis Léxico\n" +
                "El siguiente código:\n" +
                "```GEOMATRIX\n" +
                "RECT1 = CREAR.RECTANGULO(10, 20, 1, 0, 0);\n" +
                "```\n" +
                "Genera los siguientes tokens:\n\n" +

                "1. **IDENTIFICADOR (RECT1):** Nombre asignado a la figura creada.\n" +
                "2. **OPERADOR_ASIGNACION (=):** Delimita la asignación de valores.\n" +
                "3. **PALABRA_RESERVADA (CREAR):** Acción para crear una figura geométrica.\n" +
                "4. **OPERADOR_PUNTO (.):** Conecta la acción con el tipo de figura.\n" +
                "5. **PALABRA_RESERVADA (RECTANGULO):** Tipo de figura geométrica a crear.\n" +
                "6. **PARENTESIS_IZQUIERDO (():** Delimita los parámetros de la figura.\n" +
                "7. **NUMERO (10):** Dimensión que representa el ancho del rectángulo.\n" +
                "8. **COMA (,):** Separador entre parámetros.\n" +
                "9. **NUMERO (20):** Dimensión que representa el alto del rectángulo.\n" +
                "10. **COMA (,):** Separador entre parámetros.\n" +
                "11. **NUMERO (1):** Color asignado a la figura (rojo).\n" +
                "12. **COMA (,):** Separador entre parámetros.\n" +
                "13. **NUMERO (0):** Coordenada X inicial.\n" +
                "14. **COMA (,):** Separador entre parámetros.\n" +
                "15. **NUMERO (0):** Coordenada Y inicial.\n" +
                "16. **PARENTESIS_DERECHO ())** Delimita el fin de los parámetros.\n" +
                "17. **PUNTO_Y_COMA (;):** Finaliza la instrucción.\n\n" +

                "### Propósito del Análisis Léxico\n" +
                "El análisis léxico tiene como objetivo descomponer el código en componentes más simples (tokens) para que el análisis sintáctico posterior pueda reconocer correctamente la estructura del código. " +
                "Al dividir el código en tokens, se facilita la identificación de los elementos del lenguaje como identificadores, operadores, palabras reservadas, números, etc.\n\n" +

                "En GeoMatrix, los tokens pueden clasificarse en varias categorías, como identificadores, operadores, palabras reservadas, números, etc. El análisis léxico es esencial para asegurar que el código fuente " +
                "se ajusta a las reglas del lenguaje y que los elementos del lenguaje son correctamente identificados y procesados por el intérprete.\n";

        // Actualiza el TextArea con el contenido de la sección 3.1
        contentArea.setText(content);
    }

    private void updateContentFor3_2() {
        String content = "3.2 Análisis Sintáctico\n\n" +
                "El análisis sintáctico verifica que la secuencia de tokens generada cumpla con las reglas de la gramática del lenguaje. " +
                "Esto asegura que la estructura de las instrucciones sea válida antes de proceder con su ejecución.\n\n" +

                "### Regla Sintáctica\n" +
                "La siguiente es la regla sintáctica para una instrucción válida en GeoMatrix:\n\n" +
                "<instrucción> ::= <identificador> \"=\" <acción> \".\" <figura> \"(\" <dimensiones>, <color>, <coordenadaX>, <coordenadaY> \")\" \";\"\n\n" +

                "### Requisitos\n" +
                "1. **La instrucción debe iniciar con un identificador válido** seguido de un operador de asignación (**=**).\n" +
                "2. **La palabra reservada CREAR debe estar presente**, seguida por el operador **.** y el tipo de figura geométrica (por ejemplo, **CUADRADO**, **RECTANGULO**, etc.).\n" +
                "3. **Los parámetros deben estar delimitados por paréntesis** y ordenados como sigue:\n" +
                "   - **Dimensiones:** Representadas por uno o más números enteros (según el tipo de figura).\n" +
                "   - **Color:** Representado por un valor numérico (1 a 4).\n" +
                "   - **Coordenadas iniciales:** Dos números enteros que representen las posiciones **X** e **Y** en el plano cartesiano.\n" +
                "4. **La instrucción debe terminar con un punto y coma (;)**.\n\n" +

                "### Ejemplo de Código Válido\n" +
                "El siguiente código cumple con la regla sintáctica:\n\n" +
                "```GEOMATRIX\n" +
                "RECT1 = CREAR.RECTANGULO(10, 20, 2, 0, 0);\n" +
                "```\n\n" +

                "### Análisis Paso a Paso\n" +
                "1. **Tokens generados** (Análisis Léxico):\n" +
                "   - **IDENTIFICADOR (RECT1):** Nombre de la figura creada.\n" +
                "   - **OPERADOR_ASIGNACION (=):** Delimita la asignación de valores.\n" +
                "   - **PALABRA_RESERVADA (CREAR):** Acción para crear una figura geométrica.\n" +
                "   - **OPERADOR_PUNTO (.):** Conecta la acción con el tipo de figura.\n" +
                "   - **PALABRA_RESERVADA (RECTANGULO):** Tipo de figura geométrica a crear.\n" +
                "   - **PARENTESIS_IZQUIERDO (():** Delimita los parámetros.\n" +
                "   - **NUMERO (10):** Ancho del rectángulo.\n" +
                "   - **COMA (,):** Separador entre parámetros.\n" +
                "   - **NUMERO (20):** Alto del rectángulo.\n" +
                "   - **COMA (,):** Separador entre parámetros.\n" +
                "   - **NUMERO (2):** Color azul.\n" +
                "   - **COMA (,):** Separador entre parámetros.\n" +
                "   - **NUMERO (0):** Coordenada X.\n" +
                "   - **COMA (,):** Separador entre parámetros.\n" +
                "   - **NUMERO (0):** Coordenada Y.\n" +
                "   - **PARENTESIS_DERECHO ()):** Fin de los parámetros.\n" +
                "   - **PUNTO_Y_COMA (;):** Finaliza la instrucción.\n\n" +

                "2. **Validación Sintáctica:**\n" +
                "   - El identificador **RECT1** es válido.\n" +
                "   - La palabra reservada **CREAR** y el operador **.** están en el lugar correcto.\n" +
                "   - El tipo de figura **RECTANGULO** es válido.\n" +
                "   - Los parámetros cumplen las reglas definidas.\n\n" +

                "### Importancia del Análisis Sintáctico\n" +
                "El análisis sintáctico es crucial para garantizar que el código esté estructurado correctamente antes de su ejecución. " +
                "Si el código no sigue las reglas de la gramática, el intérprete no podrá ejecutarlo correctamente y se generarán errores. " +
                "Este análisis asegura que los elementos del código se encuentren en el orden adecuado y que se respeten las relaciones entre ellos.\n";

        // Actualiza el TextArea con el contenido de la sección 3.2
        contentArea.setText(content);
    }

    private void updateContentFor3_3() {
        String content = "3.3 Análisis Semántico\n\n" +
                "El análisis semántico asegura que las operaciones definidas en el código sean lógicas y válidas dentro del contexto del programa. " +
                "Valida que los elementos utilizados sean compatibles con la operación solicitada.\n\n" +

                "### Validaciones Comunes:\n\n" +
                "1. **Existencia de Identificadores:** Verifica que todos los identificadores referenciados hayan sido definidos previamente.\n" +
                "   - **Ejemplo inválido:**\n" +
                "     `CALCULAR.AREA(CIRC1);`\n" +
                "     **Error:** `CIRC1` no está definido.\n\n" +

                "2. **Compatibilidad de Operaciones:** Asegura que las operaciones sean válidas para el tipo de figura.\n" +
                "   - **Ejemplo inválido:**\n" +
                "     `CALCULAR.PERIMETRO(CIRC1);`\n" +
                "     **Error:** `CIRC1` debe ser definido previamente.\n\n" +

                "3. **Restricciones de Parámetros:**\n" +
                "   Valida las propiedades de los parámetros:\n" +
                "   - **Dimensiones coherentes con el tipo de figura.**\n" +
                "   - **Color dentro del rango válido (1-4).**\n" +
                "   - **Coordenadas dentro de los límites del plano cartesiano (-100 a 100).**\n\n" +

                "### Ejemplo de Código Válido\n" +
                "```GEOMATRIX\n" +
                "CUAD1 = CREAR.CUADRADO(10, 3, -25, 30);\n" +
                "CALCULAR.AREA(CUAD1);\n" +
                "```\n" +

                "En este caso, el identificador `CUAD1` está correctamente definido y las coordenadas están dentro del rango permitido, por lo que el código es válido.\n\n" +

                "Este análisis semántico se asegura de que no se realicen operaciones o se usen identificadores que no han sido previamente definidos, y valida la lógica de las operaciones según las figuras geométricas y sus parámetros.\n";

        // Actualiza el TextArea con el contenido de la sección 3.3
        contentArea.setText(content);
    }

    private void updateContentFor3_4() {
        String content = "3.4 Validaciones en el Plano Cartesiano\n\n" +
                "El intérprete valida automáticamente las posiciones y dimensiones de las figuras para asegurar que no excedan los límites del plano cartesiano.\n\n" +

                "### 1. Validación de Posición\n" +
                "Las coordenadas X o Y deben estar dentro del rango permitido: **[-100, 100]**.\n" +
                "   - **Ejemplo inválido:**\n" +
                "     ```GEOMATRIX\n" +
                "     CIRC1 = CREAR.CIRCULO(15, 1, 110, 0);\n" +
                "     ```\n" +
                "     **Error:** La coordenada `X=110` excede el rango permitido.\n\n" +

                "### 2. Validación de Tamaño\n" +
                "Las dimensiones deben ser tales que los bordes de la figura no salgan del rango permitido en el plano cartesiano.\n" +
                "   - **Ejemplo inválido:**\n" +
                "     ```GEOMATRIX\n" +
                "     RECT1 = CREAR.RECTANGULO(120, 50, 2, 0, 0);\n" +
                "     ```\n" +
                "     **Error:** La dimensión de `120` hace que el rectángulo exceda el límite del plano.\n\n" +

                "Estas validaciones aseguran que las figuras se mantengan dentro de los límites del plano cartesiano y no causen errores en la ejecución del programa. " +
                "El intérprete realiza estos chequeos de manera automática y proporciona mensajes de error claros si alguna figura excede los límites definidos.\n";

        // Actualiza el TextArea con el contenido de la sección 3.4
        contentArea.setText(content);
    }

    private void updateContentFor4_1() {
        String content = "4.1 Crear Figuras\n\n" +
                "El comando **CREAR** permite definir una nueva figura geométrica con características iniciales específicas. " +
                "Los parámetros varían según el tipo de figura y deben respetar reglas estrictas para garantizar su correcta interpretación.\n\n" +

                "### Estructura del Comando\n\n" +
                "<identificador> = CREAR.<figura>(<dimensiones>, <color>, <coordenadaX>, <coordenadaY>);\n\n" +

                "### Parámteros\n\n" +
                "Los parámetros son valores necesarios para definir las propiedades iniciales de la figura. Cada parámetro tiene reglas específicas según el tipo de figura:\n\n" +

                "**1. Dimensiones**\n" +
                "Las dimensiones varían dependiendo de la figura geométrica:\n" +

                "**CUADRADO:**\n" +
                " - Representa la longitud de los lados (todos iguales).\n" +
                " - Debe ser un número entero positivo mayor a 0.\n" +
                " - No se permiten valores negativos, decimales, o cero.\n" +
                "   - Ejemplo válido: `10` (un cuadrado con lados de 10 unidades).\n" +
                "   - Ejemplo inválido: `-5` (valor negativo), `0` (no se permite un lado de longitud 0), `10.5` (no se permiten decimales).\n\n" +

                "**RECTÁNGULO:**\n" +
                " - El primer número representa el ancho, y el segundo el alto.\n" +
                " - Ambos valores deben ser números enteros positivos mayores a 0.\n" +
                " - No se permiten valores negativos, decimales, o cero.\n" +
                "   - Ejemplo válido: `(15, 20)` (un rectángulo de ancho 15 y alto 20).\n" +
                "   - Ejemplo inválido: `(10, -20)` (el alto es negativo), `(0, 15)` (el ancho es 0), `(15.5, 20)` (no se permiten decimales).\n\n" +

                "**CÍRCULO:**\n" +
                " - Representa el radio del círculo.\n" +
                " - Debe ser un número entero positivo mayor a 0.\n" +
                " - No se permiten valores negativos, decimales, o cero.\n" +
                "   - Ejemplo válido: `7` (un círculo con radio de 7 unidades).\n" +
                "   - Ejemplo inválido: `-7` (valor negativo), `0` (radio no válido), `7.8` (no se permiten decimales).\n\n" +

                "**TRIÁNGULO:**\n" +
                " - Representa el lado de un triángulo equilátero.\n" +
                " - Debe ser un número entero positivo mayor a 0.\n" +
                " - No se permiten valores negativos, decimales, o cero.\n" +
                "   - Ejemplo válido: `10` (un triángulo equilátero con lados de 10 unidades).\n" +
                "   - Ejemplo inválido: `-10` (valor negativo), `0` (lado no válido), `10.5` (no se permiten decimales).\n\n" +

                "**2. Color**\n" +
                "Representado por un número entero del 1 al 4, donde:\n" +
                " - **1:** Rojo\n" +
                " - **2:** Azul\n" +
                " - **3:** Amarillo\n" +
                " - **4:** Verde\n" +
                "   - Ejemplo válido: `3` (amarillo).\n" +
                "   - Ejemplo inválido: `5` (fuera del rango permitido), `0` (no válido), `2.5` (no se permiten decimales).\n\n" +

                "**3. Coordenadas Iniciales**\n" +
                "Representan la posición inicial de la figura en el plano cartesiano:\n" +
                " - **coordenadaX:** Posición en el eje horizontal.\n" +
                " - **coordenadaY:** Posición en el eje vertical.\n" +
                "   - Ambas deben ser números enteros.\n" +
                "   - Los valores deben estar dentro del rango [-100, 100].\n" +
                "   - No se permiten decimales.\n" +
                "     - Ejemplo válido: `(0, 0)` (posición en el origen del plano).\n" +
                "     - Ejemplo inválido: `(150, 0)` (coordenada X fuera del rango), `(0, -101)` (coordenada Y fuera del rango), `(10.5, 20)` (no se permiten decimales).\n\n" +

                "### Ejemplos por Figura\n\n" +
                "**1. CUADRADO:**\n" +
                "`CUAD1 = CREAR.CUADRADO(10, 1, 0, 0);`\n" +
                "   - **Dimensión:** 10 (lado).\n" +
                "   - **Color:** Rojo (1).\n" +
                "   - **Coordenadas:** (0, 0).\n\n" +

                "**2. RECTÁNGULO:**\n" +
                "`RECT1 = CREAR.RECTANGULO(15, 20, 2, -10, 10);`\n" +
                "   - **Dimensiones:** 15 (ancho) y 20 (alto).\n" +
                "   - **Color:** Azul (2).\n" +
                "   - **Coordenadas:** (-10, 10).\n\n" +

                "**3. CÍRCULO:**\n" +
                "`CIRC1 = CREAR.CIRCULO(7, 3, 20, 20);`\n" +
                "   - **Dimensión:** 7 (radio).\n" +
                "   - **Color:** Amarillo (3).\n" +
                "   - **Coordenadas:** (20, 20).\n\n" +

                "**4. TRIÁNGULO:**\n" +
                "`TRIAN1 = CREAR.TRIANGULO(10, 4, 5, -5);`\n" +
                "   - **Dimensión:** 10 (lado).\n" +
                "   - **Color:** Verde (4).\n" +
                "   - **Coordenadas:** (5, -5).\n\n" +

                "### Errores Comunes\n\n" +
                "**1. Dimensiones inválidas:**\n" +
                "`CREAR.CUADRADO(-10, 1, 0, 0);`\n" +
                "   - **Error:** Las dimensiones no pueden ser negativas.\n\n" +

                "**2. Color fuera de rango:**\n" +
                "`CREAR.CIRCULO(7, 5, 0, 0);`\n" +
                "   - **Error:** El color 5 no es válido.\n\n" +

                "**3. Coordenadas fuera del rango permitido:**\n" +
                "`CREAR.TRIANGULO(10, 3, 120, 0);`\n" +
                "   - **Error:** La coordenada X excede el límite permitido [-100, 100].\n\n" +

                "**4. Faltan parámetros:**\n" +
                "`CREAR.RECTANGULO(15, 2);`\n" +
                "   - **Error:** Faltan el alto y las coordenadas.\n";

        // Actualiza el TextArea con el contenido de la sección 4.1
        contentArea.setText(content);
    }

    private void updateContentFor4_2() {
        String content = "4.2 Calcular Propiedades\n\n" +
                "El comando **CALCULAR** permite obtener información sobre las propiedades de una figura previamente creada. " +
                "Este comando evalúa propiedades como el área y el perímetro según el tipo de figura geométrica.\n\n" +

                "### Estructura del Comando\n\n" +
                "`CALCULAR.<operación> <identificador>`\n\n" +

                "### Elementos del Comando\n\n" +
                "**1. Operación**\n" +
                "Define la propiedad que se desea calcular. Las opciones válidas son:\n" +
                " - **AREA:** Calcula el área de la figura.\n" +
                " - **PERIMETRO:** Calcula el perímetro de la figura.\n" +
                "   - Ejemplo válido: `CALCULAR.AREA(CUAD1);`\n" +
                "   - Ejemplo inválido: `CALCULAR.VOLUMEN(CUAD1);` (Error: **VOLUMEN** no es una operación válida).\n\n" +

                "**2. Identificador**\n" +
                "Es el nombre asignado a la figura en el momento de su creación. Debe ser un identificador válido y la figura debe haber sido creada previamente.\n" +
                "   - Ejemplo válido: `RECT1`\n" +
                "   - Ejemplo inválido: `FIGURA_INEXISTENTE` (Error: **La figura no fue creada previamente**).\n\n" +

                "### Cálculo de Propiedades por Figura\n\n" +
                "**1. CUADRADO:**\n" +
                "   - **Área:** Calculada como `lado^2`.\n" +
                "   - **Perímetro:** Calculado como `4 * lado`.\n" +
                "   - Ejemplo:\n" +
                "     - `CALCULAR.AREA(CUAD1);`\n" +
                "     - `CALCULAR.PERIMETRO(CUAD1);`\n\n" +

                "**2. RECTÁNGULO:**\n" +
                "   - **Área:** Calculada como `ancho * alto`.\n" +
                "   - **Perímetro:** Calculado como `2 * (ancho + alto)`.\n" +
                "   - Ejemplo:\n" +
                "     - `CALCULAR.AREA(RECT1);`\n" +
                "     - `CALCULAR.PERIMETRO(RECT1);`\n\n" +

                "**3. CÍRCULO:**\n" +
                "   - **Área:** Calculada como `π * radio^2`.\n" +
                "   - **Perímetro:** Calculado como `2 * π * radio`.\n" +
                "   - Ejemplo:\n" +
                "     - `CALCULAR.AREA(CIRC1);`\n" +
                "     - `CALCULAR.PERIMETRO(CIRC1);`\n\n" +

                "**4. TRIÁNGULO (equilátero):**\n" +
                "   - **Área:** Calculada como `(√3 / 4) * lado^2`.\n" +
                "   - **Perímetro:** Calculado como `3 * lado`.\n" +
                "   - Ejemplo:\n" +
                "     - `CALCULAR.AREA(TRIAN1);`\n" +
                "     - `CALCULAR.PERIMETRO(TRIAN1);`\n\n" +

                "### Ejemplos de Uso\n\n" +
                "1. **Calcular el área de un cuadrado llamado CUAD1:**\n" +
                "`CALCULAR.AREA(CUAD1);`\n" +
                "   - **Resultado:** El área del cuadrado será `lado^2`.\n\n" +

                "2. **Calcular el perímetro de un rectángulo llamado RECT1:**\n" +
                "`CALCULAR.PERIMETRO(RECT1);`\n" +
                "   - **Resultado:** El perímetro será `2 * (ancho + alto)`.\n\n" +

                "3. **Calcular el área de un círculo llamado CIRC1:**\n" +
                "`CALCULAR.AREA(CIRC1);`\n" +
                "   - **Resultado:** El área será `π * radio^2`.\n\n" +

                "4. **Calcular el perímetro de un triángulo llamado TRIAN1:**\n" +
                "`CALCULAR.PERIMETRO(TRIAN1);`\n" +
                "   - **Resultado:** El perímetro será `3 * lado`.\n\n" +

                "### Errores Comunes\n\n" +
                "**1. Figura no encontrada:**\n" +
                "`CALCULAR.AREA(FIGURA_INEXISTENTE);`\n" +
                "   - **Error:** **La figura FIGURA_INEXISTENTE no fue creada previamente.**\n\n" +

                "**2. Operación inválida:**\n" +
                "`CALCULAR.VOLUMEN(CIRC1);`\n" +
                "   - **Error:** **VOLUMEN** no es una operación válida en GeoMatrix.\n\n" +

                "**3. Identificador no válido:**\n" +
                "`CALCULAR.AREA(CUAD1@);`\n" +
                "   - **Error:** **El identificador CUAD1@ contiene caracteres no permitidos.**\n\n" +

                "**4. Faltan paréntesis o punto:**\n" +
                "`CALCULAR.AREA.CUAD1;`\n" +
                "   - **Error:** **Falta el formato correcto con paréntesis ( ).**\n\n" +

                "### Validaciones Importantes\n\n" +
                " - El cálculo debe realizarse solo sobre figuras previamente creadas.\n" +
                " - La operación debe ser compatible con el tipo de figura. No se puede calcular AREA en un identificador que no represente una figura geométrica.\n" +
                " - Las fórmulas de cálculo están predefinidas y no pueden ser modificadas por el usuario.\n";

        // Actualiza el TextArea con el contenido de la sección 4.2
        contentArea.setText(content);
    }

    private void updateContentFor4_3() {
        String content = "4.3 Transformar Figuras\n\n" +
                "El comando **TRANSFORMAR** permite modificar una figura geométrica existente aplicando transformaciones específicas que alteran su tamaño o su orientación en el plano cartesiano.\n\n" +

                "### Estructura del Comando\n\n" +
                "`<identificador>.<transformación>(<valor>);`\n\n" +

                "### Componentes del Comando\n\n" +
                "**1. TRANSFORMAR**\n" +
                "Es la palabra reservada que indica que se aplicará una transformación a una figura geométrica.\n\n" +

                "**2. <identificador>**\n" +
                "El nombre de la figura geométrica previamente creada.\n" +
                "   - **Reglas:**\n" +
                "     - Debe ser un identificador válido que cumpla con las reglas definidas (ver sección de identificadores).\n" +
                "     - La figura referenciada debe haber sido creada previamente mediante el comando **CREAR**.\n" +
                "     - Ejemplo válido: `RECT1`.\n" +
                "     - Ejemplo inválido: `FIGURA_NO_EXISTE` (Error: El identificador no está definido).\n\n" +

                "**3. <transformación>**\n" +
                "La operación específica que se aplicará a la figura.\n\n" +
                "#### Transformaciones permitidas:\n\n" +
                "**ESCALAR(<número>)**\n" +
                "Cambia el tamaño de la figura proporcionalmente.\n" +
                "   - **Reglas:**\n" +
                "     - El valor debe ser un número entero positivo mayor que 0.\n" +
                "     - No se permiten valores negativos, decimales, o cero.\n" +
                "   - **Por figura:**\n" +
                "     - **CUADRADO:** Multiplica la longitud de sus lados.\n" +
                "     - **RECTÁNGULO:** Multiplica el ancho y el alto.\n" +
                "     - **CÍRCULO:** Multiplica el radio.\n" +
                "     - **TRIÁNGULO:** Multiplica la longitud de sus lados.\n" +
                "   - **Ejemplo:**\n" +
                "     - `RECT1.ESCALAR(2);` (Si el rectángulo tenía dimensiones 10x20, ahora será 20x40).\n\n" +

                "**ROTAR(<número>)**\n" +
                "Gira la figura un número de grados respecto a su posición original en el plano cartesiano.\n" +
                "   - **Reglas:**\n" +
                "     - Los valores pueden ser positivos (rotación en sentido horario) o negativos (rotación en sentido antihorario).\n" +
                "     - No afecta las dimensiones ni el color de la figura.\n" +
                "   - **Por figura:**\n" +
                "     - **CUADRADO, RECTÁNGULO, TRIÁNGULO:** Giran alrededor de su punto central.\n" +
                "     - **CÍRCULO:** Aunque no cambia su apariencia debido a la simetría, el centro del círculo puede rotar si está combinado con otras figuras.\n" +
                "   - **Ejemplo:**\n" +
                "     - `RECT1.ROTAR(90);` (El rectángulo gira 90° en el sentido horario).\n\n" +

                "**4. Delimitador**\n" +
                "El comando debe terminar con un punto y coma **(;)**.\n\n" +

                "### Ejemplos de Uso\n\n" +
                "**1. Escalar un rectángulo:**\n" +
                "`RECT1 = CREAR.RECTANGULO(10, 20, 1, 0, 0);`\n" +
                "`RECT1.ESCALAR(2);`\n" +
                "   - **Resultado:** El rectángulo, que tenía dimensiones 10x20, ahora será 20x40.\n\n" +

                "**2. Rotar un triángulo:**\n" +
                "`TRIAN1 = CREAR.TRIANGULO(10, 4, 0, 0);`\n" +
                "`TRIAN1.ROTAR(-45);`\n" +
                "   - **Resultado:** El triángulo gira 45° en sentido antihorario.\n\n" +

                "**3. Escalar y rotar un círculo:**\n" +
                "`CIRC1 = CREAR.CIRCULO(5, 3, 10, 10);`\n" +
                "`CIRC1.ESCALAR(3);`\n" +
                "`CIRC1.ROTAR(90);`\n" +
                "   - **Resultado:** El círculo ahora tiene un radio de 15 y gira 90°, aunque la rotación no afecta su forma.\n\n" +

                "### Errores Comunes\n\n" +
                "**1. Identificador no válido:**\n" +
                "`TRANSFORMAR.NO_EXISTE.ESCALAR(2);`\n" +
                "   - **Error:** **El identificador NO_EXISTE no ha sido definido previamente.**\n\n" +

                "**2. Valor de transformación inválido:**\n" +
                "`RECT1.ESCALAR(-2);`\n" +
                "   - **Error:** **El valor para ESCALAR debe ser mayor que 0.**\n\n" +

                "**3. Falta de parámetros o delimitadores:**\n" +
                "`RECT1.ROTAR;`\n" +
                "   - **Error:** **Falta el valor del ángulo de rotación y el punto y coma ;.**\n\n" +

                "**4. Operación no permitida:**\n" +
                "`RECT1.TRASLADAR(10, 20);`\n" +
                "   - **Error:** **TRASLADAR no es una transformación válida.**\n\n" +

                "### Validaciones Importantes\n\n" +
                " - Las transformaciones solo se aplican a figuras previamente creadas.\n" +
                " - Los valores para **ESCALAR** deben ser enteros positivos.\n" +
                " - Los valores para **ROTAR** deben ser números enteros (positivos o negativos).\n" +
                " - El identificador debe ser válido y corresponder a una figura existente.\n";

        // Actualiza el TextArea con el contenido de la sección 4.3
        contentArea.setText(content);
    }


    private void updateContentFor4_4() {
        String content = "4.4 Modificar Parámetros\n\n" +
                "El comando **MODIFICAR** permite cambiar atributos específicos de una figura previamente creada, sin necesidad de redefinirla completamente. " +
                "Los atributos que se pueden modificar son el color, la posición y las dimensiones.\n\n" +

                "### Estructura del Comando\n\n" +
                "`<identificador>.<atributo>(<nuevo_valor>);`\n\n" +

                "### Explicación de los Componentes\n\n" +
                "**1. <identificador>**\n" +
                "Es el nombre de la figura previamente creada que se desea modificar.\n" +
                "   - **Reglas:**\n" +
                "     - Debe ser un identificador válido.\n" +
                "     - La figura debe haber sido creada previamente con el comando **CREAR**.\n" +
                "     - Ejemplo válido: `CUAD1`.\n" +
                "     - Ejemplo inválido: `FIGURA_NO_EXISTE` (Error: El identificador no está definido).\n\n" +

                "**2. MODIFICAR**\n" +
                "Palabra reservada que indica que se desea realizar un cambio en un atributo de la figura.\n\n" +

                "**3. <atributo>**\n" +
                "Especifica qué característica de la figura se desea cambiar.\n" +
                "   - **Atributos válidos:**\n" +
                "     - **COLOR:** Cambia el color de la figura.\n" +
                "     - **POSICION:** Cambia las coordenadas de la figura en el plano cartesiano.\n" +
                "       - Puedes modificar X y Y simultáneamente o individualmente.\n" +
                "     - **DIMENSION:** Cambia las dimensiones de la figura.\n" +
                "       - Para figuras como **RECTÁNGULO**, debes modificar tanto el ancho como el alto juntos.\n\n" +

                "**4. <nuevo_valor>**\n" +
                "Es el nuevo valor asignado al atributo.\n" +
                "   - **Reglas por atributo:**\n" +
                "     - **COLOR:** Un número entero en el rango [1-4] que representa los colores primarios:\n" +
                "       - `1`: Rojo\n" +
                "       - `2`: Azul\n" +
                "       - `3`: Amarillo\n" +
                "       - `4`: Verde\n" +
                "     - **POSICION:** Un conjunto de dos números enteros (X, Y) o valores individuales para X y Y, dentro del rango [-100, 100].\n" +
                "     - **DIMENSION:** Un número entero positivo mayor que 0. Para **RECTÁNGULO**, puedes especificar (ancho, alto).\n\n" +

                "**5. Delimitador**\n" +
                "El comando debe finalizar con un punto y coma **(;)**.\n\n" +

                "### Atributos Detallados\n\n" +
                "**1. COLOR**\n" +
                "Cambia el color de la figura.\n" +
                "   - **Ejemplo válido:**\n" +
                "     - `CUAD1.COLOR(2);`\n" +
                "     - **Resultado:** El color de **CUAD1** cambia a azul (2).\n\n" +

                "**2. POSICION**\n" +
                "Cambia las coordenadas de la figura en el plano cartesiano.\n" +
                "   - **Ejemplos:**\n" +
                "     - Modificar ambas coordenadas:\n" +
                "       - `RECT1.POSICION(15, -15);`\n" +
                "       - **Resultado:** **RECT1** se mueve a la posición (15, -15).\n" +
                "     - Modificar solo la coordenada X:\n" +
                "       - `RECT1.POSICION(15, _);`\n" +
                "       - **Resultado:** **RECT1** mantiene la misma coordenada Y, pero su coordenada X cambia a 15.\n\n" +

                "**3. DIMENSION**\n" +
                "Cambia las dimensiones de la figura.\n" +
                "   - **Ejemplos:**\n" +
                "     - Modificar tanto el ancho como el alto:\n" +
                "       - `RECT1.DIMENSION(10, 40);`\n" +
                "       - **Resultado:** **RECT1** ahora tiene un ancho de 10 y un alto de 40.\n\n" +

                "### Ejemplos de Uso\n\n" +
                "**1. Cambiar el color de un cuadrado:**\n" +
                "`CUAD1.COLOR(3);`\n" +
                "   - **Resultado:** **CUAD1** ahora es amarillo (3).\n\n" +

                "**2. Mover un rectángulo completamente:**\n" +
                "`RECT1.POSICION(20, -10);`\n" +
                "   - **Resultado:** **RECT1** se mueve a (20, -10).\n\n" +

                "**3. Mover un rectángulo:**\n" +
                "`RECT1.POSICION(15, 10);`\n" +
                "   - **Resultado:** **RECT1** cambia su coordenada X a 15 y mantiene su coordenada Y.\n\n" +

                "**4. Cambiar el radio de un círculo:**\n" +
                "`CIRC1.DIMENSION(25);`\n" +
                "   - **Resultado:** El radio de **CIRC1** cambia a 25.\n\n" +

                "**5. Modificar el ancho y alto de un rectángulo:**\n" +
                "`RECT1.DIMENSION(10, 20);`\n" +
                "   - **Resultado:** El ancho de **RECT1** cambia a 10 y el alto a 20.\n\n" +

                "### Errores Comunes\n\n" +
                "**1. Identificador no definido:**\n" +
                "`FIGURA_NO_EXISTE.COLOR(3);`\n" +
                "   - **Error:** El identificador **FIGURA_NO_EXISTE** no ha sido creado previamente.\n\n" +

                "**2. Atributo inválido:**\n" +
                "`CUAD1.ALTURA(10);`\n" +
                "   - **Error:** **ALTURA** no es un atributo válido.\n\n" +

                "**3. Valor fuera de rango:**\n" +
                "`CUAD1.COLOR(5);`\n" +
                "   - **Error:** El color 5 no está en el rango permitido [1-4].\n\n" +

                "**4. Uso incorrecto del marcador _:**\n" +
                "`RECT1.POSICION(, );`\n" +
                "   - **Error:** Debes especificar ambos valores válidos.\n\n" +

                "### Validaciones Importantes\n\n" +
                " - Para **posiciones**:\n" +
                "   - Debes modificar ambas coordenadas (X, Y) en una sola instrucción.\n" +
                "   - Los valores deben estar en el rango [-100, 100].\n" +
                " - Para **dimensiones**:\n" +
                "   - Los valores deben ser positivos y mayores que 0.\n";

        // Actualiza el TextArea con el contenido de la sección 4.4
        contentArea.setText(content);
    }

    private void updateContentFor4_5() {
        String content = "4.5 Eliminar Figura\n\n" +
                "El comando **ELIMINAR** permite eliminar una figura previamente creada, eliminando tanto su definición como sus atributos. " +
                "Una vez eliminada la figura, ya no podrá ser utilizada en ninguna operación posterior.\n\n" +

                "### Estructura del Comando\n\n" +
                "`ELIMINAR.<identificador>;`\n\n" +

                "### Explicación de los Componentes\n\n" +
                "**1. <identificador>**\n" +
                "Es el nombre de la figura previamente creada que se desea eliminar.\n" +
                "   - **Reglas:**\n" +
                "     - Debe ser un identificador válido.\n" +
                "     - La figura debe haber sido creada previamente con el comando **CREAR**.\n" +
                "     - Ejemplo válido: `CUAD1`.\n" +
                "     - Ejemplo inválido: `FIGURA_NO_EXISTE` (Error: El identificador no está definido).\n\n" +

                "**2. ELIMINAR**\n" +
                "Palabra reservada que indica que se desea eliminar la figura y todos sus atributos.\n\n" +

                "**3. Delimitador**\n" +
                "El comando debe finalizar con un punto y coma **(;)**.\n\n" +

                "### Ejemplos de Uso\n\n" +
                "**Eliminar un cuadrado:**\n" +
                "`ELIMINAR CUAD1;`\n" +
                "   - **Resultado:** La figura **CUAD1** es eliminada, y ya no estará disponible para su uso posterior.\n\n" +

                "**Eliminar un rectángulo:**\n" +
                "`ELIMINAR RECT1;`\n" +
                "   - **Resultado:** La figura **RECT1** es eliminada y sus atributos se pierden.\n\n" +

                "### Errores Comunes\n\n" +
                "**1. Identificador no definido:**\n" +
                "`ELIMINAR FIGURA_NO_EXISTE;`\n" +
                "   - **Error:** El identificador **FIGURA_NO_EXISTE** no ha sido creado previamente. Debes asegurarte de que la figura haya sido creada antes de eliminarla.\n\n" +

                "**2. Uso incorrecto del delimitador:**\n" +
                "`ELIMINAR FIGURA;`\n" +
                "   - **Error:** El comando debe finalizar con un punto y coma **(;)**.\n\n" +

                "### Validaciones Importantes\n\n" +
                "- **Existencia de la figura:**\n" +
                "  - Asegúrate de que la figura a eliminar ha sido creada previamente con el comando **CREAR**. Si intentas eliminar una figura no definida, el sistema generará un error.\n\n" +
                "- **Eliminación irreversible:**\n" +
                "  - Una vez que una figura es eliminada, sus atributos no pueden recuperarse. Si necesitas seguir utilizando una figura, no la elimines.\n";

        // Actualiza el TextArea con el contenido de la sección 4.5
        contentArea.setText(content);
    }

    private void updateContentForAboutUs() {
        String content = "### About Us\n\n" +
                "Este intérprete fue desarrollado como parte de un proyecto para la materia **Lenguajes y Autómatas II**, " +
                "impartida por el Profesor **González González Ricardo**. El proyecto tiene como objetivo principal " +
                "demostrar la creación y manipulación de figuras geométricas mediante un lenguaje de programación específico, " +
                "utilizando conceptos clave de interpretación y análisis léxico, sintáctico y semántico.\n\n" +

                "### Integrantes del Equipo 6:\n\n" +
                "1. **Fuentes Rosas Cristian**\n" +
                "2. **Banda Mendoza Alma Andrea**\n" +
                "3. **Hernández Saucillo Stefany**\n\n" +

                "A lo largo del desarrollo del proyecto, se implementaron diversas funcionalidades como la creación, modificación, " +
                "transformación, y eliminación de figuras geométricas, todo basado en un conjunto de reglas sintácticas y semánticas " +
                "definidas para asegurar un procesamiento adecuado del código. Este intérprete permite interactuar con un lenguaje " +
                "personalizado que facilita la manipulación de objetos gráficos en un plano cartesiano.\n\n" +

                "Gracias al trabajo colaborativo y la dedicación de todo el equipo, el proyecto se ha podido llevar a cabo de manera exitosa. " +
                "Este intérprete representa no solo un aprendizaje práctico sobre la interpretación de lenguajes, sino también una valiosa " +
                "experiencia en el desarrollo de soluciones informáticas basadas en lenguajes formales.\n";

        // Actualiza el TextArea con el contenido de la sección About Us
        contentArea.setText(content);
    }


}
