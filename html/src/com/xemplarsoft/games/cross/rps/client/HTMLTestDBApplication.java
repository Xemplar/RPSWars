package com.xemplarsoft.games.cross.rps.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.testdb.*;
import com.google.gwt.http.client.*;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HTMLTestDBApplication implements TestDBApplication, CryptoProvider {
    static final Logger logger = Logger.getLogger(HTMLTestDBApplication.class.getName());
    private final HTMLLoginListener listener;
    private final TestDB testdb;
    public HTMLTestDBApplication(HTMLLoginListener listener){
        testdb = new TestDB(this, this, Wars.APPCODE);
        this.listener = listener;
    }

    public TestDB getTestDB(){
        return testdb;
    }

    public void login(){
        Document d = Document.get();
        String username = ((SpanElement) d.getElementById("user")).getInnerHTML();
        String password = ((SpanElement) d.getElementById("pass")).getInnerHTML();

        try {
            testdb.loginUser(username, password.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void postRequest(String url, KVPair[] postValues) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
        try {
            builder.setHeader("Content-type", "application/x-www-form-urlencoded");
            StringBuilder sb = new StringBuilder();
            for (KVPair k : postValues) {
                if(k != null){
                    if(k.value != null){
                        String vx = k.value;
                        if (sb.length() > 0) {
                            sb.append("&");
                        }
                        sb.append(k.key).append("=").append(vx);
                    }
                }
            }

            Request request = builder.sendRequest(sb.toString(), new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    // TODO Implement connection fail to own server lol.
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        testdb.dataReceived(response.getText());
                        logger.fine(response.getText());
                    } else {
                        // TODO Implement connection fail to own server lol.
                    }
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        SpanElement email = (SpanElement) Document.get().getElementById("");
        return email == null ? "" : email.getInnerText();
    }

    public String getTestDBURL() {
        return Wars.TEST_DB_URL;
    }

    public String getSystem() {
        return "GWT";
    }

    public String getJVMVersion() {
        return "GWT";
    }

    public String getArch() {
        return "WebGL";
    }

    public String getSystemVersion() {
        return "GWT";
    }

    public String getAppVersion() {
        return Wars.APP_VERSION;
    }

    public String getReleaseEnclave() {
        return Wars.APP_ENCLAVE;
    }

    public void handleIsRegistered(boolean registered) {

    }

    public void handleExists(boolean exists, int uid) {

    }

    public void handleDataPushed(boolean pushed, String message, String key) {

    }

    public void handleDataGot(String message, String key, String data) {

    }

    public void handleHasUsername(boolean has) {

    }

    public void handleLogin(boolean login, String data) {
        listener.loggedIn(login, data);
    }

    public void handleRegister(boolean register, String error) {

    }

    //HTML is encrypted with ssl by itself
    public String encrypt(String data) {
        return Base64.encodeToString(data);
    }

    public String decrypt(String data) {
        return new String(Base64.decode(data));
    }

    public void setPubkey(String pem) {

    }
}
