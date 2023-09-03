/*
 * Curso java basico
 */
package pkg24usocket;

/**
 *
 * @author Homzode
 */
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Main {
    public static boolean online = true;
    public static ServerSocket htmlSocket;
    public static Socket connectionSocket;
    public static String clientSentence;
    public static String htmlserver;
    public static InetAddress serverIp;
    
    /**
     * @param args desde lalinea de comandos
     */
    public static void main(String[] args) {
        serverIp = getServerIPs();
        serverGps();
    }
    public static void serverGps(){
        try{
            int portServer = 7000;
            int maxClients = 5;
            System.out.println("Cargando Servidor: "+serverIp+":"+portServer+" max: "+maxClients);
            htmlSocket = new ServerSocket(portServer, maxClients, serverIp);
            if(htmlSocket.isBound()){
                System.out.println("*****Servidor activo");
                htmlserver = "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "    <head>\n" +
                                "        <title>Example</title>\n" +
                                "    </head>\n" +
                                "    <body>\n" +
                                "        <script>\n" +
                                "            alert('Hola');\n" +
                                "        </script>\n" +
                                "		<p>Ejemplo de pagina enviada por Java ServerSocket</p>\n" +
                                "		<img src=\"http://192.168.10.60:8181/fill_movil.jpg" +
                                "\" alt=\"24U image\" style=\"width:500px;height:400px;\">\n" +
                                "    </body>\n" +
                                "</html>";
            }else{
                online = false;
                System.out.println("*****Servidor fuera de linea: "+online);
            }
            while (online) {
                System.out.println("*****Servidor atendiendo en linea: "+online);
                connectionSocket = htmlSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("**********Recibido: " + clientSentence);

                //if(clientSentence.contains("Request location")){
                if(clientSentence.contains("GET")){    
                    PrintWriter writer = new PrintWriter(connectionSocket.getOutputStream());
                    String response = htmlserver;
                    //writer.println(response);
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/html");
                    writer.println("Content-Length: " + response.length());
                    writer.println();
                    writer.println(response);
                    writer.flush();
                }else if(clientSentence.contains("Response location")){
                    System.out.println("**********Respuesta no implementada: " + clientSentence);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static InetAddress getServerIPs(){
        InetAddress inAd = null;
        try{
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            NetworkInterface nint;
            while(e.hasMoreElements()) {
                nint = (NetworkInterface) e.nextElement();
                Enumeration nintip = nint.getInetAddresses();
                while (nintip.hasMoreElements()) {
                   inAd = (InetAddress) nintip.nextElement();
                   System.out.println(inAd.getHostAddress());
                   if( inAd.getHostAddress().indexOf("192.168") != -1 ){
                       System.out.println("find: "+ inAd.getHostAddress() );
                       return inAd;
                   }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return inAd;
    }
}
