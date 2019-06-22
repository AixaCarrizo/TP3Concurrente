//solo genera las tareas, no las distribuye
//intenta disparar transiciones t1 y arrival_rate

public class Productor extends Thread {

	
    private int id;
    private Monitor monitor;

    public Productor(int id, Monitor monitor){
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        super.run();

        int cont = 0;
        int cont2 = 0;
        while(cont<100) {

//            double choose = Math.random()*100 +1;
//            int index = 0;
//
//            if(choose<50) index = 1;
//            else index = 2;
            
//            switch (monitor.shoot(index)){
//                case 1:
//                    try {
//                        sleep(50);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    monitor.agregar(1, Integer.toString(id));
//                    
//                    break;
//
//                case 2:
//                    try {
//                        sleep(50);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    monitor.agregar(2, Integer.toString(id));
//                    
//                    break;
        	monitor.shoot(0);//hace la transición arrival_rate
        	try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	monitor.shoot(7);

            cont++;
            cont2++;
            //System.out.println(cont);
            if(cont2==100) {
                System.out.println("El prductor " + id + " ya lleva: " + cont);
                cont2=0;
            }
        }
        System.out.println("Soy un productor y TERMINE: " + id);
    }
}
