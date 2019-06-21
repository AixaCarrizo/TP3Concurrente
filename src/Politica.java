public class Politica {


    public int prioridad(int b1, int b2) { //b1 = size buffer1 , b2 = size buffer 2

       if(b1>b2){
           return 2;
       }
       else if (b1<b2){
           return 1;
       }
       else {
           return 1;
       }
    }
}
