package com.xemplarsoft.utils.ats;

public interface ATSClient {
    //-----------------------------------------------------//
    // Synchronous Functions
    //-----------------------------------------------------//

    /**
     * Generates a synchronous key.
     * @return Synchronous Key
     */
    public Object generateSyncKey();

    /**
     * Encrypt the provided challenge with stored synchronous key.
     * @param challenge string to be encrypted.
     * @return the encrypted String to be decrypted by STSServer.
     */
    public String encryptChallenge(String challenge);

    /**
     * Encrypt the provided username and password with stored synchronous key.
     * @param username username to be encrypted
     * @param pass password to be encrypted
     * @return the encrypted username and password to be decrypted by STSServer.
     */
    public String encryptCredentials(String username, byte[] pass);

    /**
     * Handle a synchronous encrypted message received from ATSServer
     * @param message synchronous encrypted message
     */
    public void handleSyncMessage(byte[] message);

    //-----------------------------------------------------//
    // Asynchronous Functions
    //-----------------------------------------------------//

    /**
     * Handle an asynchronous encrypted message received from ATSServer
     * @param message asynchronous encrypted message
     */
    public void handleAsyncMessage(byte[] message);

    /**
     * Return Asynchronous key from
     * @param async synchronous encrypted message
     */
    public Object getAsyncFromBytes(byte[] async);

    //-----------------------------------------------------//
    // Common Functions
    //-----------------------------------------------------//

    /**
     * Send byte-array message to a ATSServer.
     * @param message to be sent to ATSServer
     */
    public void sendToServer(byte[] message);

    /**
     * Handle received byte-array message.
     * @param message to be sent to ATSServer
     */
    public void messageReceived(byte[] message);

    //-----------------------------------------------------//
    // Plain Text Functions
    //-----------------------------------------------------//

    /**
     * Handle a plain text message received from ATSServer
     * @param message plain text message
     */
    public void handlePlainTextMessage(byte[] message);
}
