import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GatoServidor {
    public static void main(String[] args) throws Exception {

        int portNumber = 12345;
        Scanner scan = new Scanner(System.in);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Esperando la conexion del Jugador en el puerto " + portNumber + "...");

            Socket jugadorSocket = serverSocket.accept();
            System.out.println("Jugador conectado desde: " + jugadorSocket.getInetAddress());

            // Flujo de entrada y salida para recibir datos del Jugador 
            BufferedReader jugadorIn = new BufferedReader(new InputStreamReader(jugadorSocket.getInputStream()));
            PrintWriter jugadorOut = new PrintWriter(jugadorSocket.getOutputStream(), true);
            DataOutput outputData = new DataOutputStream(jugadorSocket.getOutputStream());

            jugadorOut.println("Conectado ");// A1
            //Tablero
            char[][] tablero = new char[3][3];
            inicializarTablero(tablero);

            //Indicar quien comienza
            jugadorOut.println("Espere un momento el Servidor elejira quien comienza...");// A2
            System.out.println("Ingrese 1 para que el Servidor comience jugando");
            System.out.println("Ingrese 2 para que el Cliente comience jugando");
            System.out.print("-> ");
            int op = scan.nextInt();
            while (op<1 | op>2 ){
                System.out.print("-> ");
                op = scan.nextInt();
            }
            if(op==1){
                System.out.println("Comienza el Servidor");
                outputData.writeChar(1);// A3
            }else{
                System.out.println("Comienza el Cliente");
                outputData.writeChar(2);// A3
            }
            char simbolo='X';
            while (true) {
                // Turno 
                jugar(tablero, jugadorIn, jugadorOut,simbolo,outputData,op);
                
                // Verifica si el Servidor ganó o si hay un empate
                if (verificarGanador(tablero, 'X')) {
                    outputData.writeChar('^');
                    mostrarTablero(tablero);
                    System.out.println("¡Felicidades, eres el ganador!");
                    jugadorOut.println("Lo siento, el Servidor ha ganado.");
                    break;
                }else if (tableroCompleto(tablero)) {
                    outputData.writeChar('^');
                    mostrarTablero(tablero);
                    enviarTablero(jugadorOut, tablero, outputData);
                    System.out.println("Empate");
                    jugadorOut.println("Empate");
                    break;
                }else if (verificarGanador(tablero, 'O')) { // Verifica si el cliente ganó o si hay un empate
                    outputData.writeChar('^');
                    mostrarTablero(tablero);
                    jugadorOut.println("¡Felicidades, eres el ganador!");
                    System.out.println("Lo siento, el cliente ha ganado.");
                    break;
                }else{
                    outputData.writeChar('$');
                }
                if(simbolo=='X'){
                    simbolo='O';
                }else{
                    simbolo='X';
                }
                if(op==1){
                    
                    op=2;
                }else{
                    op=1;
                    
                }
            }
                

            // Cierre recursos
            jugadorIn.close();
            jugadorOut.close();
            jugadorSocket.close();
            serverSocket.close();
            scan.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enviarTablero(PrintWriter out, char[][] tablero,DataOutput outData)throws IOException {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                outData.writeChar(tablero[i][j]);//A4
            }
        }
    }

    public static void jugar(char[][] tablero, BufferedReader in, PrintWriter out, char jugador,DataOutput outData, int aux) throws IOException {
        
        char turno = Integer.toString(aux).charAt(0);
        outData.writeChar(turno); //A3.1
        Scanner scan = new Scanner(System.in);
        if(aux==1){
            out.println("Turno del Servidor"); //A3.2
            System.out.println("Turno del Jugador " + jugador + ". Ingresa la fila y columna (ejemplo: 1,2):");
            String corServer = scan.nextLine();
            String[] coordenadas = corServer.split(",");
            int fila = Integer.parseInt(coordenadas[0]);
            int columna = Integer.parseInt(coordenadas[1]);

            if(fila<3 && fila>-1 && columna<3 && columna>-1){
                if (tablero[fila][columna] == ' ' ) {
                    tablero[fila][columna] = jugador;
                    outData.writeChar('S');//A3.3
                    mostrarTablero(tablero);
                    enviarTablero(out, tablero,outData); //A4
                }
            } else {
                outData.writeChar('#');//A3.3
                System.out.println("Posicion invalida. Inténtalo de nuevo");
                jugar(tablero, in, out, jugador,outData,aux);
            }
            
        }else{
            System.out.println("Turno del Cliente");
            out.println("Turno del Jugador " + jugador + ". Ingresa la fila y columna (ejemplo: 1,2):"); //A5
            String entrada = in.readLine(); //B1
            String[] coordenadas = entrada.split(",");
            int fila = Integer.parseInt(coordenadas[0]);
            int columna = Integer.parseInt(coordenadas[1]);

            if (tablero[fila][columna] == ' ') {
                tablero[fila][columna] = jugador;
                outData.writeChar('S');//A6
                mostrarTablero(tablero);
                enviarTablero(out, tablero,outData);//A7
            } else {
                outData.writeChar('#');//A6
                out.println("La casilla ya está ocupada. Inténtalo de nuevo");//A6.1
                jugar(tablero, in, out, jugador,outData,aux);
            }
            
        }
        
        
    }

    public static boolean tableroCompleto(char[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean verificarGanador(char[][] tablero, char c) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == c && tablero[i][1] == c && tablero[i][2] == c) {
                return true; // Verifica filas
            }
            if (tablero[0][i] == c && tablero[1][i] == c && tablero[2][i] == c) {
                return true; // Verifica columnas
            }
        }
        if (tablero[0][0] == c && tablero[1][1] == c && tablero[2][2] == c) {
            return true; // Verifica diagonal principal
        }
        if (tablero[0][2] == c && tablero[1][1] == c && tablero[2][0] == c) {
            return true; // Verifica diagonal secundaria
        }
        return false;
    }

    public static void mostrarTablero(char[][] tablero) {
        System.out.println("Tablero:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("[" + tablero[i][j] + "]");
            }
            System.out.println();
        }
    }

    public static char[][] inicializarTablero(char[][] tablero) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
            }
        }
        return tablero;
    }
}
