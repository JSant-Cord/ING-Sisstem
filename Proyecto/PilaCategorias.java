
public class PilaCategorias { // Declara la clase PilaCategorias, encargada de administrar una pila de categorías.

    // Declara un arreglo que almacenará los objetos de tipo Categoria.
    private Categoria[] elementos;

    // Indica la posición del último elemento agregado a la pila.
    private int tope;

    // Declara una constante que establece la capacidad máxima de la pila en 50 elementos.
    private static final int CAPACIDAD = 50;

    // Constructor de la clase PilaCategorias.
    // Se ejecuta automáticamente cuando se crea un objeto de esta clase.
    public PilaCategorias() {

        // Crea un arreglo de tipo Categoria con capacidad para 50 elementos.
        this.elementos = new Categoria[CAPACIDAD];

        // Inicializa el tope en -1 para indicar que la pila está vacía.
        this.tope = -1;
    }

    // Método que verifica si la pila está vacía.
    // Devuelve un valor boolean: true si está vacía y false si tiene elementos.
    public boolean estaVacia() {

        // Compara el valor del tope con -1.
        // Si son iguales, significa que no hay elementos en la pila.
        return tope == -1;
    }

    // Método que verifica si la pila está llena.
    // Devuelve true si alcanzó su capacidad máxima y false si todavía tiene espacio.
    public boolean estaLlena() {

        // Compara el tope con la última posición disponible del arreglo.
        // CAPACIDAD - 1 equivale a 49 porque las posiciones comienzan desde cero.
        return tope == CAPACIDAD - 1;
    }

    // Método APILAR: permite agregar una categoría a la parte superior de la pila.
    // HU-10: se utiliza cuando se elimina una categoría del arreglo.
    // Recibe un objeto Categoria como parámetro.
    public boolean apilar(Categoria cat) {

        // Comprueba si la pila ya alcanzó su capacidad máxima.
        if (estaLlena()) {

            // Devuelve false indicando que no fue posible agregar la categoría.
            return false;
        }

        // Aumenta el valor del tope para avanzar a la siguiente posición disponible.
        tope++;

        // Almacena la categoría recibida en la posición actual del tope.
        elementos[tope] = cat;

        // Devuelve true indicando que la categoría se agregó correctamente.
        return true;
    }

    // Método DESAPILAR: permite retirar y recuperar la última categoría agregada.
    // HU-12: se utiliza para deshacer la última eliminación.
    // Devuelve un objeto de tipo Categoria.
    public Categoria desapilar() {

        // Comprueba si la pila está vacía antes de intentar retirar un elemento.
        if (estaVacia()) {

            // Devuelve null porque no existe ninguna categoría para recuperar.
            return null;
        }

        // Guarda temporalmente la categoría que está ubicada en la parte superior de la pila.
        Categoria recuperada = elementos[tope];

        // Coloca null en la posición que acaba de quedar libre.
        elementos[tope] = null;

        // Disminuye el valor del tope para indicar que se retiró un elemento.
        tope--;

        // Devuelve la categoría que fue retirada de la pila.
        return recuperada;
    }
}
