package eu.cise.adaptor.tbsalling;

import dk.tbsalling.aismessages.nmea.NMEAMessageHandler;
import dk.tbsalling.aismessages.nmea.NMEAMessageSocketClient;
import eu.cise.adaptor.AISAdaptorException;
import eu.cise.adaptor.AISConsumer;
import eu.cise.adaptor.tbsalling.AISMessageHandler;

import java.io.IOException;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class SocketAISConsumer implements AISConsumer {

    private final NMEAMessageSocketClient nmeaMessageHandler;

    public SocketAISConsumer(String host, int port) {
        try {
            nmeaMessageHandler = new NMEAMessageSocketClient(
                    host, port,
                    new NMEAMessageHandler("AISAdaptor", new AISMessageHandler())
            );
        } catch (UnknownHostException e) {
            throw new AISAdaptorException(e);
        }
    }

    @Override
    public void run() {
        try {
            nmeaMessageHandler.run();
        } catch (IOException e) {
            throw new AISAdaptorException(e);
        }

    }
}