//solo genera las tareas, no las distribuye
//intenta disparar transiciones t1 y arrival_rate

public class Productor extends Thread {

	
    private int id;
    private Monitor monitor;
    private Asignador asignador;

    public Productor(int id, Monitor monitor, Asignador asignador){
        this.id = id;
        this.monitor = monitor;
        this.asignador=asignador;
    }

    @Override
    public void run() {
        super.run();

        int cont = 0;
        int cont2 = 0;
        while(cont<10) {


        	monitor.shoot(0);//hace la transici�n arrival_rate
        	try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	monitor.shoot(7);
        	//this.asignador.asignar("producto nro"+cont);
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
