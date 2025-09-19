package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;



public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

    private static final int COSTO_POR_KM_NATURAL      = 600;
    private static final int COSTO_POR_KM_CORPORATIVO  = 900;

    private static final double DESCUENTO_GRANDES   = 0.20;
    private static final double DESCUENTO_MEDIANAS  = 0.10; 
    private static final double DESCUENTO_PEQ       = 0.02; 

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int distancia = calcularDistanciaVuelo(vuelo.getRuta());
        int costoKm = (cliente instanceof ClienteNatural)
                ? COSTO_POR_KM_NATURAL
                : COSTO_POR_KM_CORPORATIVO;
        return distancia * costoKm;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente) {
        if (cliente instanceof ClienteCorporativo) {
            ClienteCorporativo corp = (ClienteCorporativo) cliente;
            int desc = corp.getTamanoEmpresa();
           
           
            switch (desc) {
                case 1:  return DESCUENTO_GRANDES;
                case 2:  return DESCUENTO_MEDIANAS;
                case 3:  return DESCUENTO_PEQ;
                default: return 0.0; 
            }
        }
      
        return 0.0;
    }