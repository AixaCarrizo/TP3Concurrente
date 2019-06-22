
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


	            
	            switch (monitor.shoot(13)) //Intento diaparar T5
				{
	                case 13: //Si pude, intento procesar
	                	monitor.shoot(12)
						proceso();
	                	break;
	                case 14: //Si no pude disparar T5, intento con T6
						monitor.shoot(14);
						monitor.shoot(3); //Si pude,disparo Power-up delay. Siempre puede.
						monitor.shoot(12); //Intento procesar
						proceso();
	                    break;

	                case 6: // dispara T5
	                	monitor.shoot(13);

	                	break;

	                case 5:
	                    monitor.shoot(13);
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
