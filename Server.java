import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" Server running at: http://localhost:" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleRequest(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleRequest(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream()
        ) {

            System.out.println("\n Client connected!");

            // Read HTTP request safely
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

            // HTML DESIGN
            String html =
                    "<html>" +
                    "<head>" +
                    "<title>Coquette Server</title>" +
                    "<style>" +
                    "body {" +
                    "  background: linear-gradient(to right, #ffe6f0, #fff0f5);" +
                    "  font-family: 'Segoe UI', cursive;" +
                    "  text-align: center;" +
                    "  margin-top: 100px;" +
                    "}" +
                    ".card {" +
                    "  background: white;" +
                    "  padding: 30px;" +
                    "  margin: auto;" +
                    "  width: 50%;" +
                    "  border-radius: 20px;" +
                    "  box-shadow: 0 8px 20px rgba(255, 182, 193, 0.5);" +
                    "}" +
                    "h1 {" +
                    "  color: #ff69b4;" +
                    "}" +
                    "p {" +
                    "  color: #555;" +
                    "  font-size: 18px;" +
                    "}" +
                    ".footer {" +
                    "  margin-top: 20px;" +
                    "  font-size: 14px;" +
                    "  color: #999;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='card'>" +
                    "<h1> Hello from Server </h1>" +
                    "<p>This is a HTTP server built using Java TCP sockets.</p>" +
                    "<div class='footer'>By Akshaya</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            // Proper HTTP response
            String response =
                    "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + html.getBytes().length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    html;

            out.write(response.getBytes());
            out.flush();

            clientSocket.close();

        } catch (IOException e) {
            System.out.println(" Error handling client");
            e.printStackTrace();
        }
    }
}