//In this single threaded system we one making one request by the client
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
public class Client {
    public void run() throws UnknownHostException,IOException{
    int port = 8010;
    // Here we define the port number of the server to which the client wants to connect.
    // The client must use the same port on which the server is listening.

    InetAddress address = InetAddress.getByName("LocalHost");
    // InetAddress represents the IP address of a host.
    // getByName("LocalHost") resolves the hostname "localhost" to the IP address 127.0.0.1.
    // This means the client will connect to a server running on the same machine.

    Socket socket = new Socket(address, port);//it creates a socket that will connect to a server socket after it has been listened by a ServerSocket object at (address, port)
    // A Socket object is created in the JVM.
    // This asks the operating system to create a TCP client socket.
    // The OS assigns a temporary (ephemeral) port to the client and initiates a TCP connection
    // to the server's IP address (127.0.0.1) and port 8010.
    // If a ServerSocket is listening on that port, the TCP connection is established.
    // This socket will now be used by the client to send and receive data from the Socket of server.
        PrintWriter toServer=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader fromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer.println("Hello World from client "+socket.getLocalSocketAddress());
        System.out.println("Server: "+fromServer.readLine());
        toServer.close();
        fromServer.close();
        socket.close();
    }
    public static void main(String[] args){
        Client singleThreadedWebServerClient=new Client();
        try{
            singleThreadedWebServerClient.run();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}