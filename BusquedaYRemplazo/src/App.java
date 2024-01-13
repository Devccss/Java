import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception{
        //Creamos las variables y definiciones
        try (Scanner texto = new Scanner(System.in)) {
            int encontrado=0, largoZ=0, largoX=0, largoY=0, empieza=0,contador,contadoraux,lnuevo=0;
            //Creamos la cadena Z
            System.out.println("Ingrese la cadena de texto original: ");
            String textoZ = texto.nextLine();
            char[] ctextoZ = textoZ.toCharArray();

            //Creamos la cadena x
            System.out.println("Ingrese la cadena que desea remplazar: ");
            String textoX = texto.nextLine();
            char[] ctextoX = textoX.toCharArray();

            

            //Vemos si la cadena x esta presente en la cadena z
            largoZ=ctextoZ.length;
            largoX=ctextoX.length;
            if(largoZ<largoX){
                System.out.println("No se encontro la cadena a remplazar");
            }else{
                contador=0;
                for(int i=0;i<largoZ-1;i++){
                    if(ctextoZ[i]==ctextoX[contador]){
                        if(contador==0){
                            empieza=i;
                        }
                        contador++;
                        encontrado=1;
                    }else{
                        contador=0;
                        encontrado=0;
                    }
                    if(contador==largoX){
                        //Lo hemos encontrado
                        i=largoZ;
                    }
                }
                if(encontrado==0){
                    //Si no emcontramos la cadena
                    System.out.println("No se encontro la cadena a remplazar");
                }else{

                    //Creamos la cadena y
                    System.out.println("Ingrese la cadena por la cual se intercambiara la de remplazo: ");
                    String textoY = texto.nextLine();
                    char[] ctextoY = textoY.toCharArray();

                    //obtenemos el largo
                    largoY=ctextoY.length;
                    lnuevo=largoZ-largoX+largoY;
                    char[] nuevo = new char[lnuevo];
                    contador=0;
                    contadoraux=0;
                    //Reemplazamos
                    for(int i=0;i<lnuevo;i++){
                        if(i<empieza){
                            nuevo[i]=ctextoZ[contador];
                            contador++;
                        }
                        if((i>=empieza)&&(i<=(empieza+largoY)-1)){
                            nuevo[i]=ctextoY[contadoraux];
                            contadoraux++;
                            if(contadoraux>=largoY){
                                encontrado=0;
                            }
                        }
                        if(encontrado==0){
                            encontrado=1;
                            contador=contador+largoX;
                        }
                        if(i>(empieza+largoY-1)){
                            nuevo[i]=ctextoZ[contador];
                            contador++;
                        }

                    }
                    System.out.println(" ");
                    System.out.print("La frase queda: ");
                    System.out.println(nuevo);
                }
            }
        }
    }
}
