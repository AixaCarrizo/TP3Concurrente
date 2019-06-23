
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

	        	monitor.shoot(14);

				monitor.shoot(12);

				while(monitor.shoot(1) == -1){
					/**
					 * Si le devuelve un -1 significa que no pudó hacer el disparo y que tiene que volver a esperar un tiempo aleatorio para hacerlo
					 * Espera un tiempo aleatorio entre 10-50 milisegundos y vuelve a intentar.
					 * Y así hasta que consigue hacerlo dentro del rango deseado.
					 */

					try {
						long choose = (long) (Math.random()*50 +10);

						sleep(choose);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

	        }
	    }
}
