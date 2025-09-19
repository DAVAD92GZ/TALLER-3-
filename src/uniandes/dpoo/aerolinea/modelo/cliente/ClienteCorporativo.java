package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

/**
 * Esta clase se usa para representar a los clientes de la aerolínea que son empresas
 */
public class ClienteCorporativo extends Cliente
{
    // TODO completar
    
    public static final String CORPORATIVO = "CORPORATIVO";
    
    private String nombreEmpresa;
    private int tamanoEmpresa;

    /**
     * Crea un nuevo objeto de tipo a partir de un objeto JSON.
     * 
     * El objeto JSON debe tener dos atributos: nombreEmpresa (una cadena) y tamanoEmpresa (un número).
     * @param cliente El objeto JSON que contiene la información
     * @return El nuevo objeto inicializado con la información
     */
    public ClienteCorporativo(String nombreEmpresa, int tamanoEmpresa) {
    
        super(nombreEmpresa);  
        
        if (nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la empresa es obligatorio.");
        }
        if (tamanoEmpresa <= 0) {
            throw new IllegalArgumentException("El tamaño de la empresa debe ser mayor que cero.");
        }
        this.nombreEmpresa = nombreEmpresa.trim();
        this.tamanoEmpresa = tamanoEmpresa;
    }
    
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
    
    public int getTamanoEmpresa() {
        return tamanoEmpresa;
    }

    @Override
    public String getTipoCliente() {
        return CORPORATIVO;
    }
    
    public static ClienteCorporativo cargarDesdeJSON( JSONObject cliente )
    {
        String nombreEmpresa = cliente.getString( "nombreEmpresa" );
        int tam = cliente.getInt( "tamanoEmpresa" );
        return new ClienteCorporativo( nombreEmpresa, tam );
    }

    /**
     * Salva este objeto de tipo ClienteCorporativo dentro de un objeto JSONObject para que ese objeto se almacene en un archivo
     * @return El objeto JSON con toda la información del cliente corporativo
     */
    public JSONObject salvarEnJSON( )
    {
        JSONObject jobject = new JSONObject( );
        jobject.put( "nombreEmpresa", this.nombreEmpresa );
        jobject.put( "tamanoEmpresa", this.tamanoEmpresa );
        jobject.put( "tipo", CORPORATIVO );
        return jobject;
    }
    @Override
    public String toString() {
        return "ClienteCorporativo{empresa='" + nombreEmpresa + "', tamaño=" + tamanoEmpresa + "}";
    }
}
