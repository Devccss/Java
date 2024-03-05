import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {

        String serverAddress = "LocalHost"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto en el que el servidor escucha
        //Tablero
        char[][] tablero = new char[3][3];

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            // Flujos de entrada y salida para enviar y recibir datos al/del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            DataInput input = new DataInputStream(socket.getInputStream());

            Scanner scan = new Scanner(System.in);

            //leer mensajes antes de comenzar
            String serverMsg,clientMsg;
            char termino,fallo,turno;
            for(int i=0;i<2;i++){ //A1 A2
                serverMsg = in.readLine();
                System.out.println(serverMsg);
            }
            if((turno=input.readChar())==1){//A3
                System.out.println("Comienza el Servidor");
            }else{
                System.out.println("Comienza el cliente");
            }

            
            // Recibir y mostrar la respuesta del servidor
            
            while(true){
                //Ler en que turno estamos 
                turno = input.readChar(); //A3.1 

                //Turno Servidor
                if(turno == '1'){
                    serverMsg = in.readLine();//A3.2
                    
                    System.out.println(serverMsg);

                    //Verificar si elijio posicion valida
                    fallo = input.readChar();//A3.3

                    if(fallo== 'S'){
                        for(int i=0; i<3;i++){
                            for(int j=0;j<3;j++){
                                tablero[i][j] = input.readChar(); //A4
                                System.out.print("["+tablero[i][j]+"]");
                            }
                            System.out.println(" ");
                        }

                        //Verficar si termino la partida
                        termino = input.readChar();
                        if(termino!='$'){
                            serverMsg = in.readLine();
                            System.out.println(serverMsg);
                            break;
                        }
                    }
                }else{
                    //Turno Cliente
                    serverMsg = in.readLine(); //A5
                    System.out.println(serverMsg);
                    clientMsg = scan.nextLine(); //scaner
                    out.println(clientMsg); //B1
                    
                    //Verificar si elijio posicion valida
                    fallo = input.readChar();//A6
                    
                    if(fallo == 'S'){ 
                        
                        for(int i=0; i<3;i++){
                            for(int j=0;j<3;j++){
                                tablero[i][j] = input.readChar(); //A7
                                System.out.print("["+tablero[i][j]+"]");
                            }
                            System.out.println(" ");
                        }

                        //Verficar si termino la partida
                        termino = input.readChar();
                        if(termino!='$'){
                            serverMsg = in.readLine();
                            System.out.println(serverMsg);
                            break;
                        }
                    }else{
                        serverMsg = in.readLine();
                        System.out.println(serverMsg);//A6.1 
                    }
                    
                    
                }
              
            }            

            // Cierra conexiones
            in.close();
            out.close();
            scan.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}