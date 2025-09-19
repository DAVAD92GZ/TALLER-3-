package uniandes.dpoo.aerolinea.modelo;



public class Avion implements Comparable<Avion> {

    private final String codigo;

    private String modelo;

    private int capacidad;


    public Avion(String codigo, String modelo, int capacidad) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del avión es obligatorio.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo del avión es obligatorio.");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.codigo = codigo.trim();
        this.modelo = modelo.trim();
        this.capacidad = capacidad;
    }


    public String getCodigo() {
        return codigo;
    }


    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo del avión es obligatorio.");
        }
        this.modelo = modelo.trim();
    }


    public int getCapacidad() {
        return capacidad;
    }


    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
    }

 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Avion)) return false;
        Avion other = (Avion) obj;
        return this.codigo.equals(other.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    public int compareTo(Avion o) {
        if (o == null) return 1;
        return this.codigo.compareTo(o.codigo);
    }

    @Override
    public String toString() {
        return "Avion{codigo='" + codigo + "', modelo='" + modelo + "', capacidad=" + capacidad + "}";
    }
}
