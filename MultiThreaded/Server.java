// When I run java Server, the JVM creates one application thread called the main thread.
// This thread executes the main() method or executes all the code called from main() sequentially.
// it executes these lines
    // int port=8010; //initialzes the port variable in heap
    // Server server=new Server(); //initialzes the server object in the heap
    // ServerSocket serverSocket=new ServerSocket(port); //creates a server-socket to listen for clients
    // serverSocket.setSoTimeout(20000);//sets the waiting time for server-socket to 20 sec
    // System.out.println("Server is Listening on port: "+port);
    // while(true){
    //     Socket acceptedConnection=serverSocket.accept(); //this step also happens in main thread
    //     Thread thread =new Thread(()->server.getConsumer().accept(acceptedConnection));
    ///    In this step the main thread creates a new Thread called "worker-thread" (with state=new) that has the responsiblity to execute
    ///    "()->server.getConsumer().accept(acceptedConnection)" this lambda expression
    /// 
    //     thread.start(); here the main thread causes the state of "worker-thread" from "new" to "running".
    ///    now we have two threads "main" and "worker-thread" both in runnble state.
    ///    The OS scheduler decides which thread to  run when 
    // Possible scenario 1:
    // Main Thread continues running
    // Worker Thread waits

    // Possible scenario 2:
    // Worker Thread starts immediately
    // Main Thread waits

    // Possible scenario 3:
    // Both run simultaneously on different CPU cores
    //     If CPU Has Multiple Cores

    // Example: 4 cores
    // Core 1 → Main Thread
    // Core 2 → Worker Thread


    //Conclusion
    // Main thread: accepts incoming socket connections and spawns/creates worker threads.
    // Worked threads: handle client communication and process requests independently.





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
public class Server{
    public Consumer<Socket> getConsumer(){
        return (acceptedConnection)->{
            try (
                    PrintWriter toClient=new PrintWriter(acceptedConnection.getOutputStream(),true);
                    BufferedReader fromClient =new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()))
                ){
                toClient.println("Hello from server: "+acceptedConnection.getRemoteSocketAddress());
                char[] buffer = new char[1024];
                int n = fromClient.read(buffer);
                String msg = new String(buffer, 0, n);
                System.out.println("Client: " + msg);
                toClient.println("ACK");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    // getConsumer() return a object of type Consumer<Socket>.
    // defination of Consumer<Socket> from Consumer<T>
    // objects(obj) of Consumer<Socket> have ability to do obj.accept(Socket) and process the Socket 
    // here it accepts the socket object of the server and creates a text writer which is used to send text to the client
    // the purpose of writing try(here) {not here} to automatically close the resources when the try block finises
    // 
    public static void main(String[] args){
        int port=8010;
        Server server=new Server();
        try{
            ServerSocket serverSocket=new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
            System.out.println("Server is Listening on port: "+port);
            while(true){
                Socket acceptedConnection=serverSocket.accept();
                Thread thread =new Thread(()->server.getConsumer().accept(acceptedConnection));
                thread.start(); 
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}