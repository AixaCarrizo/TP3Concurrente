
//hace de "consumidor" para el buffer1
public class CPU1 extends Thread{
	    private int id;
	    private Monitor monitor;

	    public CPU1(int id, Monitor monitor){
	        this.id = id;
	        this.monitor = monitor;
	    }

	    @Override
	    public void run() {
	        super.run();

	        while(true) {


	            
	            switch (monitor.shoot(12)) {
	                case 12:
						proceso();
	                	break;
	                case 1: //Si no pude procesar, veo si debo poner en standBy
						monitor.shoot(1);
	                    break;

	                case 6: // despierta el nucleo
	                	monitor.shoot(14);
	                	monitor.shoot(3); // Dispara powe-up Delay
	                	break;

	                case 3:
	                    try {
	                        sleep(50);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                    monitor.quitar(2);
	                    break;

	                case -1:
	                    System.out.println("Soy un consumidor y TERMINE " + id);
	                    return;
	            }
	        }
	    }


	    public void proceso()
		{
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			monitor.shoot(5);

		}
}
