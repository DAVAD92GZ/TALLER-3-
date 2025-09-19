package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente
{
    protected String nombre;

    public Cliente(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        this.nombre = nombre.trim();
    }


    public abstract String getIdentificador();

    public abstract String getTipoCliente();

    public String getNombre() { return nombre; }

    public void agregarTiquete(Tiquete tiquete) {
    }

    public void usarTiquetes(Vuelo vuelo) {
    }

    public JSONObject salvarEnJSON() {
        JSONObject o = new JSONObject();
        o.put("nombre", nombre);
        o.put("tipoCliente", getTipoCliente());
        return o;
    }
}
