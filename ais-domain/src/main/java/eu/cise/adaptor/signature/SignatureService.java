
package eu.cise.adaptor.signature;

import eu.cise.servicemodel.v1.message.Message;

public interface SignatureService {
    /**
     * This method will validate the signature of an incoming message against
     * the public key of the Sender System (Gateway or Legacy System).
     *
     * @throws eu.cise.adaptor.exceptions.AISAdaptorException in case the verification process fails
     */
    void verifySignature(Message message);

    /**
     * This method will sign a CISE Message and will return an instance of the signed message
     */
    Message sign(Message message);

}