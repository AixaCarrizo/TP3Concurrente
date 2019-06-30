
public class PN {

    int[]m;
    int []q;
    int []B;
    int [] sensib;
    int[][] w;
    int [][] h;



    public PN(int[] m, int[][] w, int[] s, int[][]h, int[] B) {
        this.m = m;
        this.w = w;
        this.h = h;
        this.B = B;

    }

    public  PN(){
        init();
    }

    public void init(){

        this.m = new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1}; //Vector de marcado inicial
        this.q = new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1}; //qi = cero ( M ( pi ) ), 0 si M(pi) != 0, en otro caso, 1
        /**
         * m0: Active
         * m1: Active_2
         * m2: CPU_buffer1
         * m3: CPU_buffer2
         * m4: CPU_ON
         * m5: CPU_ON_2
         * m6: Idle
         * m7: Idle_2
         * m8: P0
         * m9: P1
         * m10:P13
         * m11:P16
         * m12:P6
         * m13:Power_up
         * m14:Power_up_2
         * m15:Stand_by
         * m16:Stand_by2
         */


        /**
         * T0: Arrival_rate
         * T1: Power_down_threshold
         * T2: Power_down_threshold_2
         * T3: Power_up_delay
         * T4: Power_up_delay_2
         * T5: Service_Rate
         * T6: Service_Rate_2
         * T7: t1
         * T8: t12
         * T9: t13
         * T10:t14
         * T11:t15
         * T12:t2
         * T13:t5
         * T14:t6
         * T15:t8
         */

        this.w = new int[][]{{0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0},		// 0	Active
                             {0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  1},		// 1	Active_2
                             {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1, -1,  0,  0,  0},		// 2	CPU_buffer1
                             {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0, -1},		// 3	CPU_buffer2
                             {0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 4	CPU_ON
                             {0,  0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 5	CPU_ON_2
                             {0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0},		// 6	Idle
                             {0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0, -1},		// 7	Idle_2
                             {-1, 0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0},		// 8	P0
                             {1,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0},		// 9	P1
                             {0,  0,  0,  0, -1,  0,  0,  1, -1,  0,  0,  0,  0,  0,  0,  0},		// 10	P13
                             {0,  0,  0,  0,  0,  0,  0,  1,  0,  0, -1, -1,  0,  0,  0,  0},		// 11	P16
                             {0,  0,  0, -1,  0,  0,  0,  1,  0,  0,  0,  0,  0, -1,  0,  0},		// 12	P6
                             {0,  0,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0},		// 13	Power_up
                             {0,  0,  0,  0, -1,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0},		// 14	Power_up_2
                             {0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0},		// 15	Stand_by
                             {0,  0,  1,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0}};		// 16	Stand_by_2
                             
                             
        this.h = new int[][]{{0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 0	Active
           					 {0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 1	Active_2
           					 {0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 2	CPU_buffer1
           					 {0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 3	CPU_buffer2
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 4	CPU_ON
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 5	CPU_ON_2
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 6	Idle
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 7	Idle_2
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 8	P0
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 9	P1
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 10	P13
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 11	P16
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 12	P6
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 13	Power_up
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 14	Power_up_2
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 15	Stand_by
           					 {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}};		// 16	Stand_by_2
           				
       this.B = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
       
       
       calcularB();
       
       for(int i = 0; i <16; i++) {
           sensib[i] = this.m[i] * this.B[i];	
           }
       
        
    }
    
    public void calcularB() {
    	int acum=0;   					 
        for (int i=0; i < 16; i++) {
        	for(int j=0; j <17; i++) {
        		acum = acum + ( h[i][j] * q[i]);
        	}
        	this.B[i]=acum;
        	acum = 0;
        }
    }
    
    

    public boolean isPos(int index) {   //Mediante la ecuacion de la PN devuelve un boolean que indica si se puede disparar la transicion

        int[] mPrima = new int[m.length];
        
        if(this.sensib[index] == 0) { //si la trans no esta sensibilizada, devuelve false, sino, calcula si puede hacer la transic.	
        	return false;
        }

        for (int i = 0; i < m.length; i++) {   //Si algun numero del nuevo vector de marcado es negativo, no puedo dispararla
            mPrima[i] = m[i] + w[i][index];    //Sumo para obtener el nuevo vector de marcado

            if (mPrima[i] == -1) return false;
        }

        this.m=mPrima;
        for(int i=0; i<this.q.length; i++) {
        	if(this.m[i] == 0) {
        		this.q[i] = 0; 
        	}
        	else {
        		this.q[i] = 1;
        	}
        }
        
        calcularB();
        
        for(int i = 0; i <16; i++) {
        sensib[i] = this.m[i] * this.B[i];	
        }
        return true;   //Si ninguno es negativo, puedo dispararla
    }
}