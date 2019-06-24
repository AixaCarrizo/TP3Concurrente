//cuando t14(T10) y t15(T11) son posibles, elige cual disparar
public class Politica {
	private CPU_buffer b1;
	private CPU_buffer b2;
	
    public Politica(CPU_buffer buffer1, CPU_buffer buffer2) {
    	b1 = buffer1;
    	b2 = buffer2;
	}

	public int prioridad() { //b1 = size buffer1 , b2 = size buffer 2

        if(b1.size()>b2.size()){//si hay mas tareas en el buffer1 que en el 2
            return 2;//usa el 2
        }
        else{
            return 1;//sino usa el 1
        }
     }
}
