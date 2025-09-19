package uniandes.dpoo.aerolinea.tiquetes;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

/**
 * Esta clase representa al módulo del sistema que es capaz de generar nuevos tiquetes, asignándole a cada uno un código único.
 */
public class GeneradorTiquetes
{
    /**
     * Un conjunto con los códigos que ya han sido usados anteriormente para otros tiquetes.
     * Este conjunto se utiliza para no correr el riesgo de repetir un código.
     */
    private static final Set<String> codigos = new HashSet<>();

    /**
     * Construye un nuevo tiquete con los datos dados y con un identificador que corresponde a una cadena con 7 dígitos.
     * @param vuelo El vuelo al que está asociado el tiquete
     * @param cliente El ciente que compró el tiquete
     * @param tarifa El valor que se le cobró al cliente por el tiquete
     * @return El nuevo tiquete, inicializado con un código único
     */
    public static synchronized Tiquete generarTiquete( Vuelo vuelo, Cliente cliente, int tarifa )
    {
        if (vuelo == null) throw new IllegalArgumentException("El vuelo no puede ser nulo.");
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo.");
        if (tarifa < 0) throw new IllegalArgumentException("La tarifa no puede ser negativa.");

        int numero = (int)(Math.random() * 10_000_000);
        String codigo = String.valueOf(numero);

        
        while (codigos.contains(codigo)) {
            numero = (int)(Math.random() * 10_000_000);
            codigo = String.valueOf(numero);
        }

        
        while (codigo.length() < 7) {
            codigo = "0" + codigo;
        }

        
        codigos.add(codigo);

        return new Tiquete( codigo, vuelo, cliente, tarifa );
    }

    /**
     * Registra que un cierto tiquete ya fue vendido, para que el generador de tiquetes no vaya a generar otro tiquete con el mismo código.
     * @param unTiquete El tiquete existente
     */
    public static synchronized void registrarTiquete( Tiquete unTiquete )
    {
        if (unTiquete == null) return;
        String codigo = unTiquete.getCodigo();
        if (codigo != null && !codigo.isEmpty()) {
            codigos.add(codigo);
        }
    }

    /**
     * Revisa si ya existe un tiquete con el código dado.
     * @param codigoTiquete El código que se quiere consultar
     * @return true si ya se tenía registrado un tiquete con el código dado
     */
    public static synchronized boolean validarTiquete( String codigoTiquete )
    {
        if (codigoTiquete == null) return false;
        return codigos.contains(codigoTiquete);
    }
}
