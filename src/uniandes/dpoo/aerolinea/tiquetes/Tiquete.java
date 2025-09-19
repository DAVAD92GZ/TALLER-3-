package uniandes.dpoo.aerolinea.tiquetes;

import org.json.JSONObject;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;


public class Tiquete
{
  
    private final String codigo;

  
    private final Vuelo vuelo;

    private final Cliente cliente;

    private final int tarifa;

   
    public Tiquete(String codigo, Vuelo vuelo, Cliente cliente, int tarifa)
    {
        if (codigo == null || codigo.trim().isEmpty())
            throw new IllegalArgumentException("El código del tiquete es obligatorio.");
        if (vuelo == null)
            throw new IllegalArgumentException("El vuelo es obligatorio.");
        if (cliente == null)
            throw new IllegalArgumentException("El cliente es obligatorio.");
        if (tarifa < 0)
            throw new IllegalArgumentException("La tarifa no puede ser negativa.");

        this.codigo = codigo;
        this.vuelo = vuelo;
        this.cliente = cliente;
        this.tarifa = tarifa;
    }


    public String getCodigo()
    {
        return codigo;
    }

    public Vuelo getVuelo()
    {
        return vuelo;
    }

    public Cliente getCliente()
    {
        return cliente;
    }

    public int getTarifa()
    {
        return tarifa;
    }


    public JSONObject salvarEnJSON()
    {
        JSONObject jobject = new JSONObject();
        jobject.put("codigo", this.codigo);
        jobject.put("tarifa", this.tarifa);
        jobject.put("cliente", this.cliente.getNombre());
        jobject.put("tipoCliente", this.cliente.getTipoCliente());
        jobject.put("vuelo", this.vuelo.toString()); 
        return jobject;
    }

    public String toString()
    {
        return "Tiquete{codigo='" + codigo + "', cliente=" + cliente.getNombre()
                + ", vuelo=" + vuelo + ", tarifa=" + tarifa + "}";
    }
}
