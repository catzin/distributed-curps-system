/** PROYECTO 4 - Ehecatzin Vallejo Serrano - 4CM14*/
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; 
import java.util.stream.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class Application {
    //cadenas de direcciones de los endpoints para dos servidores 
    private static final String WORKER_ADDRESS_1 = "http://34.125.212.195:80/newCurp"; 
    private static final String WORKER_ADDRESS_2 = "http://34.125.160.33:80/newCurp"; 
    private static final String WORKER_ADDRESS_3 = "http://34.125.134.146:80/newCurp"; 

    private static  List<String> taskToDo = new ArrayList<String>();
    public static String returnOption(int numeroEstado){
        String busqueda = "";
        switch(numeroEstado){
            
            case 1: busqueda = "AS";break;
            case 2: busqueda = "BC";break;
            case 3: busqueda = "BS";break;
            case 4: busqueda = "CC";break;
            case 5: busqueda = "CL";break;
            case 6: busqueda = "CM";break;
            case 7: busqueda = "CS";break;
            case 8: busqueda = "CH";break;
            case 9: busqueda = "DF";break;
            case 10: busqueda = "DG";break;
            case 11: busqueda = "GT";break;
            case 12: busqueda = "GR";break;
            case 13: busqueda = "HG";break;
            case 14: busqueda = "JC";break;
            case 15: busqueda = "MC";break;
            case 16: busqueda = "MN";break;
            case 17: busqueda = "MS";break;
            case 18: busqueda = "NT";break;
            case 19: busqueda = "NL";break;
            case 20: busqueda = "OC";break;
            case 21: busqueda = "PL";break;
            case 22: busqueda = "QT";break;
            case 23: busqueda = "QR";break;
            case 24: busqueda = "SP";break;
            case 25: busqueda = "SL";break;
            case 26: busqueda = "SR";break;
            case 27: busqueda = "TC";break;
            case 28: busqueda = "TS";break;
            case 29: busqueda = "TL";break;
            case 30:busqueda = "VZ";break;
            case 31: busqueda = "YN";break;
            case 32: busqueda = "ZS";break;
            case 33: busqueda = "NE";break;
        
        }

        return busqueda;
    }
    public static void showEntitiesMenu(){
        String data = 
        "1.AGUASCALIENTES AS"+"\n"+
        "2.BAJA CALIFORNIA BC"+"\n"+
        "3.BAJA CALIFORNIA SUR BS"+"\n"+
        "4.CAMPECHE CC"+"\n"+
        "5.COAHUILA CL "+"\n"+
        "6.COLIMA CM"+"\n"+
        "7.CHIAPAS CS"+"\n"+
        "8.CHIHUAHUA CH"+"\n"+
        "9.DISTRITO FEDERAL DF "+"\n"+
        "10.DURANGO DG"+"\n"+
        "11.GUANAJUATO GT "+"\n"+
        "12.GUERRERO GR"+"\n"+
        "13.HIDALGO HG"+"\n"+
        "14.JALISCO JC"+"\n"+
        "15.MÉXICO MC"+"\n"+
        "16.MICHOACÁN MN"+"\n"+
        "17.MORELOS MS "+"\n"+
        "18.NAYARIT NT"+"\n"+
        "19.NUEVO LEÓN NL "+"\n"+
        "20.OAXACA OC"+"\n"+
        "21.PUEBLA PL "+"\n"+
        "22.QUERÉTARO QT"+"\n"+
        "23.QUINTANA ROO QR "+"\n"+
        "24.SAN LUIS POTOSÍ SP"+"\n"+
        "25.SINALOA SL"+"\n"+
        "26.SONORA SR"+"\n"+
        "27.TABASCO TC "+"\n"+
        "28.TAMAULIPAS TS"+"\n"+
        "29.TLAXCALA TL"+"\n"+
        "30.VERACRUZ VZ"+"\n"+
        "31.YUCATÁN YN "+"\n"+
        "32.ZACATECAS ZS"+"\n"+
        "33.NACIDO EN EL EXTRANJERO NE";

        System.out.println(data);
    }

    public static void main(String[] args)throws InterruptedException{

        Aggregator aggregator = new Aggregator();
        Scanner entrada = new Scanner(System.in);
        int curpsPerMinute = 0;
        int op = 0;
        int op2 = 0;
        String state = "";
        String type = "";

        if (args.length > 1) {
            type = args[0];
            curpsPerMinute = Integer.parseInt(args[1]);
        }

        if(type.equals("Cliente")){

            while(op != 7){

                System.out.println("- - - - - - MENU - - - - --");
                System.out.println("1. Cuantos CURPS se generan por segundo");
                System.out.println("2. Cuantos registros hay en total en la base de datos.");
                System.out.println("3. Cuantos registros hay en cada uno de los tres servidores.");
                System.out.println("4. Cuantos bytes ocupa la base de datos completa y cuantos bytes se ocupan en cada servidor.");
                System.out.println("5. Cuantos registros son varones y cuantas mujeres en toda la base de datos en ese momento. ");
                System.out.println("6. Cuantos registros existen para una entidad específica que se desee buscar en ese momento");

                op = entrada.nextInt();

                switch(op){
                    case 1: System.out.println("100");break; 
                    case 2: aggregator.totalCurps(Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3));break; 
                    case 3: aggregator.lenCurps(Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3));break;
                    case 4: aggregator.totalMemory(Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3));break;
                    case 5: aggregator.totalHM(Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3));break;
                    case 6: 
                        showEntitiesMenu();
                        op2 = entrada.nextInt();
                        state = returnOption(op2);
                        aggregator.buscaEstado(Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3),state);
                    ;break;
                    default: op = 7; break;
                }

            }
            
           
        }

        else{

            Timer time = new Timer();
		    ScheduledTask st = new ScheduledTask(curpsPerMinute); 
            time.schedule(st, 0, 60000);

        }

        
    }

    }


