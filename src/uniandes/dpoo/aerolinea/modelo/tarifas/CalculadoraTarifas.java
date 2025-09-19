package uniandes.dpoo.aerolinea.modelo.tarifas;

import java.util.*;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;


public abstract class CalculadoraTarifas {

    public static final double IMPUESTO = 0.28;


    public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
        int costoBase = calcularCostoBase(vuelo, cliente);
        double pDesc = calcularPorcentajeDescuento(cliente);
        int descuento = (int) Math.round(costoBase * pDesc);
        int impuestos = calcularValorImpuestos(costoBase);
        int total = costoBase - descuento + impuestos;
        return Math.max(total, 0);
    }

    protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);

    protected abstract double calcularPorcentajeDescuento(Cliente cliente);


    protected int calcularDistanciaVuelo(Ruta ruta) {
        Aeropuerto ori = ruta.getOrigen();
        Aeropuerto des = ruta.getDestino();
        return ori.calcularDistancia(des);
    }

    protected int calcularValorImpuestos(int costoBase) {
        return (int) Math.round(costoBase * IMPUESTO);
    }
}
