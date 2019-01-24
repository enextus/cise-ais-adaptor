package eu.cise.adaptor.server;

import eu.cise.adaptor.exceptions.AdaptorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class IsairRestWorker implements RestWorker {

    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String[] aisMessages = new String[]{
            "!AIVDM,1,1,,B,13P88o@02=OqlrHM6FATwCvf08=E,0*73",
            "!AIVDM,1,1,,A,13P88o@uB=Oqm2<M6EkTvkvp0@@b,0*44",
            "!AIVDM,1,1,,A,13P88o@uB=OqmFPM6DSTukwB0<1G,0*7D",
            "!AIVDM,1,1,,A,13P88o@2j=OqmWHM6CRTuCw`0@NU,0*06"};

    private volatile boolean alive = true;

    public IsairRestWorker(Socket socket) {
        this.reader = getReader(socket);
        this.writer = getWriter(socket);
    }

    @Override
    public void run() {
        try {
            while (alive) {
                handleRequest();
                sleep(100);
            }
            reader.close();
            writer.close();
        } catch (InterruptedException | IOException e) {
            throw new AdaptorException(e);
        }
    }

    @Override
    public void handleRequest() throws IOException, InterruptedException {
        String input = readSocket();

        if (input != null && !input.isEmpty() && input.startsWith("[TYPE=LOGIN")) {
            Thread.sleep(1000);
            writeSocket("[TYPE=LOGIN_OK]");

            for (String aisMessage : aisMessages) {
                sleep(1000);
                writeSocket(aisMessage);
            }
        } else {
            Thread.sleep(1000);
            writeSocket("[TYPE=LOGIN_KO]");
        }

        alive = false;
    }

    private void writeSocket(String output) {
        System.out.println("> " + output);
        writer.println(output);
    }

    private String readSocket() throws IOException {
        String input = reader.readLine();
        System.out.println("< " + input);
        return input;
    }

    private PrintWriter getWriter(Socket socket) {
        try {
            return new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new AdaptorException(e);
        }
    }

    private BufferedReader getReader(Socket socket) {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new AdaptorException(e);
        }
    }
}
