// In the context of your Java client–server socket program, a single-threaded system means:

    // When you run java Server, the JVM creates one application thread called the main thread.
    // This thread executes the main() method or executes all the code  inside the  main() sequentially.
    // only one thread is  created and that manages all clients, so it is single-threaded.

//When ever i say a thread is created it is actually a thread control block that is created
//a datastructue describing a thread
//It looks like this
//Thread Control Block
// ---------------------
// Thread ID
// Thread state
// Program counter
// Stack pointer
// CPU registers
// Priority
// Scheduling info

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
public class Server{
    public void run() throws IOException,UnknownHostException, InterruptedException{
        int port=8010;
        ServerSocket socket=new ServerSocket(port);
        //in this step, first  a ServerSocket java object is created in the memory which
        //points to a server-socket created in the os binded to the port no 8010
        //In this way it is binded to the process(this Server.java) containing this line

        //a port no identifies which socket and therefore which program should receive the data
        //A socket is a software data structure in the os that represents a network communication endpoint
        //used by the process to send/receive data
        //ServerSocket and Socket class in java are used for creating TCP sockets 
        //ServerSocket creates listing socket 
        //Socket creates data-transfer sockets
        //The port no identifies socket not acceptedConnection
        socket.setSoTimeout(20000);
        //sets a waiting time limit (20,000 ms = 20 seconds) for accept() by the ServerSocket socket
        while(true){
            try{
                System.out.println("Server is listening on port: "+port);
                Socket acceptedConnection=socket.accept(); //it returns a Socket object that is connected to the Socket
                //object of the client and is used to send and receive data
        
                System.out.println("Connection accepted from client "+acceptedConnection.getRemoteSocketAddress());
                //.getRemoteSocketAddress give the remote/client end point socket address
                //Thread.sleep(5000);
                PrintWriter toClient=new PrintWriter(acceptedConnection.getOutputStream(),true); //it creates a text writer from server to the client
                BufferedReader fromClient =new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));//it creates a text reader to receive client messages line by line
                toClient.println("Hello from the server");
                char[] buffer = new char[1024];
                int n = fromClient.read(buffer);
                String msg = new String(buffer, 0, n);
                System.out.println("Client: " + msg);
                toClient.println("ACK");
                // fromClient.close();
                // toClient.close();
                acceptedConnection.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    
    }
    public static void main(String[] args){
        //here the JVM creates one thread called the main thread.
        Server server=new Server();
        try{
            server.run();
        }
        catch(Exception ex){
            ex.printStackTrace();   
        }
    }
}