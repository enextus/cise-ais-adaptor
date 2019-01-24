package eu.cise.adaptor;

import eu.cise.adaptor.server.RestWorkerFactory;
import eu.cise.adaptor.server.TestRestServer;
import eu.cise.adaptor.sources.IasirAuthDecorator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.fail;

public class IsairTcpStreamGeneratorTest {

    private TestRestServer testRestServer;
    private AisStreamGenerator isairGenerator;
    private Thread serverT;

    @Before
    public void before() {
        testRestServer = new TestRestServer(new RestWorkerFactory(), 64738, 1);
        serverT = new Thread(testRestServer);
    }

    @After
    public void after() {
        testRestServer.shutdown();
    }

    @Test
    public void it_waits_for_the_first_ais_message_in_the_stream() throws InterruptedException {
        serverT.start();

        try {
            isairGenerator = new IasirAuthDecorator("localhost", 64738, new Socket());
            isairGenerator.generate().forEach(System.out::println);
            testRestServer.shutdown();
        } catch (Exception e) {
            testRestServer.shutdown();
            e.printStackTrace();
            fail("something went wrong with the protocol");
        }

        testRestServer.shutdown();

    }

    @Test
    @Ignore
    public void it_shutdown_gracefully_if_the_socket_is_closed() {
    }

    @Test
    @Ignore
    public void it_exits_if_the_login_string_is_wrong_or_missing() {
    }

}
