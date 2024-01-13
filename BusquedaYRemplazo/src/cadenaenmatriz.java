//Librerias
import java.util.Scanner;

//Programa
public class cadenaenmatriz {

    //Programa Main
    public static void main(String[] args){
        try (//Escaneo de la pabra y guardarla en un string
        Scanner palabra = new Scanner(System.in)) {
            char[][] matriz = new char[5][5];
            System.out.print("Ingrese una oracion: ");
            String texto = palabra.nextLine();
            System.out.println(" ");
            //Guardamos en caracteres el string
            char [] cpalabra = texto.toCharArray();

            //Comenzamos a guardar los caracteres en la matriz
            int contador=0,largo;
            largo=cpalabra.length;
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    matriz[i][j]= '0';
                }
            }

            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(matriz[i][j]=='0'){
                        if(cpalabra[contador]!= 32){
                        //En el caso de que la matriz esta vacia
                            matriz[i][j]=cpalabra[contador];
                            contador++;
                            i=0;
                            j=-1;
                        }else{
                            contador++;
                            i=0;
                            j=-1;
                        }
                    }else{
                        //Caso de que la matris tenga el mismo caracter
                        if(matriz[i][j]==cpalabra[contador]){
                            contador++;
                            i=0;
                            j=-1;
                        }
                    }
                    //Si los caracteres de la palanbra se terminan, salimos de la matriz
                    if(contador==largo){
                        i=5;
                        j=5;
                    }
                }
            }
            //La impresion de la matriz
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(matriz[i][j]!='0'){
                        System.out.print("["+matriz[i][j]+"]");
                    }else{
                        System.out.print("[ ]");
                    }
                }
                System.out.println(" ");
            }
            //Encriptacion de la frase siguiente
            //Si se encuentra la letra se cambia por la de la derechera
            //Si no se mantiene
            System.out.print("Ingrese la palabra: ");
            String orig = palabra.nextLine();
            char []codif = orig.toCharArray();
            int largocod=0;
            largocod=codif.length;

            //Comenzamos a modificar
            for(int i=0;i<largocod;i++){
            
                for(int j=0;j<5;j++){
                    for(int k=0;k<5;k++){
                        if(matriz[j][k]==codif[i]){
                            if(k+1<4){
                                codif[i]=matriz[j][k+1];
                            }else{
                                codif[i]=matriz[j+1][0];
                            }
                        }
                    }
                }
            }
            System.out.println("Palabra codificada: ");
            for(int i=0;i<largocod;i++){
                System.out.print(codif[i]);
            }
        }
    }
    
}
