
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Log implements Runnable{
      private String ruta = "D:\\salida.txt";
      private String contenido="";
      private String estadoBuff="";
      private String estadoCon="";


     Thread[] CPUS ;
    


    CPU_buffer buff1;
    CPU_buffer buff2;

    Log(CPU_buffer buffer1,CPU_buffer buffer2, Thread[] CPUS ){
        buff1=buffer1;
        buff2 =buffer2;
        this.CPUS = CPUS;
       


    }
    public void EscribirContenido() {
        estadoBuff=LocalDateTime.now()+ " - El buffer 1 tiene "+buff1.size()+" elementos";
        contenido= contenido + estadoBuff + "\r\n";
        estadoBuff=LocalDateTime.now()+ " - El buffer 2 tiene "+buff2.size()+" elementos";
        contenido= contenido + estadoBuff + "\r\n";

        for(int i=0; i<2; i++) {
            estadoCon = " - El estado del CPU "+(i+1)+" es " + CPUS[i].getState()+"\r\n";
            contenido = contenido + estadoCon;


        }
    }
    public void GuardarArchivo(){
        try {

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
            System.out.println("Contenido Guardado: ");
            System.out.println(contenido);
            System.out.println("Se ha guardado el txt con exito. Enhorabuena!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        for(int j=1; j<=100; j++) {
            this.EscribirContenido();
           // System.out.println("Se ha guardado en contenido " + j + " de 300 veces");
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.print("Process Failed");
            }
        }
        this.GuardarArchivo();

    }

}
