package core;

import java.util.Observer;
import java.util.Scanner;

public class Main {

    private static void simpleTest(int period, Clock clock) throws InterruptedException {
        //creo el root del projecte per poder crear tasks(1) o intervals(2)
        Project root = new Project("root","root",null);

        //Prints & sc
        Scanner sc= new Scanner(System.in);
        System.out.println("What do you want to do ?");
        System.out.println("1. Create task");
        System.out.println("2. Count time");
        String str= sc.nextLine();

/**Creem una task al root, a la taskhi assignem un interval i fem que observi durant 10 segons**/
        if(str.equals("1")){
            System.out.println("equal to 1");

            //Creo la task i interval
            Task T1 = new Task("T1","Child of root",root);
            Interval int1 = new Interval(T1);

            //Insercio
            root.inserirFill(T1);
            T1.inserirFill(int1);

            //afegim l'observer
            clock.addObserver(int1);
            int1.update(clock, clock.getDate());

             wait(10);

            System.out.println(T1.getNom()+", durada:"+T1.getDurada());
            System.out.println(root.nom+",durada:"+ root.getDurada());
        }



    }

    // temps d'activitat definit del programa en seconds. Fil d'espera
    private static void wait(int seconds){
        try{
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int period =2;

        Clock clock = new Clock(period);
        Thread thread=new Thread(clock);
        thread.start();/**inicia el run() del clock en un Thread**/

        simpleTest(period, clock);


        clock.stop();

    }





}
