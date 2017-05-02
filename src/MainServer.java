import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static final String SabaAplicationSource = "/home/" + "mehdi" + "/SabaAplicationSource/";
    public static final String server = SabaAplicationSource + "Server/";
    public static final String PersonAddressDir = server + "Persons/";
    public static final String clientAddres = server + "ClientAddress";
    public static final String PersonAddressTxt = PersonAddressDir + "/Persons.txt";
    public static final int Port = 2466;

    public static void main(String[] args) throws IOException {
        new File(SabaAplicationSource).mkdir();
        new File(server).mkdir();
        new File(PersonAddressDir).mkdir();
        File persons = new File(PersonAddressTxt);
        persons.createNewFile();
        File personaddres = new File(clientAddres);
        personaddres.createNewFile();
        new MainServer().runserver();
    }

    public void runserver() throws IOException {

        ServerSocket serverSocket = new ServerSocket(Port);
        System.out.println("Server Up &&  ready for conection...");
        System.out.println("The first line is the clients request");
        System.out.println("And the second line is the answer server");
        System.out.println("-------------------------------------");

        while (true) {
            Socket socket = serverSocket.accept();
            new ServerThread(socket).start();
        }
    }

    public class ServerThread extends Thread {
        public Socket socket;

        ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                String message = null;
                BufferedReader inserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter outClient = new PrintWriter(socket.getOutputStream(), true);
                while ((message = inserver.readLine()) != null) {
//                   System.out.println(message);
                    outClient.println(Controller.ClientOrder(socket.getLocalAddress() + " " + socket.getLocalPort() + " " + message));
                   System.out.println("--------------------------------------------------");
                }
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());

            }
        }
    }

}
