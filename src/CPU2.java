
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



			switch (monitor.shoot(8)) //Intento diaparar T12
			{
				case 8: //Si pude, intento procesar
					monitor.shoot(15);
					proceso();
					break;
				case 9: //Si no pude disparar T12, intento con T13
					monitor.shoot(9);
					monitor.shoot(4); //Si pude,disparo Power-up delay 2. Siempre puede.
					monitor.shoot(15); //Intento procesar
					proceso();
					break;

				case 6: // dispara T12
					monitor.shoot(8);

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
		monitor.shoot(6);

	}
}
