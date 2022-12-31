/** PROYECTO 4 - Ehecatzin Vallejo Serrano - 4CM14*/
import java.util.TimerTask;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; 
import java.util.stream.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
public class ScheduledTask extends TimerTask {

    private static final String WORKER_ADDRESS_1 = "http://34.125.212.195:80/newCurp"; 
    private static final String WORKER_ADDRESS_2 = "http://34.125.160.33:80/newCurp"; 
    private static final String WORKER_ADDRESS_3 = "http://34.125.134.146:80/newCurp"; 
    List<String> tasks = new ArrayList<String>();
    private static  List<String> taskToDo = new ArrayList<String>();
    Aggregator aggregator = new Aggregator();
    int numCurps = 0;

    public ScheduledTask(int curpsLen){
        this.numCurps = curpsLen;
    }
	public void run() {
        System.out.println("EJECUTANDO MINUTO...");
        taskToDo = aggregator.buildPayloads(this.numCurps);

        List<String> results = aggregator.sendTasksToWorkers(
                Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3),
                taskToDo
            );
		
	}
}