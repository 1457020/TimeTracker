package TimeTracker;

import java.util.Scanner;

public class Main {
// temps d'activitat definit del programa en seconds. Fil d'espera
    private static void wait(int seconds){
        try{
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

// f per temps d'activitat indefinit  . Fil d'espera
    private static void unstop() {
        boolean stop = false;
            Scanner sc= new Scanner(System.in);

            while(!stop){
                System.out.println("");
                System.out.println("Introduce 'stop' for stop the program:");
                String str= sc.nextLine();
                System.out.print("You have entered: "+str);
                if(str.equals("stop")){stop=true;}

            }

    }



    public static void main(String[] args) {
        final int period =2;
        Clock clock = new Clock(period);
        Clock.start();
        //wait(11);
        unstop();
        clock.stop();
    }





}
