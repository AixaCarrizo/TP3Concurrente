
public class PN {

    int[]m;
    int[][] w;
    int[] S;

    public PN(int[] m, int[][] w, int[] s) {
        this.m = m;
        this.w = w;
        S = s;
    }

    public  PN(){
        init();
    }

    public void init(){

        this.m = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0}; //Vector de marcado inicial

        /**
         * m0: Active
         * m1: Active_2
         * m2: CPU_Buffer1
         * m3: CPU_ON
         * m4: CPU_ON_2
         * m5: Idle
         * m6: Idle_2
         * m7: P0
         * m8: P1
         * m9: Power_up2 (P12)
         * m10: P13
         * m11: P6
         * m12: Power_up
         * m13: Stand_by
         * m14: Stand_by_2
         * m15: CPU_buffer2
         */

        this.S = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //Vector de disparo

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
         * T10:t2
         * T11:t5
         * T12:t6
         * T13: t8
         */

        this.w = new int[][]{{0 , 0,  0,  0,  0, -1,  0,  0,  0,  0,  1,  0,  0,  0},		// 0    Active
                             {0 , 0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0 , 1},		// 1    Active_2
                             {0 , 0,  0,  0,  0,  0,  0,  1,  0,  0, -1,  0,  0,  0},		// 2    CPU_Buffer1
                             {0 ,-1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 3    CPU_ON
                             {0 , 0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0},		// 4    CPU_ON_2
                             {0 , 0,  0,  0,  0,  1,  0,  0,  0,  0, -1,  0,  0,  0},		// 5    Idle
                             {0 , 0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0, -1},		// 6    Idle_2
                             {-1, 0,  1,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0},		// 7    P0
                             {1 , 0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0,  0,  0},		// 8    P1
                             {0 , 0,  0,  0, -1,  0,  0,  0,  0,  1,  0,  0,  0,  0},		// 9    Power_up_2
                             {0 , 0,  0,  0, -1,  0,  0,  1, -1,  0,  0,  0,  0,  0},		// 10   P13
                             {0 , 0,  0, -1,  0,  0,  0,  1,  0,  0,  0, -1,  0,  0},		// 11   P6
                             {0 , 0,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0},		// 12   Power_up
                             {0 , 1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  0},		// 13   Stand_by
                             {0 , 0,  0,  0,  0,  0,  0,  0,  0, -1,  0,  0,  0,  0},		// 14   Stand_by_2
                             {0 , 0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0, -1}};		// 15   CPU_Buffer2
    }

    public boolean isPos(int index) {   //Mediante la ecuacion de la PN devuelve un boolean que indica si se puede disparar la transicion

        this.S = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        S[index] = 1;

        int[] mPrima = new int[m.length];

        for (int i = 0; i < m.length; i++) {   //Si algun numero del nuevo vector de marcado es negativo, no puedo dispararla
            mPrima[i] = m[i] + w[i][index];    //Sumo para obtener el nuevo vector de marcado

            if (mPrima[i] == -1) return false;
        }

        this.m=mPrima;

        return true;   //Si ninguno es negativo, puedo dispararla
    }
}