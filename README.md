GeoMatrix Interpreter
GeoMatrix es una herramienta interactiva que permite crear, modificar y visualizar figuras geométricas en una interfaz gráfica. Los usuarios pueden manipular figuras como cuadrados, rectángulos, círculos y triángulos a través de comandos sencillos escritos en un lenguaje de programación propio. El sistema está basado en un intérprete que procesa los comandos y ejecuta las operaciones en tiempo real.

Características Principales
Creación de Figuras: Crea figuras geométricas como rectángulos, círculos, triángulos y cuadrados.
Modificación de Figuras: Modifica propiedades de las figuras como color, tamaño y posición.
Transformaciones: Aplica transformaciones a las figuras, como escalar y rotar.
Cálculos de Propiedades: Calcula el área y el perímetro de las figuras.
Crecimiento Gradual: Las figuras pueden crecer progresivamente hasta un tamaño máximo.
Interactividad en Tiempo Real: Los comandos se procesan e interactúan con la interfaz de usuario de forma inmediata.
Tecnologías Utilizadas
Lenguaje: Java
Framework de GUI: JavaFX
Herramientas: Autómatas finitos (DFA) y autómatas de pila (PDA) para el análisis léxico y sintáctico.
Análisis: Análisis léxico, sintáctico y semántico.
Requisitos
Para ejecutar este proyecto, necesitarás tener instalado:

Java 8 o superior
JavaFX para la interfaz gráfica
Un IDE como IntelliJ IDEA o Eclipse (opcional)
Instalación
Clona este repositorio:

git clone [https://github.com/tu-usuario/GeoMatrix.git](https://github.com/SisifoSaga/GEOMATRIXFINAL)
Abre el proyecto en tu IDE preferido.
Asegúrate de tener configurado JavaFX en tu entorno de desarrollo.
Compila y ejecuta el proyecto.
Uso
Comandos Básicos:
Los comandos deben ingresarse en la terminal de la interfaz gráfica. A continuación se presentan algunos ejemplos de los comandos disponibles:

Crear una figura:
Rectángulo:

RECT1 = CREAR.RECTANGULO(10, 20, 1, 0, 0);
Esto crea un rectángulo con ancho 10, altura 20, color 1 (rojo) y posición en (0, 0).

Círculo:

CIRC1 = CREAR.CIRCULO(5, 2, 15, 15);
Esto crea un círculo con radio 5, color 2 (azul) y posición en (15, 15).

Modificar una figura:
Cambiar color:
RECT1.COLOR(3);
Cambia el color del rectángulo RECT1 a amarillo (color 3).

Cambiar dimensiones:

RECT1.DIMENSION(15, 40);
Cambia las dimensiones del rectángulo RECT1 a un ancho de 15 y un alto de 40.

Transformar una figura:
Escalar:
RECT1.ESCALAR(2);
Escala el rectángulo RECT1 por un factor de 2.

Rotar:
RECT1.ROTAR(90);
Rota el rectángulo RECT1 90 grados en sentido horario.

Calcular área y perímetro:
Área:
CALCULAR.AREA(RECT1);

Perímetro:
CALCULAR.PERIMETRO(RECT1);


El proyecto está estructurado de la siguiente manera:
GeoMatrix/
│
├── .idea/                        # Archivos del entorno de desarrollo (IDE), como IntelliJ IDEA
│   ├── encodings.xml
│   ├── misc.xml
│   ├── vcs.xml
│   ├── workspace.xml
│
├── .mvn/                          # Archivos relacionados con Maven Wrapper
│   ├── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
│
├── src/                           # Directorio principal de código fuente
│   └── main/
│       └── java/
│           └── com/example/geometrix/  # Paquete principal del proyecto
│               ├── GeoMatrixApp.java     # Clase principal con la lógica del intérprete
│               ├── LexicalAnalyzerGUI.java # Clase para la interfaz gráfica del analizador léxico
│               ├── Symbol.java           # Clase para la tabla de símbolos
│               ├── TablaDeSimbolos.java  # Clase para gestionar la tabla de símbolos
│               ├── TerminalApp.java      # Clase que gestiona la terminal de entrada
│               ├── UserManual.java       # Clase para gestionar el manual de usuario (si aplica)
│               ├── module-info.java      # Archivo de módulo para Java 9+ (si se usa módulos)
│
├── resources/                     # Archivos de recursos, como imágenes, configuraciones y vistas
│   └── com/example/geometrix/       # Carpeta para los recursos relacionados con el paquete
│       ├── hello-view.fxml          # Vista FXML para la interfaz de usuario (si se usa JavaFX)
│       ├── encabezado.png           # Imagen para el encabezado (usada en la interfaz gráfica)
│
├── .gitignore                      # Archivos y carpetas que Git debe ignorar (como .idea, archivos compilados, etc.)
├── mvnw                            # Script para ejecutar Maven en sistemas Unix
├── mvnw.cmd                        # Script para ejecutar Maven en sistemas Windows
├── pom.xml                         # Archivo de configuración de Maven (contiene las dependencias y plugins)

Descripción de los Archivos Principales:
Archivos del IDE (.idea):

Estos archivos son generados por IntelliJ IDEA y contienen configuraciones del proyecto específicas de este IDE. No son necesarios para el funcionamiento del proyecto, pero se utilizan para mantener la configuración del entorno de desarrollo.
Maven Wrapper (.mvn):

Los archivos en la carpeta .mvn/ permiten usar el Maven Wrapper (mvnw y mvnw.cmd), lo que facilita la ejecución de Maven en sistemas donde Maven no esté instalado. El archivo pom.xml contiene la configuración de Maven, como dependencias y plugins.
Código Fuente (src/main/java):

Contiene todas las clases de tu proyecto, como:
GeoMatrixApp.java: Clase principal que maneja la lógica del intérprete y la interfaz gráfica.
LexicalAnalyzerGUI.java: Clase que gestiona la interfaz gráfica del analizador léxico, mostrando el análisis de los comandos en la GUI.
Symbol.java: Define la estructura de la tabla de símbolos, que almacena las figuras y sus propiedades.
TablaDeSimbolos.java: Similar a Symbol.java, pero podría enfocarse en gestionar y mostrar la tabla completa.
TerminalApp.java: Clase que maneja la terminal donde el usuario ingresa los comandos.
UserManual.java: Clase opcional para manejar y mostrar un manual de usuario dentro de la aplicación (si aplica).
module-info.java: Si estás utilizando módulos (Java 9+), este archivo define los módulos del proyecto.
Archivos de Recursos (resources):

Archivos utilizados por la aplicación, como imágenes (encabezado.png) o vistas (hello-view.fxml si se usa JavaFX para la GUI).
Archivos de Configuración de Maven y Git:

pom.xml: Es el archivo de configuración de Maven que gestiona las dependencias del proyecto, plugins y configuraciones relacionadas con la construcción del proyecto.
.gitignore: Contiene una lista de archivos y carpetas que Git debe ignorar, como los archivos generados por el IDE (.idea), archivos binarios y dependencias.

Licencia
Este proyecto está bajo la Licencia MIT. Puedes ver el archivo LICENSE para más detalles.
