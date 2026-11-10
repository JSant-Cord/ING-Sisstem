
/**
 * Este es un comentario de documentación.
 * Asignatura: Ingeniería de Software I (4.º Semestre).
 * Estudiante: Santiago Córdoba Camacho.
 * Entidad: Reporte de Incendio Forestal.
 * Sirve para identificar de qué proyecto y entidad es esta clase.
 */

// public significa que la clase puede ser utilizada desde otros archivos.
public class Reporte {

    // private significa que estos atributos solo se pueden
    // consultar o modificar directamente desde esta misma clase.

    // String almacena texto. Aquí guarda el código del reporte.
    private String codigo_reporte;

    // Guarda el código de la categoría del incendio.
    private String codigo_categoria;

    // Guarda la fecha en la que se registró el reporte.
    private String fecha_registro;

    // double almacena números decimales.
    // Guarda la latitud de la ubicación del incendio.
    private double latitud;

    // Guarda la longitud de la ubicación del incendio.
    private double longitud;

    // Guarda una descripción escrita sobre el incendio.
    private String descripcion;


    // Este es el constructor de la clase.
    // Se ejecuta cuando se crea un nuevo objeto Reporte.
    // Recibe los seis datos necesarios para crear el reporte.
    public Reporte(String codigo_reporte, String codigo_categoria, String fecha_registro,
                   double latitud, double longitud, String descripcion) {

        // this.codigo_reporte representa el atributo del objeto.
        // El valor que está entre paréntesis es el dato recibido.
        this.codigo_reporte = codigo_reporte;

        // Asigna la categoría recibida al atributo del objeto.
        this.codigo_categoria = codigo_categoria;

        // Asigna la fecha recibida al atributo del objeto.
        this.fecha_registro = fecha_registro;

        // Asigna la latitud recibida al atributo del objeto.
        this.latitud = latitud;

        // Asigna la longitud recibida al atributo del objeto.
        this.longitud = longitud;

        // Asigna la descripción recibida al atributo del objeto.
        this.descripcion = descripcion;
    }


    // GETTERS Y SETTERS:
    // Los getters permiten consultar los valores de los atributos.
    // Los setters permiten modificar los valores de los atributos.

    // Getter: devuelve el código del reporte.
    public String getCodigo_reporte() {
        return codigo_reporte;
    }

    // Setter: permite cambiar el código del reporte.
    public void setCodigo_reporte(String codigo_reporte) {
        this.codigo_reporte = codigo_reporte;
    }

    // Getter: devuelve el código de la categoría.
    public String getCodigo_categoria() {
        return codigo_categoria;
    }

    // Setter: permite modificar la categoría.
    public void setCodigo_categoria(String codigo_categoria) {
        this.codigo_categoria = codigo_categoria;
    }

    // Getter: devuelve la fecha del registro.
    public String getFecha_registro() {
        return fecha_registro;
    }

    // Setter: permite modificar la fecha del registro.
    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    // Getter: devuelve la latitud del incendio.
    public double getLatitud() {
        return latitud;
    }

    // Setter: permite modificar la latitud.
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    // Getter: devuelve la longitud del incendio.
    public double getLongitud() {
        return longitud;
    }

    // Setter: permite modificar la longitud.
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    // Getter: devuelve la descripción del reporte.
    public String getDescripcion() {
        return descripcion;
    }

    // Setter: permite modificar la descripción.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    // Este método convierte los datos del reporte en una cadena de texto.
    // Se utiliza para guardar la información en un archivo reportes.txt.
    public String aFormatoArchivo() {

        // El signo + concatena (une) los textos.
        // El punto y coma separa cada campo para facilitar su lectura.
        return codigo_reporte + ";" + codigo_categoria + ";" + fecha_registro + ";" +
               latitud + ";" + longitud + ";" + descripcion;
    }


    // @Override indica que estamos sobrescribiendo un método heredado.
    // En este caso, modificamos el método toString() de Java.
    @Override

    // toString() permite mostrar los datos del objeto como texto.
    public String toString() {

        // Construye una cadena organizada con todos los datos del reporte.
        // Se utiliza para mostrar la información en pantalla.
        return "ID: " + codigo_reporte + " | Categoría: " + codigo_categoria +
               " | Fecha: " + fecha_registro + " | Lat: " + latitud +
               " | Lon: " + longitud + " | Desc: " + descripcion;
    }

// Esta llave cierra la clase Reporte.
}

