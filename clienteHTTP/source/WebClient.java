/** PROYECTO 4 - Ehecatzin Vallejo Serrano - 4CM14*/
package networking;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    //Objeto HttpClient para instanciar clientes HTTP
    private HttpClient client;
     //Instancia del objeto HTTP con procolo HTTP 1.1
    public WebClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }
    //Recibe la direccion para entablar la conexion y lps datos a enviar hacia el servidor 
    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) {
        //Creación de un objeto HTTP para realizar petición HTTP con el Metodo POST y la direccion de destino 
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .header("X-debug","false")
                .build();
      //Envio de la solicitud request de una manera asincrona 
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        //respuesta.headers().toString() 
                .thenApply(respuesta -> { return respuesta.body().toString();});
    }
}


