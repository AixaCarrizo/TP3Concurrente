//cuando t14(T10) y t15(T11) son posibles, elige cual disparar
public class Politica {
	private CPU_buffer1 b1;
	private CPU_buffer2 b2;
	
    public Politica(CPU_buffer1 buffer1, CPU_buffer2 buffer2) {
    	buffer1=b1;
    	buffer2=b2;
	}

	public int prioridad() { //b1 = size buffer1 , b2 = size buffer 2

        if(b1.size()>b2.size()){
            return 2;
        }
        else if (b1.size()<b2.size()){
            return 1;
        }
        else {
            return 1;
        }
     }
}
