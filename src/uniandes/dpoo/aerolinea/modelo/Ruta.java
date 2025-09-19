package uniandes.dpoo.aerolinea.modelo;

/**
 * Esta clase tiene la información de una ruta entre dos aeropuertos que cubre una aerolínea.
 */
public class Ruta
{
    private String horaSalida; 
    private final Aeropuerto destino;
    private final Aeropuerto origen;
    // TODO completar

    public Ruta(Aeropuerto origen, Aeropuerto destino, String horaSalida)
    {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Origen y destino no pueden ser nulos.");
        }
        if (origen.equals(destino)) {
            throw new IllegalArgumentException("Origen y destino no pueden ser el mismo aeropuerto.");
        }
        if (horaSalida == null || horaSalida.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de salida es obligatoria.");
        }

        int horas = getHoras(horaSalida);
        int minutos = getMinutos(horaSalida);
        if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Hora de salida inválida: " + horaSalida);
        }

        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida.trim();
    }

    public Aeropuerto getOrigen()
    {
        return origen;
    }

    public Aeropuerto getDestino()
    {
        return destino;
    }

    public String getHoraSalida()
    {
        return horaSalida;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna los minutos.
     * 
     * Por ejemplo, para la cadena '715' retorna 15.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de minutos entre 0 y 59
     */
    public static int getMinutos( String horaCompleta )
    {
        int minutos = Integer.parseInt( horaCompleta ) % 100;
        return minutos;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna las horas.
     * 
     * Por ejemplo, para la cadena '715' retorna 7.
     * @param horaCompleta Una cadena con una hora, donde los minutos siempre ocupan los dos últimos caracteres
     * @return Una cantidad de horas entre 0 y 23
     */
    public static int getHoras( String horaCompleta )
    {
        int horas = Integer.parseInt( horaCompleta ) / 100;
        return horas;
    }
    
    public String toString() {
        return "Ruta{" +
                "origen=" + origen.getNombre() +
                ", destino=" + destino.getNombre() +
                ", horaSalida='" + horaSalida + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ruta)) return false;
        Ruta otra = (Ruta) obj;
        return origen.equals(otra.origen) &&
               destino.equals(otra.destino) &&
               horaSalida.equals(otra.horaSalida);
    }

    @Override
    public int hashCode() {
        int result = origen.hashCode();
        result = 31 * result + destino.hashCode();
        result = 31 * result + horaSalida.hashCode();
        return result;
    }
}
