

public class Main{
	
    private static Monitor monitor;


    public static void main(String[] args) {

        CPU_buffer buffer1 = new CPU_buffer();
        CPU_buffer buffer2 = new CPU_buffer();
        
        monitor = new Monitor(buffer1, buffer2);
        
        Thread[] CPUS = new Thread[2];
        	Asignador asignador=new Asignador(monitor, buffer1, buffer2);
        	Thread prod =new Productor(0, monitor, asignador);
        	Thread CPU1= new CPU1(1, monitor, buffer1);
        	Thread CPU2= new CPU2(2, monitor, buffer2);
        	CPUS[0]=CPU1;
        	CPUS[1]=CPU2;
        	prod.start();
        	CPU1.start();
        	CPU2.start();
        	
       
        Thread log=new Thread(new Log(buffer1, buffer2, CPUS));
        log.start();

        try {
                prod.join();
            for(int i=0; i<2; i++)
            {
                CPUS[i].join();
            }
            log.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

