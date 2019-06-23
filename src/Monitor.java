

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
    private int contProd = 0;
    private int contCons=0;
    private PN pn = new PN();
    CPU_buffer1 buffer1;
    CPU_buffer2 buffer2;
    private Politica politica;

    public Monitor(CPU_buffer1 buffer1, CPU_buffer2 buffer2) {
         this.buffer1=buffer1;
         this.buffer2=buffer2;
         politica = new Politica(buffer1,buffer2);
    }
    

    public int shoot(int index){  //Dispara una transicion (index)
    	lock.lock();
    	
    	//PRODUCTOR
    	
    	if (index==0|| index==7) {//productor quiere producir
    		pn.isPos(index);
    		if(index==7) {
    			noTaskAvailable.signalAll();
    		}
    		lock.unlock();
    		return 0;//no importa que devuelve, solo quiero que haga los disparos, y estos dos siempre los puede hacer secuencialmente
    	}
    	
    	
    	//ASIGNADOR
    	if(index==10|| index==11) {//asigna la tarea a un CPU u otro
    		index=this.politica.prioridad();//elige el CPU		
    		lock.unlock();
    		if(index==2) {//si eligio el 2
    			pn.isPos(10);//dispara t14
		    	notEmpty2.signal();
    		}
    		else {
    			pn.isPos(11);//sino dispara t15
    	    	notEmpty1.signal();
    		}
    		return index; //y devuelve el nro de CPU
    	}
    	
    	
    	
    	//CPU
    	if(index== 1)//CPU1 intenta apagarse
		{
			if ((pn.m[0]!=0)||(pn.m[2]!=0))//pero tiene tareas o en el buffer
			{
				pn.isPos(6);//entonces pasa a atender la tarea activa
				lock.unlock();
				return 0;
			}
			else {
				pn.isPos(index);//puede apagarse
				lock.unlock();
				return 0;
			}
		}
    	
    	//CPU
    	if(index==2)//CPU2 intenta apagarse
    	{
			if ((pn.m[1]!=0)||(pn.m[3]!=0))//pero tiene tareas activas o en el buffer
			{
				pn.isPos(6);//atiende tarea activa
				lock.unlock();
				return 0;
			}
			else {
				pn.isPos(index);//puede apagarse
				lock.unlock();
				return 0;
			}
		}
    	
    	//CPU
    	if (index==12) {//CPU1 quiere pasar a atender una tarea
	    	if(pn.isPos(12)) {//si puede hacerlo, dispara transicion
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
    	
     	//CPU
    	if (index==15) {//CPU2 quiere pasar a atender una tarea
	    	if(pn.isPos(15)) {//si puede hacerlo, dispara transicion
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
    	
    	//CPU
    	if(index==14) {//CPU1 intenta prenderse
    		if(pn.m[15]==0) {//si no hay token en stand_by, esta prendido
    		//	lock.unlock();
    		//	return 0;
    		}
    		else{
    			if(pn.isPos(index)) {//si pudo disparar t6
	    			pn.isPos(3); //dispara power_up_delay
	    			pn.isPos(13);//y t5
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
    	
    	
    	//CPU
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


}
