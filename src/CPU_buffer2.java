import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class CPU_buffer2 {


	    private Queue<String> cola;

	    public CPU_buffer2() {

	        cola = new ConcurrentLinkedQueue<>();
	    }

	    public void add(String dato) {
	        cola.add(dato);
	    }

	    public String remove() {
	        return cola.remove();
	    }

	    public  int size() {
	        return cola.size();
	    }
}
