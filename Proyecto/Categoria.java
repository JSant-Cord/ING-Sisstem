
public class Categoria { // Declara la clase Categoria, que representa una categoría.

// Atributos privados: almacenan la información de cada categoría.
// private significa que solo se pueden acceder directamente desde esta clase.
    private String codigo_categoria; // Guarda el código que identifica la categoría.
    private String nombre_categoria; // Guarda el nombre de la categoría.
    private int nivel_gravedad; // Guarda el nivel de gravedad, desde 1 (Muy leve) hasta 5 (Crítico).
    private String descripcion; // Guarda una descripción de la categoría.
    private boolean estado_activo; // Indica si la categoría está activa (true) o inactiva (false).

    // Constructor: permite crear un objeto Categoria con sus datos iniciales.
    public Categoria(String codigo_categoria, String nombre_categoria,
                     int nivel_gravedad, String descripcion, boolean estado_activo) {

        this.codigo_categoria = codigo_categoria; // Asigna el código recibido al atributo de la clase.
        this.nombre_categoria = nombre_categoria; // Asigna el nombre recibido al atributo de la clase.
        this.nivel_gravedad = nivel_gravedad; // Asigna el nivel de gravedad recibido.
        this.descripcion = descripcion; // Asigna la descripción recibida.
        this.estado_activo = estado_activo; // Asigna el estado activo o inactivo recibido.
    }

    // Getters y Setters: permiten consultar y modificar los atributos privados.

    // Getter: devuelve el código de la categoría.
    public String getCodigo_categoria() {
        return codigo_categoria; // Retorna el valor del código.
    }

    // Setter: permite modificar el código de la categoría.
    public void setCodigo_categoria(String codigo_categoria) {
        this.codigo_categoria = codigo_categoria; // Guarda el nuevo código.
    }

    // Getter: devuelve el nombre de la categoría.
    public String getNombre_categoria() {
        return nombre_categoria; // Retorna el nombre.
    }

    // Setter: permite modificar el nombre de la categoría.
    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria; // Guarda el nuevo nombre.
    }

    // Getter: devuelve el nivel de gravedad.
    public int getNivel_gravedad() {
        return nivel_gravedad; // Retorna el nivel de gravedad.
    }

    // Setter: permite modificar el nivel de gravedad.
    public void setNivel_gravedad(int nivel_gravedad) {
        this.nivel_gravedad = nivel_gravedad; // Guarda el nuevo nivel de gravedad.
    }

    // Getter: devuelve la descripción de la categoría.
    public String getDescripcion() {
        return descripcion; // Retorna la descripción.
    }

    // Setter: permite modificar la descripción.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Guarda la nueva descripción.
    }

    // Getter: devuelve el estado de la categoría.
    // Se utiliza is porque el atributo es de tipo boolean.
    public boolean isEstado_activo() {
        return estado_activo; // Retorna true si está activa o false si está inactiva.
    }

    // Setter: permite modificar el estado de la categoría.
    public void setEstado_activo(boolean estado_activo) {
        this.estado_activo = estado_activo; // Guarda el nuevo estado.
    }

    // Método que convierte los datos de la categoría en un texto.
    // Utiliza punto y coma (;) para separar los datos y guardarlos en categorias.txt.
    public String aFormatoArchivo() {
        return codigo_categoria + ";" + nombre_categoria + ";" +
               nivel_gravedad + ";" + descripcion + ";" + estado_activo;
        // Retorna todos los atributos unidos por punto y coma.
    }

    // @Override indica que se está sobrescribiendo un método de la clase Object.
    // toString permite representar el objeto Categoria como un texto.
    @Override
    public String toString() {

        // Construye y retorna una cadena con la información de la categoría.
        return "Código: " + codigo_categoria + " | Nombre: " + nombre_categoria +
               " | Nivel Gravedad: " + nivel_gravedad + " | Estado: " +
               (estado_activo ? "Activa" : "Inactiva") +
               // El operador ternario verifica el estado:
               // Si es true muestra "Activa"; si es false muestra "Inactiva".
               "\n   Descripción: " + descripcion;
               // \n genera un salto de línea antes de mostrar la descripción.
    }
}
