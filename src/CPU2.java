
//hace de "consumidor" para el buffer1
public class CPU2 extends Thread{
	private int id;
	private Monitor monitor;

	public CPU2(int id, Monitor monitor){
		this.id = id;
		this.monitor = monitor;
	}

	@Override
	public void run() {
		super.run();

		while(true) {

			monitor.shoot(9);

			monitor.shoot(15);

			while(monitor.shoot(2) == -1){
				/**
				 * Si le devuelve un -1 significa que no pudó hacer el disparo y que tiene que volver a esperar un tiempo aleatorio para hacerlo
				 * Espera un tiempo aleatorio entre 5-25 milisegundos y vuelve a intentar.
				 * Y así hasta que consigue hacerlo dentro del rango deseado.
				 */

				try {
					long choose = (long) (Math.random()*25 +5);

					sleep(choose);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		monitor.shoot(6);

	}
}
