package eu.cise.adaptor.signature;

import eu.cise.adaptor.exceptions.AISAdaptorException;
import eu.cise.datamodel.v1.entity.vessel.NavigationalStatusType;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.datamodel.v1.entity.vessel.VesselType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullResponse;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.DefaultXmlValidator;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlValidator;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import static eu.cise.servicemodel.v1.message.InformationSecurityLevelType.NON_CLASSIFIED;
import static eu.cise.servicemodel.v1.message.InformationSensitivityType.AMBER;
import static eu.cise.servicemodel.v1.message.PriorityType.HIGH;
import static eu.cise.servicemodel.v1.message.PurposeType.BORDER_MONITORING;
import static eu.cise.servicemodel.v1.message.ResponseCodeType.SUCCESS;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PULL;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;
import static eu.eucise.helpers.PullResponseBuilder.newPullResponse;
import static eu.eucise.helpers.ServiceBuilder.newService;


public class SignatureServiceTest {

    private SignatureService signature;
    private XmlMapper xmlMapper;
    private XmlValidator xmlValidator;
    private Message msg;

    @Before
    public void before() {
        xmlMapper = new DefaultXmlMapper();
        xmlValidator = new DefaultXmlValidator();
        msg = buildMessage();
        signature = new DefaultSignatureService(
                new DefaultCertificateRegistry(new PrivateKeyInfo("eu.cise.es.gc-ls01", "cisecise"),
                        new KeyStoreInfo("cisePrivate.jks", "cisecise"),
                        new KeyStoreInfo("cisePublic.jks", "cisecise")));
    }

    @Test
    public void when_aaa() {
        Message signedMsg = signature.sign(msg);
        String messageXML = xmlMapper.toXML(signedMsg);

        xmlValidator.validate(messageXML);
        signedMsg = xmlMapper.fromXML(messageXML);

        signature.verifySignature(signedMsg);
    }

    @Test
    public void test_signing_and_verification_of_signature() {
        long startTime = System.currentTimeMillis();
        Message signedMsg = signature.sign(msg);
        signature.verifySignature(signedMsg);
        long endTime = System.currentTimeMillis();
        System.out.println("Operation took " + (endTime - startTime) + "ms");
    }

    @Test
    public void test_verification_of_signature_on_LC_Message() throws Exception {
        String messageXML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/SignedPushVessels.xml").toURI())), "UTF-8");
        Message signedMsg = new DefaultXmlMapper.PrettyNotValidating().fromXML(messageXML);
        signature.verifySignature(signedMsg);
    }


    @Test(expected = AISAdaptorException.class)
    public void test_verification_fails_if_message_was_tampered_with() {
        long time_1 = System.currentTimeMillis();
        Message signedMsg = signature.sign(msg);
        signedMsg.setMessageID("lulu");
        signature.verifySignature(signedMsg);
        long time_2 = System.currentTimeMillis();
        System.out.println("Operation took " + (time_2 - time_1) + "ms");
    }

    @Test(expected = AISAdaptorException.class)
    public void test_verification_fails_if_payload_was_tampered_with() {
        long time_1 = System.currentTimeMillis();
        Message msg = buildMessage();
        Message signedMsg = signature.sign(msg);
        ((Vessel) ((XmlEntityPayload) signedMsg.getPayload()).getAnies().get(0)).setDeadweight(88);
        signature.verifySignature(signedMsg);
        long time_2 = System.currentTimeMillis();
        System.out.println("Operation took " + (time_2 - time_1) + "ms");
    }


    private Message buildMessage() {
        String uuid = UUID.randomUUID().toString();
        return newPullResponse()
                .contextId(uuid)
                .correlationId(uuid)
                .creationDateTime(new Date())
                .id(uuid)
                .priority(HIGH)
                .isRequiresAck(false)
                .sender(newService().id("es.gc-ls01.maritimesafetyincident.pullresponse.gcs04")
                        .operation(PULL))
                .resultCode(SUCCESS)
                .recipient(newService().id("myService2")
                        .operation(PULL))
                .addEntity(buildVessel())
                .informationSecurityLevel(NON_CLASSIFIED)
                .informationSensitivity(AMBER)
                .isPersonalData(false)
                .purpose(BORDER_MONITORING)
                .retentionPeriod(new Date())
                .build();
    }

    private Vessel buildVessel() {
        Vessel v = new Vessel();
        v.getNames().add("The Mother Queen");
        v.setDeadweight(30);
        v.setDraught(30.4);
        v.setGrossTonnage(34.5);
        v.setMMSI(45545454L);
        v.setIMONumber(435454L);
        v.setLength(54.56);
        v.setNavigationalStatus(NavigationalStatusType.ENGAGED_IN_FISHING);
        v.getShipTypes().add(VesselType.FISHING_VESSEL);
        v.setYearBuilt(1978);
        return v;
    }

}
