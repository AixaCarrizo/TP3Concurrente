

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {
	
	static Lock lock=new ReentrantLock();
	private final static Condition notFull1 = lock.newCondition();
	private final static Condition notEmpty1 = lock.newCondition();
	private final static Condition notFull2 = lock.newCondition();
	private final static Condition notEmpty2 = lock.newCondition();
	
    private int contProd = 0;
    private int contCons=0;
    private PN pn = new PN();
    CPU_buffer1 buffer1;
    CPU_buffer2 buffer2;
    private Politica politica= new Politica(buffer1, buffer2);
    
    public Monitor(CPU_buffer1 buffer1, CPU_buffer2 buffer2) {
         this.buffer1=buffer1;
         this.buffer2=buffer2;
    }

    public int shoot(int index){  //Dispara una transicion (index)
    	lock.lock();
    	if(index==10|| index==11) {
    		lock.unlock();
    		return this.politica.prioridad();
    	}

    	if(index== 1)
		{
			if ((pn.m[0]!=0)&&(pn.m[2]!=0))
			{
				return 6;
			}
		}
    	if (index==0|| index==7) {
    		pn.isPos(index);
    		lock.unlock();
    		return 0;//no importa que devuelve, solo quiero que haga los disparos, y estos dos siempre los puede hacer secuencialmente
    	}
    	
    	
        while (!pn.isPos(index)) {
        	
            if (contProd == 480 && contCons == 480) { //Esto solo le interesa al Consumidor. El Productor muere solo.
            	notEmpty1.signalAll();
            	notEmpty2.signalAll();
                lock.unlock();
                return -1;
            }

            switch (index) {



                case 13:

                    	lock.unlock();
                    	return 14;




                case 14:
                	lock.unlock();
                	return 5;




				case 12: //No pude procesar
				{
					return 1;
				}
        }

        lock.unlock();
        return index; //Logro agregar en el buffer que intentó incialmente
    }

    public void agregar(int idBuffer, String dato){
    	//System.out.println(contProd);
    	lock.lock();
       contProd++;

        if(idBuffer==1) {
        	buffer1.add(dato);
        	pn.isPos(6);
        	notEmpty1.signal();
        }

        else {
        	buffer2.add(dato);
        	pn.isPos(7);
        	notEmpty2.signal();
        }
        lock.unlock();
    }

    public void quitar(int idBuffer){
//    	System.out.println(contCons);
    	lock.lock();
        contCons++;

        if(idBuffer==1) {
        	buffer1.remove();
        	pn.isPos(5);
        	notFull1.signal();
        }
        else {
        	buffer2.remove();
        	pn.isPos(4);
        	notFull2.signal();
        }
       
        lock.unlock();
    }
}