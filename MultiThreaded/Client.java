//In this Multi threaded system we will be  making multiple request by the client
//we have only one client code but we need to spawn 100s of clients 

import java.io.BufferedReader;
//import java.io.IO;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public Runnable getRunnable() throws UnknownHostException,IOException{
        return new Runnable(){
            @Override
            public void run(){
                int port=8010;
                try{
                    InetAddress address=InetAddress.getByName("localhost");
                    Socket socket=new Socket(address,port);
                    try(
                        PrintWriter toServer=new PrintWriter(socket.getOutputStream(),true);
                        BufferedReader fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    ){
                        toServer.println("Hello from Client: "+socket.getLocalSocketAddress());
                        String line=fromServer.readLine();
                        System.out.println("Server: "+line);
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        };
    }
    public static void main(String[] args) throws InterruptedException{
        Client client=new Client();
        for(int i=0;i<100;i++){
            try{
                Thread thread=new Thread(client.getRunnable());
                thread.start();
                Thread.sleep(10);
            }
            catch(IOException ex){
                return;
            }
        }
        return;
    }
}
