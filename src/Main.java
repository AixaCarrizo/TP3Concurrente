

public class Main{
	
    private static Monitor monitor;


    public static void main(String[] args) {


        /**
         * ESTO ES UNA PRUEBA DE SI PUEDO HACER PUSH.
         */



        CPU_buffer buffer1 = new CPU_buffer();
        CPU_buffer buffer2 = new CPU_buffer();
        
        monitor = new Monitor(buffer1, buffer2);



//        System.out.println(monitor.pn.isPos(0));//TRUE
//        System.out.println(monitor.pn.isPos(14));//FALSE
//        System.out.println(monitor.pn.isPos(7));//TRUE
//        System.out.println(monitor.pn.isPos(11));//FALSE
//        System.out.println(monitor.pn.isPos(14));//TRUE
//        System.out.println(monitor.pn.isPos(3));//TRUE
//        System.out.println(monitor.pn.isPos(14));//FALSE
//        System.out.println(monitor.pn.isPos(12));//TRUE
//        System.out.println(monitor.pn.isPos(1));//TRUE
//        System.out.println(monitor.pn.isPos(12));//FALSE
//        System.out.println(monitor.pn.isPos(14));//TRUE
//        System.out.println(monitor.pn.isPos(3));//TRUE
//        System.out.println(monitor.pn.isPos(12));//FALSE




//        Thread[] CPUS = new Thread[2];
//        	Asignador asignador=new Asignador(monitor, buffer1, buffer2);
//        	Thread prod =new Productor(0, monitor, asignador);
//        	Thread CPU1= new CPU1(1, monitor, buffer1);
//        	Thread CPU2= new CPU2(2, monitor, buffer2);
//        	CPUS[0]=CPU1;
//        	CPUS[1]=CPU2;
//
//        	CPU1.start();
//        	CPU2.start();
//            prod.start();
//
//
//        Thread log=new Thread(new Log(buffer1, buffer2, CPUS));
//        log.start();
//
//        try {
//                prod.join();
//            for(int i=0; i<2; i++)
//            {
//                CPUS[i].join();
//            }
//            log.join();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


}

