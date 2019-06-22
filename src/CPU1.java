
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

	            double choose = Math.random()*100 +1;
	            int index = 0;

	            if(choose<50) index = 0;
	            else index = 3;
	            
	            switch (monitor.shoot(index)) {
	                case 1:
	                	break;
	                case 0:
	                    try {
	                        sleep(50);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }

	                    monitor.quitar(1);
	                    break;

	                case 2:
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
}
