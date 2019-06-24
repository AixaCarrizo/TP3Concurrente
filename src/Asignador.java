
public class Asignador{
	private Monitor monitor;
	private CPU_buffer buffer1;
	private CPU_buffer buffer2;
	private String tarea;

	public Asignador(Monitor monitor, CPU_buffer buffer1, CPU_buffer buffer2) {
		this.buffer1=buffer1;
		this.monitor=monitor;
		this.buffer2=buffer2;
	}

public void setTarea(String tarea) {
	this.tarea=tarea;
}

public void asignar(String tarea) {

	if(monitor.shoot(10)==1) {
				buffer1.add(tarea);	
		    }
		
		    else {
		    	buffer2.add(tarea);
		    }
		}
}
