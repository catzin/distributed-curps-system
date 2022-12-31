/** PROYECTO 4 - Ehecatzin Vallejo Serrano - 4CM14*/
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class WebServer {

  // ENDPOINTS
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";
  private static final String SEARCH_ENDPOINT = "/searchtoken";
  private static final String CURP_ENDPOINT = "/newCurp";
  private static final String CURP_QUANTITY = "/curpsLen";
  private static final String HM_ENDPOINT = "/HM";
  private static final String STATE_ENDPOINT = "/buscaEstado";
  private static final String MEMORY_ENDPOINT = "/memoriaTotal";

  private final int port; // Puerto que escucha
  private HttpServer server; // Incluye un servidor HTTP básico
  private ArrayList<String> curps = new ArrayList<String>();

  public static void main(String[] args) {
    // Se define un puerto default para el servidor
    int serverPort = 8080;
    // Mediante la terminal se puede escribir como parámetro otro puerto.
    // Si se da el nuevo puerto, se le asigna a la variable serverPort.
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
    }
    // Se instancia un objeto WebServer en el puerto asociado
    WebServer webServer = new WebServer(serverPort);
    // Se inicia el método principal para iniciar la configuración del Server
    webServer.startServer();
    // Se imprime el puerto en donde está escuchando el servidor
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }

  // Constructor del WebServer
  public WebServer(int port) {
    // Recibe e inicializa la variable puerto
    this.port = port;
  }

  // Método principal de la clase WebServer
  public void startServer() {
    try {
      // Objeto HTTPServer con instancia de socket TCP vinculada a una IP y un puerto.
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    HttpContext newCurpContext = server.createContext(CURP_ENDPOINT);
    HttpContext curpsLenContext = server.createContext(CURP_QUANTITY);
    HttpContext hmContext = server.createContext(HM_ENDPOINT);
    HttpContext stateContext = server.createContext(STATE_ENDPOINT);
    HttpContext memoryContext = server.createContext(MEMORY_ENDPOINT);
    /* setHandler recibe como parámetro el método que implementa el manejador y vincula el
            Handler para dicho contexto si no se ha inicializado ya. */

    newCurpContext.setHandler(this::handleCurpRequest);
    curpsLenContext.setHandler(this::handleCurpsLen);
    hmContext.setHandler(this::handleHMTotal);
    stateContext.setHandler(this::handleSearchState);
    memoryContext.setHandler(this::handleMemory);

    // El método setExecutor establece un objeto ejecutor al servido, este es necesario antes de iniciar.
    // Se agrega un ThreadPool de 8 hilos y se deja al ejecutor la labor de iniciarlos y asignarles
    // tareas.
    server.setExecutor(Executors.newFixedThreadPool(8));
    // Se inicia la ejecución del servidor en un hilo nuevo en segundo plano.
    server.start();
  }

  private void handleMemory(HttpExchange exchange) throws IOException {

    int dataSize = 1024 * 1024;
    Runtime runtime = Runtime.getRuntime();

    double data = (runtime.totalMemory() - runtime.freeMemory()) / dataSize;

    sendResponse(String.format("%s",data).getBytes(), exchange);

  


  }

  private void handleSearchState(HttpExchange exchange) throws IOException {
    ArrayList<String> aux = curps;
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    String data = new String(requestBytes); 
    int np = 0;
    char first = 'x';
    char second = 'x';
    String complete = "";
      
        for(String i : aux){
          first = i.charAt(11);
          second = i.charAt(12);
          complete = String.valueOf(first) + String.valueOf(second); 
          if (complete.equals(data))
                np++;
        }

    sendResponse(String.format("%s",np).getBytes(), exchange);

  }

  private void handleHMTotal(HttpExchange exchange) throws IOException {
      ArrayList<String> aux = curps;
      int h = consultaHM(aux,'H');
      int m = consultaHM(aux,'M');
      
      sendResponse(String.format("%s,%s",h,m).getBytes(), exchange);
    }

    public int consultaHM(ArrayList<String> arrCurps, char c) {
    
        int np = 0;
      
        for(String i : arrCurps){

          if (i.charAt(10) == c)
                np++;
        }

        return np;
    }

  private void handleCurpsLen(HttpExchange exchange) throws IOException {
    sendResponse(String.format("%s", curps.size()).getBytes(), exchange);
  }

  private void handleCurpRequest(HttpExchange exchange) throws IOException {
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    String data = new String(requestBytes);
    System.out.println("Almaceno:" + data);
    curps.add(data);

    sendResponse(requestBytes, exchange);
  }

  private void sendResponse(byte[] responseBytes, HttpExchange exchange)
    throws IOException {

    exchange.sendResponseHeaders(200, responseBytes.length);
    // Se escribe como el cuerpo de mensaje.
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes); // Se escribe en el Stream.
    outputStream.flush(); // Limpieza al Stream.
    outputStream.close(); // Cierra el Stream
    exchange.close(); // Cierra la transacción
  }
}
