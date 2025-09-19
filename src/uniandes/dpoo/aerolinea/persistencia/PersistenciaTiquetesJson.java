package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.ClienteRepetidoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteTiqueteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class PersistenciaTiquetesJson implements IPersistenciaTiquetes
{

    private static final String NOMBRE_CLIENTE = "nombre";
    private static final String TIPO_CLIENTE = "tipoCliente";
    private static final String CLIENTE = "cliente";
    private static final String USADO = "usado";
    private static final String TARIFA = "tarifa";
    private static final String CODIGO_TIQUETE = "codigoTiquete";
    private static final String FECHA = "fecha";
    private static final String CODIGO_RUTA = "codigoRuta";

    /**
     * Carga la información de los clientes y tiquetes vendidos por la aerolínea, y actualiza la estructura de objetos que se encuentra dentro de la aerolínea
     * @param archivo La ruta al archivo que contiene la información que se va a cargar
     * @param aerolinea La aerolínea dentro de la cual debe almacenarse la información
     * @throws IOException Se lanza esta excepción si hay problemas leyendo el archivo
     * @throws InformacionInconsistenteException Se lanza esta excepción si hay información inconsistente dentro del archivo, o entre el archivo y el estado de la aerolínea
     */
    @Override
    public void cargarTiquetes( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException
    {
        String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        cargarClientes( aerolinea, raiz.getJSONArray( "clientes" ) );
        cargarTiquetes( aerolinea, raiz.getJSONArray( "tiquetes" ) );
    }

    /**
     * Salva en un archivo toda la información sobre los clientes y los tiquetes vendidos por la aerolínea
     * @param archivo La ruta al archivo donde debe quedar almacenada la información
     * @param aerolinea La aerolínea que tiene la información que se quiere almacenar
     * @throws IOException Se lanza esta excepción si hay problemas escribiendo el archivo
     */
    @Override
    public void salvarTiquetes( String archivo, Aerolinea aerolinea ) throws IOException
    {
        JSONObject jobject = new JSONObject( );

        // Salvar clientes
        salvarClientes( aerolinea, jobject );

        // Salvar tiquetes
        salvarTiquetes( aerolinea, jobject );

        // Escribir la estructura JSON en un archivo
        PrintWriter pw = new PrintWriter( archivo );
        jobject.write( pw, 2, 0 );
        pw.close( );
    }

    /**
     * Carga los clientes de la aerolínea a partir de un archivo JSON
     * @param aerolinea La aerolínea donde deben quedar los clientes
     * @param jClientes El elemento JSON donde está la información de los clientes
     * @throws ClienteRepetidoException Lanza esta excepción si alguno de los clientes que se van a cargar tiene el mismo identificador que otro cliente
     */
    private void cargarClientes( Aerolinea aerolinea, JSONArray jClientes ) throws ClienteRepetidoException
    {
        int numClientes = jClientes.length( );
        for( int i = 0; i < numClientes; i++ )
        {
            JSONObject cliente = jClientes.getJSONObject( i );
            String tipoCliente = cliente.getString( TIPO_CLIENTE );
            Cliente nuevoCliente = null;
            // En las siguientes líneas se utilizan dos estrategias para implementar la carga de objetos: en la primera estrategia, la carga de los objetos
            // lo hace alguien externo al objeto que se carga; en la segunda estrategia, los objetos saben cargarse.
            // En general es una mala idea mezclar las dos estrategias: acá lo hacemos para ilustrar las dos posibilidades y mostrar las ventajas y desventajas de cada una.
            // Lo que sí es recomendable es seleccionar una estrategia y usarla consistentemente para cargar y salvar.
            if( ClienteNatural.NATURAL.equals( tipoCliente ) )
            {
                // 1. En esta estrategia, en ESTA clase se realiza todo lo que tiene que ver con cargar objetos de la clase ClienteNatural
                // Al revisar el código de la clase ClienteNatural, no hay nada que tenga que ver con cargar o salvar.
                // En este caso, la persistencia es una preocupación transversal de la que no se ocupa la clase ClienteNatural
                String nombre = cliente.getString( NOMBRE_CLIENTE );
                nuevoCliente = new ClienteNatural( nombre );
            }
            else
            {
                // 2. En esta estrategia, en la clase ClienteCorporativo se realiza una parte de lo que tiene que ver con cargar objetos de la clase ClienteCorporativo.
                // La clase ClienteCorporativo tiene un método para cargar y otro para salvar.
                // En este caso, la persistencia es una preocupación de la cual se ocupa la clase ClienteCorporativo
                nuevoCliente = ClienteCorporativo.cargarDesdeJSON( cliente );
            }
            if( !aerolinea.existeCliente( nuevoCliente.getIdentificador( ) ) )
                aerolinea.agregarCliente( nuevoCliente );
            else
                throw new ClienteRepetidoException( nuevoCliente.getTipoCliente( ), nuevoCliente.getIdentificador( ) );
        }
    }

    /**
     * Salva la información de los clientes de la aerolínea dentro del objeto json que se recibe por parámetro.
     * 
     * La información de los clientes queda dentro de la llave 'clientes'
     * @param aerolinea La aerolínea que tiene la información
     * @param jobject El objeto JSON donde debe quedar la información de los clientes
     */
    private void salvarClientes( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jClientes = new JSONArray( );
        for( Cliente cliente : aerolinea.getClientes( ) )
        {
            // Acá también se utilizaron dos estrategias para salvar los clientes.
            // Para los clientes naturales, esta clase extrae la información de los objetos y la organiza para que luego sea salvada.
            // Para los clientes corporativos, la clase ClienteCorporativo hace todo lo que está en sus manos para persistir un cliente
            if( ClienteNatural.NATURAL.equals( cliente.getTipoCliente( ) ) )
            {
                JSONObject jCliente = new JSONObject( );
                jCliente.put( NOMBRE_CLIENTE, cliente.getIdentificador( ) );
                jClientes.put( jCliente );
            }
            else
            {
                ClienteCorporativo cc = ( ClienteCorporativo )cliente;
                JSONObject jCliente = cc.salvarEnJSON( );
                jClientes.put( jCliente );
            }
        }

        jobject.put( "clientes", jClientes );
    }

    private void cargarTiquetes( Aerolinea aerolinea, JSONArray jTiquetes ) throws InformacionInconsistenteTiqueteException
    {
        int num = jTiquetes.length( );
        for( int i = 0; i < num; i++ )
        {
            JSONObject jT = jTiquetes.getJSONObject( i );

            String idCliente = jT.getString( NOMBRE_CLIENTE );
            String tipo = jT.getString( TIPO_CLIENTE );
            String fecha = jT.getString( FECHA );
            String codigoRuta = jT.getString( CODIGO_RUTA );
            String codigoTiquete = jT.getString( CODIGO_TIQUETE );
            int tarifa = jT.getInt( TARIFA );
            boolean usado = jT.optBoolean( USADO, false );

            Cliente cliente = aerolinea.getCliente( idCliente );
            if( cliente == null )
            {
                if( ClienteNatural.NATURAL.equals( tipo ) )
                    cliente = new ClienteNatural( idCliente );
                else
                    cliente = ClienteCorporativo.cargarDesdeJSON( new JSONObject( ).put( NOMBRE_CLIENTE, idCliente ).put( "tamanoEmpresa", 2 ) );
                try { aerolinea.agregarCliente( cliente ); } catch( ClienteRepetidoException ignore ) {}
            }

            Vuelo vuelo = aerolinea.getVuelo( codigoRuta, fecha );
            if( vuelo == null )
                throw new InformacionInconsistenteTiqueteException( "No existe el vuelo con ruta " + codigoRuta + " en la fecha " + fecha );

            Tiquete t = new Tiquete( codigoTiquete, vuelo, cliente, tarifa );
            GeneradorTiquetes.registrarTiquete( t );
            cliente.agregarTiquete( t );

            if( usado )
                cliente.usarTiquetes( vuelo );
        }
    }

    private void salvarTiquetes( Aerolinea aerolinea, JSONObject jobject )
    {
        JSONArray jTiquetes = new JSONArray( );
        for( Tiquete t : aerolinea.getTiquetes( ) )
        {
            JSONObject jT = new JSONObject( );
            jT.put( CODIGO_TIQUETE, t.getCodigo( ) );
            jT.put( TARIFA, t.getTarifa( ) );
            jT.put( NOMBRE_CLIENTE, t.getCliente( ).getIdentificador( ) );
            jT.put( TIPO_CLIENTE, t.getCliente( ).getTipoCliente( ) );
            jT.put( FECHA, t.getVuelo( ).getFecha( ) );
            Ruta r = t.getVuelo( ).getRuta( );
            jT.put( CODIGO_RUTA, r.getCodigoRuta( ) );
            jT.put( USADO, false );
            jTiquetes.put( jT );
        }
        jobject.put( "tiquetes", jTiquetes );
    }
}
