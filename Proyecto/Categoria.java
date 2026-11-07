public class Categoria {
    // Atributos privados Diccionario de Datos
    private String codigo_categoria;
    private String nombre_categoria;
    private int nivel_gravedad; // 1 (Muy leve) a 5 (Crítico)
    private String descripcion;
    private boolean estado_activo;

    // Constructor
    public Categoria(String codigo_categoria, String nombre_categoria, 
                     int nivel_gravedad, String descripcion, boolean estado_activo) {
        this.codigo_categoria = codigo_categoria;
        this.nombre_categoria = nombre_categoria;
        this.nivel_gravedad = nivel_gravedad;
        this.descripcion = descripcion;
        this.estado_activo = estado_activo;
    }

    // Getters y Setters
    public String getCodigo_categoria() { return codigo_categoria; }
    public void setCodigo_categoria(String codigo_categoria) { this.codigo_categoria = codigo_categoria; }

    public String getNombre_categoria() { return nombre_categoria; }
    public void setNombre_categoria(String nombre_categoria) { this.nombre_categoria = nombre_categoria; }

    public int getNivel_gravedad() { return nivel_gravedad; }
    public void setNivel_gravedad(int nivel_gravedad) { this.nivel_gravedad = nivel_gravedad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isEstado_activo() { return estado_activo; }
    public void setEstado_activo(boolean estado_activo) { this.estado_activo = estado_activo; }

    // Formato delimitado por punto y coma para el archivo categorias.txt
    public String aFormatoArchivo() {
        return codigo_categoria + ";" + nombre_categoria + ";" + 
               nivel_gravedad + ";" + descripcion + ";" + estado_activo;
    }

    @Override
    public String toString() {
        return "Código: " + codigo_categoria + " | Nombre: " + nombre_categoria +
               " | Nivel Gravedad: " + nivel_gravedad + " | Estado: " + (estado_activo ? "Activa" : "Inactiva") +
               "\n   Descripción: " + descripcion;
    }
}
