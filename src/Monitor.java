

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {
	
	static Lock lock=new ReentrantLock();
	private final static Condition notFull1 = lock.newCondition();
	private final static Condition notEmpty1 = lock.newCondition();
	private final static Condition notFull2 = lock.newCondition();
	private final static Condition notEmpty2 = lock.newCondition();
    private Politica polis = new Politica();
    private int contProd = 0;
    private int contCons=0;
    private PN pn = new PN();
    CPU_buffer1 buffer1;
    CPU_buffer2 buffer2;
    
    public Monitor(CPU_buffer1 buffer1, CPU_buffer2 buffer2) {
         this.buffer1=buffer1;
         this.buffer2=buffer2;
    }

    public int shoot(int index){  //Dispara una transicion (index)
    	lock.lock();
        while (!pn.isPos(index)) {
        	
//          System.out.println("asdasd" + contCons);
            if (contProd == 480 && contCons == 480) { //Esto solo le interesa al Consumidor. El Productor muere solo.
            	notEmpty1.signalAll();
            	notEmpty2.signalAll();
                lock.unlock();
                return -1;
            }

            switch (index) {

                case 0:
                    if (pn.isPos(3)) {
                    	lock.unlock();
                    	return 3;
                    }
                    else {
                    	try {
							notEmpty2.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}                    	
                    }
                    break;

                case 3:
                    if (pn.isPos(0)) {
                    	lock.unlock();
                    	return 0;
                    }
                    else {
                    	try {
							notEmpty1.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
                    	}
                    }
                	break;

                case 1:
                    if (pn.isPos(2)) {
                    	lock.unlock();
                    	return 2;
                    }
                    else {
            			try {
        					notFull2.await();
        				} catch (InterruptedException e2) {
        					// TODO Auto-generated catch block
        					e2.printStackTrace();
        				}
                    }
                    break;

                case 2:
                	
                    if (pn.isPos(1)) {
                    	lock.unlock();
                    	return 1;
                    }
					else {
						try {
							notFull1.await();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

                    break;
            }
        }

        lock.unlock();
        return index; //Logro agregar en el buffer que intentó incialmente
    }

    public void agregar(int idBuffer, String dato){
    	//System.out.println(contProd);
    	lock.lock();
       contProd++;
       int decision = polis.prioridad(buffer1.size(),buffer2.size());
        if(idBuffer==1 || decision == 1) {
        	buffer1.add(dato);
        	pn.isPos(6);
        	notEmpty1.signal();
        }

        else {
            if(decision == 2) {
                buffer2.add(dato);
                pn.isPos(7);
                notEmpty2.signal();
            }
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