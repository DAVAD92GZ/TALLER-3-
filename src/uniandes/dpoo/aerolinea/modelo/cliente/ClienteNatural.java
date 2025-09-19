package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

public class ClienteNatural extends Cliente
{
    public static final String NATURAL = "Natural";

    public ClienteNatural(String nombre) {
        super(nombre);
    }

    @Override
    public String getIdentificador() {
        return nombre;
    }

    @Override
    public String getTipoCliente() {
        return NATURAL;
    }

    public static ClienteNatural cargarDesdeJSON(JSONObject cliente) {
        String nombre = cliente.getString("nombre");
        return new ClienteNatural(nombre);
    }

    @Override
    public JSONObject salvarEnJSON() {
        JSONObject o = new JSONObject();
        o.put("nombre", nombre);
        o.put("tipoCliente", NATURAL);
        return o;
    }
}
