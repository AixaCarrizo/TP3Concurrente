
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {
	
	static Lock lock=new ReentrantLock();

	private final static Condition noTaskAvailable = lock.newCondition();
	private final static Condition notEmpty1 = lock.newCondition();
	private final static Condition notEmpty2 = lock.newCondition();
	private final static Condition  CPU1notON= lock.newCondition();
	private final static Condition  CPU2notON= lock.newCondition();

	private long timeCPU1, timeCPU2;

	private long wCPU1Up = 30;
	private long wCPU1Down = 15;
	private long wCPU2Up = 45;
	private long wCPU2Down = 10;

	private int contProd = 0;
    private int contCons=0;

    private PN pn = new PN();
    CPU_buffer buffer1;
    CPU_buffer buffer2;
    private Politica politica;

    public Monitor(CPU_buffer buffer1, CPU_buffer buffer2) {
         this.buffer1=buffer1;
         this.buffer2=buffer2;
         timeCPU1 = 0;
         timeCPU2 = 0;
         politica = new Politica(buffer1,buffer2);
    }
    

    public int shoot(int index){  //Dispara una transicion (index)
    	lock.lock();
    	
    	//PRODUCTOR
    	
    	if (index==0|| index==7) {//productor quiere producir
    		pn.isPos(index);
    		if(index==7) {
    			noTaskAvailable.signalAll();
    			System.out.println("pingo");
    		}
    		lock.unlock();
    		return 0;//no importa que devuelve, solo quiero que haga los disparos, y estos dos siempre los puede hacer secuencialmente
    	}
    	
    	
    	//ASIGNADOR
    	if(index==10|| index==11) {//asigna la tarea a un CPU u otro (T14 ó T15)
    		index=this.politica.prioridad();//elige el CPU	
    		if(index==2) {//si eligio el 2
    			pn.isPos(10);//dispara t14
		    	notEmpty2.signal();
    		}
    		else {
    			pn.isPos(11);//sino dispara t15
    	    	notEmpty1.signal();
    		}
			lock.unlock();
			return index; //y devuelve el nro de CPU
    	}
    	
    	
    	
    	//CPU1
    	if(index== 1)//CPU1 intenta apagarse
		{
			if ((pn.m[0]!=0)||(pn.m[2]!=0))//pero tiene tareas o en el buffer
			{
				if(windowsTimer(1)) { //Le deja disparar la transicion temporal ServiceRate1
					pn.isPos(5);
					pn.isPos(index);
					lock.unlock();
					return 0;
				}

				//Si llegó acá entonces es que no le dejó hacer el disparo.
				lock.unlock();
				return -1;
			}
			else {
				pn.isPos(index);//puede apagarse
				lock.unlock();
				return 0;
			}
		}
    	
    	//CPU2
    	if(index==2)//CPU2 intenta apagarse
    	{
			if ((pn.m[1]!=0)||(pn.m[3]!=0))//pero tiene tareas activas o en el buffer
			{
				if(windowsTimer(2)){ //Le deja disparar la transicion temporal ServiceRate1
					pn.isPos(6);
					pn.isPos(index);
					lock.unlock();
					return 0;
				}

				lock.unlock();
				return -1;
			}
			else {
				pn.isPos(index);//puede apagarse
				lock.unlock();
				return 0;
			}
		}
    	
    	//CPU1
    	if (index==12) {//CPU1 quiere pasar a atender una tarea (T2)
	    	if(pn.isPos(12)) {//si puede hacerlo, dispara transicion

	    		windowsTimer(1);	//Pudo hacerT T2, por lo que ahora hay un tocken en la plaza Active, tiene que empezar a contar para sensibilizar ServiceRate1

	    		lock.unlock();
	    		return 0;
	    	}
	    	else {//sino, espera a que se prenda el cpu1
	    		try {
					CPU1notON.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		lock.unlock();
	    	}
	    	return 0;
    	}
    	
     	//CPU2
    	if (index==15) {//CPU2 quiere pasar a atender una tarea
	    	if(pn.isPos(15)) {//si puede hacerlo, dispara transicion

	    		windowsTimer(2); //Pudo hacerT T8, por lo que ahora hay un tocken en la plaza Active, tiene que empezar a contar para sensibilizar ServiceRate2

	    		lock.unlock();
	    		return 0;
	    	}
	    	else {//sino, espera a que se prenda el cpu1
	    		try {
					CPU2notON.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		lock.unlock();
	    	}
	    	return 0;
    	}
    	
		//CPU1
    	if(index==14) {//CPU1 intenta prenderse

			if(pn.isPos(index)) {//si pudo disparar t6
				pn.isPos(3); //dispara power_up_delay
				pn.isPos(13);//y t5
				lock.unlock();
				return 1;
			}
			else {//si no pudo, no hay tareas disponibles
				try {
					noTaskAvailable.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lock.unlock();
	    		return 0;
			}
    	}
    	
    	
    	//CPU2
    	if(index==9) {//CPU2 intenta prenderse
    		if(pn.m[16]==0) {//si no hay token en stand_by_2, esta prendido
    		//	lock.unlock();
    		//	return 0;
    		}
    		else{
    			if(pn.isPos(index)) {//si pudo disparar t13
	    			pn.isPos(4); //dispara power_up_delay_2
	    			pn.isPos(8);//y t12
	    		}
    			else {//si no pudo, no hay tareas disponibles
    				try {
						noTaskAvailable.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    		lock.unlock();
    		return 0;
    	}


    	lock.unlock();
		return 0;	
  
    }

    public void quitar(int idBuffer){
//    	System.out.println(contCons);
    	lock.lock();
        contCons++;

        if(idBuffer==1) {
        	buffer1.remove();
        	pn.isPos(5);
        }
        else {
        	buffer2.remove();
        	pn.isPos(6);
        }
       
        lock.unlock();
    }
    public boolean windowsTimer(int select){


    	//Esta primera parte solo es para cuando se sencibiliza por primera vez y empieza a contar.
    	if(select ==1){

			if(timeCPU1==0) {
				timeCPU1 = System.currentTimeMillis();
				return false;	////En realidad no importa
			}

		} else {
			if(timeCPU2==0) {
				timeCPU2 = System.currentTimeMillis();
				return false;	//En realidad no importa
			}
		}

    	//A partir de acá es cuando ya empieza a intentar disparar la transicion después de sencibilizarla.

		long auxtime = System.currentTimeMillis();	//Tomo el tiempo acutal del sistema.

    	long time = 0;

		if(select == 1) time = auxtime - timeCPU1;	//Calculo el tiempo transcurrid con respecto al que tomo cuando se sencibilizo.
		else time = auxtime - timeCPU2;

		if(select==1)
			if(wCPU1Down<time && time<wCPU1Up){	//Si esta dentro del rango ...
				timeCPU1 = 0;
				return  true;
			}else{	//Si no ...
				timeCPU1 = auxtime;
				return false;
			}
		else
			if(wCPU2Down<time && time<wCPU2Up){	//Lo mismo pero para la CPU2
				timeCPU2 = 0;
				return true;
			}else{
				timeCPU2 = auxtime;
				return false;
			}
	}
}
