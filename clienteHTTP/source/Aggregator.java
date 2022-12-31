/** PROYECTO 4 - Ehecatzin Vallejo Serrano - 4CM14*/
import networking.WebClient;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {

    private WebClient webClient;
    public Aggregator() {
        this.webClient = new WebClient();
    }


    public String totalMemory(List<String> workersAddresses){
        byte[ ]aux = new byte[1];
        CompletableFuture<String> future1 = new CompletableFuture();
        CompletableFuture<String> future2 = new CompletableFuture();
        CompletableFuture<String> future3 = new CompletableFuture();

        future1 = webClient.sendTask("http://34.125.212.195:80/memoriaTotal",aux);
        future2 = webClient.sendTask("http://34.125.160.33:80/memoriaTotal",aux);
        future3 = webClient.sendTask("http://34.125.134.146:80/memoriaTotal",aux);

        String res1 = future1.join();
        String res2 = future1.join();
        String res3 = future1.join();

        double total = Double.parseDouble(res1) + Double.parseDouble(res2) + Double.parseDouble(res3);

        String responseMessage = "Memoria ocupada en el server 1 "+res1+" MB"+"\n"+"Memoria ocupada en el server 2 "+res2+" MB"+"\n"+"Memoria ocupada en el server 3 "+res3+" MB"+"\n"+"Memoria total: "+String.valueOf(total);;
        System.out.println(responseMessage);

        return responseMessage;

    }


    public String buscaEstado(List<String> workersAddresses, String state){
        byte[ ]aux = state.getBytes();

        CompletableFuture<String> future1 = new CompletableFuture();
        CompletableFuture<String> future2 = new CompletableFuture();
        CompletableFuture<String> future3 = new CompletableFuture();

        future1 = webClient.sendTask("http://34.125.212.195:80/buscaEstado",aux);
        future2 = webClient.sendTask("http://34.125.160.33:80/buscaEstado",aux);
        future3 = webClient.sendTask("http://34.125.134.146:80/buscaEstado",aux);

        String res1 = future1.join();
        String res2 = future2.join();
        String res3 = future3.join();

        int total = Integer.parseInt(res1) + Integer.parseInt(res2) + Integer.parseInt(res3);
        String responseMessage = "El total de registros en "+state+" en este momento es "+String.valueOf(total);
       
        System.out.println(responseMessage);

        return responseMessage;

    }

    public String lenCurps(List<String> workersAddresses){

        byte[ ]aux = new byte[1];
        CompletableFuture<String> future1 = new CompletableFuture();
        CompletableFuture<String> future2 = new CompletableFuture();
        CompletableFuture<String> future3 = new CompletableFuture();

        future1 = webClient.sendTask("http://34.125.212.195:80/curpsLen",aux);
        future2 = webClient.sendTask("http://34.125.160.33:80/curpsLen",aux);
        future3 = webClient.sendTask("http://34.125.134.146:80/curpsLen",aux);

        String res1 = future1.join();
        String res2 = future2.join();
        String res3 = future3.join();

        String responseMessage = "El servidor 1 tiene : "+res1+" curps"+"\n"+"El servidor 2 tiene : "+res2+" curps"+"\n"+"El servidor 3 tiene : "+res3+" curps"+"\n";
        System.out.println(responseMessage);
        return responseMessage;
    }

    public String totalCurps(List<String> workersAddresses){

        byte[ ]aux = new byte[1];
        CompletableFuture<String> future1 = new CompletableFuture();
        CompletableFuture<String> future2 = new CompletableFuture();
        CompletableFuture<String> future3 = new CompletableFuture();

        future1 = webClient.sendTask("http://34.125.212.195:80/curpsLen",aux);
        future2 = webClient.sendTask("http://34.125.160.33:80/curpsLen",aux);
        future3 = webClient.sendTask("http://34.125.134.146:80/curpsLen",aux);

        String res1 = future1.join();
        String res2 = future2.join();
        String res3 = future3.join();

        int total = Integer.parseInt(res1) + Integer.parseInt(res2) + Integer.parseInt(res3);
        String responseMessage = "El total de curps en este momento es "+ String.valueOf(total);
        System.out.println(responseMessage);
        return responseMessage;
    }

    public String totalHM(List<String> workersAddresses){
        //H,M
        byte[ ]aux = new byte[1];
        CompletableFuture<String> future1 = new CompletableFuture();
        CompletableFuture<String> future2 = new CompletableFuture();
        CompletableFuture<String> future3 = new CompletableFuture();

        future1 = webClient.sendTask("http://34.125.212.195:80/HM",aux);
        future2 = webClient.sendTask("http://34.125.160.33:80/HM",aux);
        future3 = webClient.sendTask("http://34.125.134.146:80/HM",aux);

        String res1 = future1.join();
        String res2 = future2.join();
        String res3 = future3.join();

        String []explit1 = res1.split(",");
        String []explit2 = res1.split(",");
        String []explit3 = res1.split(",");
       
        int totalHombres = Integer.parseInt(explit1[0]) + Integer.parseInt(explit2[0]) + Integer.parseInt(explit3[0]);
        int totalMujeres = Integer.parseInt(explit1[1]) + Integer.parseInt(explit2[1]) + Integer.parseInt(explit3[1]);
        String responseMessage = "El total de curps en este momento es  hombres: "+String.valueOf(totalHombres)+" y "+String.valueOf(totalHombres)+" mujeres";
        System.out.println(responseMessage);
        return responseMessage;

    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        System.out.println("Tasks size:"+tasks.size());
        CompletableFuture<String>[] futures = new CompletableFuture[tasks.size()];
        List<String> results = new ArrayList();
        String workerAddress = "";
        int j = 0;
        String task = "";
        String tarea = "";
        boolean bandera = false;

        for (int i = 0; i < tasks.size(); i++){

            task = tasks.get(i);
            byte [] requestPayload = task.getBytes();
        

            if(i < 3){

                workerAddress = chooseSever(workersAddresses,i);
                futures[i] = webClient.sendTask(workerAddress,requestPayload);
                System.out.println("Server:"+workerAddress+","+"tarea:"+task+"\n");

            }
            else{

                bandera = true;

                while(bandera){
                
                    if(futures[j].isDone()){
                        
                        String res = futures[j].join();
                        results.add(res);
                        workerAddress = chooseSever(workersAddresses,j);
                        System.out.println("Asignando:"+tasks.get(i)+" a: "+workerAddress);
                        futures[j] = webClient.sendTask(workerAddress,requestPayload);
                        bandera = false;
                    }
                    else{
                        j++;
                        if(j >= workersAddresses.size()){
                            j = 0;
                        }
                    }
                }

            }
        }
    
        return results;
    }

   
    public List<String> buildPayloads(int cantidad){

        List<String> taskToDo = new ArrayList<String>();
    
        for(int i = 0; i <= cantidad; i++){
            String aux = this.generateCurp();
           
            taskToDo.add(aux);
        }

        return taskToDo;

    }

    public String chooseSever(List<String> workersAddresses, int k){
        return workersAddresses.get(k);
    }

    public String generateCurp(){

        String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String Numero = "0123456789";

        String Sexo = "HM";

        String Entidad[] = {"AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS"};

        int indice;


        StringBuilder sb = new StringBuilder(18);

        

        for (int i = 1; i < 5; i++) {

            indice = (int) (Letra.length()* Math.random());

            sb.append(Letra.charAt(indice));        

        }

        for (int i = 5; i < 11; i++) {

            indice = (int) (Numero.length()* Math.random());

            sb.append(Numero.charAt(indice));        

        }

        indice = (int) (Sexo.length()* Math.random());

        sb.append(Sexo.charAt(indice));        
        sb.append(Entidad[(int)(Math.random()*32)]);

        for (int i = 14; i < 17; i++) {

            indice = (int) (Letra.length()* Math.random());

            sb.append(Letra.charAt(indice));        

        }

        for (int i = 17; i < 19; i++) {

            indice = (int) (Numero.length()* Math.random());

            sb.append(Numero.charAt(indice));        

        }

        return sb.toString();

    }
}
