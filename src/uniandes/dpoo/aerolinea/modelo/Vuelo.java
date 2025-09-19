package uniandes.dpoo.aerolinea.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;


public class Vuelo {

    private final Ruta ruta;

    private final Avion avion;

    private final LocalDate fecha;

    private final List<Tiquete> tiquetes;

    public Vuelo(Ruta ruta, Avion avion, LocalDate fecha) {
        if (ruta == null) throw new IllegalArgumentException("La ruta no puede ser nula.");
        if (avion == null) throw new IllegalArgumentException("El avión no puede ser nulo.");
        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");
        this.ruta = ruta;
        this.avion = avion;
        this.fecha = fecha;
        this.tiquetes = new ArrayList<>();
    }

    public Ruta getRuta() { return ruta; }

    public Avion getAvion() { return avion; }

    public LocalDate getFecha() { return fecha; }

    public List<Tiquete> getTiquetes() {
        return Collections.unmodifiableList(tiquetes);
    }

    public Tiquete venderTiquete(Cliente cliente, CalculadoraTarifas calc)
            throws VueloSobrevendidoException {
        if (tiquetes.size() >= avion.getCapacidad()) {
            throw new VueloSobrevendidoException("El vuelo ya está lleno.");
        }
        double precio = calc.calcularTarifa(cliente, this);
        Tiquete t = new Tiquete(cliente, this, precio);
        tiquetes.add(t);
        return t;
    }


    public int getCapacidadDisponible() {
        return avion.getCapacidad() - tiquetes.size();
    }


    public double calcularOcupacion() {
        return (double) tiquetes.size() / avion.getCapacidad();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vuelo)) return false;
        Vuelo other = (Vuelo) obj;
        return ruta.equals(other.ruta) && fecha.equals(other.fecha);
    }

    @Override
    public int hashCode() {
        return ruta.hashCode() * 31 + fecha.hashCode();
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "ruta=" + ruta +
                ", avion=" + avion.getCodigo() +
                ", fecha=" + fecha +
                ", tiquetesVendidos=" + tiquetes.size() +
                "/" + avion.getCapacidad() +
                '}';
    }
}
