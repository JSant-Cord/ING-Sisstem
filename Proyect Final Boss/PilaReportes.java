public class PilaReportes {
    private Reporte[] elementos;
    private int tope;
    private static final int CAPACIDAD = 50;

    public PilaReportes() {
        this.elementos = new Reporte[CAPACIDAD];
        this.tope = -1; 
    }

    public boolean estaVacia() {
        return tope == -1;
    }

    public boolean estaLlena() {
        return tope == CAPACIDAD - 1;
    }

    // Apila
    public boolean apilar(Reporte reporte) {
        if (estaLlena()) {
            return false;
        }
        tope++;
        elementos[tope] = reporte;
        return true;
    }

    public Reporte desapilar() {
        if (estaVacia()) {
            return null;
        }
        Reporte eliminado = elementos[tope];
        elementos[tope] = null; // Limpieza de la casilla
        tope--;
        return eliminado;
    }
}
