/*
 * Curso java basico
 */
package pkg24usocket;
/**
 *
 * @author Homzode
 */
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.net.ssl.SSLServerSocket;

public class Main {
    private static ServerHomzode svh;
    private static Thread thread;
    
    /**
     * @param args desde lalinea de comandos
     */
    public static void main(String[] args) {
        try {
            System.out.println("Cargando Main");
            svh = new ServerHomzode();
            thread = new Thread(){
                public void run(){
                    svh.runPyServer();
                }
            };
            thread.start();
            svh.serverHomzode();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}

class ServerHomzode{
    public boolean online = true;
    public ServerSocket htmlSocket;
    public Socket connectionSocket;
    public String clientSentence;
    public String htmlserver;
    public InetAddress serverIp;
    public InputStream ins;
    public PrintWriter writer;
    public URL uimage;
    
    /**
    * Clase que inicia el ServerSocket
    */
    public void serverHomzode(){
        System.out.println("Cargando constructor");
        try{
            clientSentence = "Unknown";
            serverIp = getServerIPs();
            int portServer = 7000;
            int maxClients = 5;
            System.out.println("Cargando Servidor: "+serverIp+":"+portServer+" max: "+maxClients);
            htmlSocket = new ServerSocket(portServer, maxClients, serverIp);
            if(htmlSocket.isBound()){
                System.out.println("*****Servidor activo");
                System.out.println("From browser: http://"+serverIp+":"+portServer+"/home" );
                ins =  getClass().getClassLoader().getResourceAsStream("resources/embedpage.html");
                htmlserver = new BufferedReader(new InputStreamReader(ins)).lines().collect(Collectors.joining("\n"));
            }else{
                online = false;
                System.out.println("*****Servidor fuera de linea: "+online);
            }
            while (online) {
                System.out.println("*****Servidor atendiendo en linea: "+online);
                connectionSocket = htmlSocket.accept();
                writer = new PrintWriter(connectionSocket.getOutputStream());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("**********Recibido: " + clientSentence);
                if(clientSentence == null){
                    clientSentence = "failed";
                }
                if(clientSentence.contains("home")){    
                    String response = htmlserver;
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/html");
                    writer.println("Content-Length: " + response.length());
                    writer.println();
                    writer.println(response);
                    writer.flush();
                }else{
                    System.out.println("**********Respuesta no implementada: " + clientSentence);
                    String response = "<!DOCTYPE html>\n" +
                                    "<html>\n" +
                                    "    <head>\n" +
                                    "        <title>Example</title>\n" +
                                    "    </head>\n" +
                                    "    <body>\n" +
                                    "		<p>400 Bad request  try /home</p>\n" +
                                    "    </body>\n" +
                                    "</html>";
                    writer.println("HTTP/1.1 400 Bad request");
                    writer.println("Content-Type: text/html");
                    writer.println("Content-Length: " + response.length());
                    writer.println();
                    writer.println(response);
                    writer.flush();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Metodo que obtiene la IP local del sistema
     * @return 
     */
    public InetAddress getServerIPs(){
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
    
    /**
     * Metodo que corre servidor python sobre directoriio de recursos
     */
    public void runPyServer(){
        try{
           String  cmd_ts = "python3 -m http.server 8181";
           System.out.println("cmd_ts: "+cmd_ts);    
           Process ps = Runtime.getRuntime().exec(cmd_ts);
           Scanner sc = new Scanner(ps.getInputStream());
           while (sc.hasNextLine()) {
               String ln = sc.nextLine();
               System.out.println("line: "+ln);    
           } 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}