package com.xemplarsoft.games.cross.rps.desktop;

import com.badlogic.gdx.utils.Queue;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.IntermediateTestDBApplication;
import com.xemplarsoft.utils.testdb.*;

import javax.crypto.Cipher;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static com.xemplarsoft.utils.testdb.DataRequester.*;

public class DesktopTestDBApplication extends IntermediateTestDBApplication implements CryptoProvider, DataReceiver {
    private final Queue<String> queue = new Queue<>();
    private final TestDB testdb;

    public static final int INTENT_NONE = 0;
    public static final int INTENT_CHECK_EMAIL = 1;
    public static final int INTENT_CHECK_ACCESS = 2;
    public static final int INTENT_ACCESS_CHECK_EMAIL = 3;
    public static final int INTENT_LOGIN = 4;
    public static final int INTENT_REGISTER = 5;
    public static final int INTENT_ACCESS_SUCCESSFUL = 6;
    public static final int INTENT_LOGIN_ERROR = -1;
    public static final int INTENT_REGISTER_ERROR = -2;
    private int INTENT = 0;

    private DataRequester requester;
    private String email = "", user, error, pem;
    private byte[] pass;
    private boolean exist;
    private int PID, UID;
    public PublicKey pubkey;

    public DesktopTestDBApplication(DataRequester requester){
        testdb = new TestDB(this, this, Wars.APPCODE);
        this.requester = requester;
    }

    public String encrypt(String data) {
        if(pubkey == null) return null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); //or try with "RSA"
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String decrypt(String data) {
        if(pubkey == null) return null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); //or try with "RSA"
            cipher.init(Cipher.DECRYPT_MODE, pubkey);
            byte[] encrypted = cipher.doFinal(Base64.decode(data.getBytes(), Base64.DEFAULT));
            return new String(encrypted, StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void setPubkey(String pem) {
        if(pem == null) return;
        if(pem.equals("")) return;

        this.pem = pem;
        String key = new String(Base64.decode(pem, Base64.DEFAULT));
        key = key.replace("-----BEGIN PUBLIC KEY-----", "");
        key = key.replace("-----END PUBLIC KEY-----", "");
        key = key.replace("\n", "");

        try {
            pubkey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(key)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPem(){
        return pem;
    }

    public void checkSignedIn(int uid, int pid, String user, byte[] pass){
        if(uid == -1){
            requester.requestEmail(ACTION_EMAIL);
        } else {
            testdb.setUID(uid);
            if(user.equals("")){
                requester.requestUserPass(pid == -1 ? ACTION_REGISTER : ACTION_LOGIN,"");
            } else {
                this.user = user;
                this.pass = pass;
                testdb.loginUser(user, pass);
            }
        }
    }

    public TestDB getTestDB(){
        return testdb;
    }

    //Data Receiver Methods
    public void emailReceived(String email) {
        if(email == null){
            this.exist = false;
        } else {
            this.email = email;
            this.INTENT = INTENT_ACCESS_CHECK_EMAIL;
            this.testdb.exist();
        }
    }

    public void userPassReceived(String user, byte[] pass, int action) {
        if(email == null){
            this.exist = false;
        } else {
            this.user = user;
            this.pass = pass;

            if(action == ACTION_LOGIN){
                testdb.loginUser(user, pass);
            } else if(action == ACTION_REGISTER){
                testdb.registerUser(user, pass);
            }
        }
    }


    public void postRequest(String url, KVPair[] postValues) {
        StringBuilder urlBuilder = new StringBuilder();
        try {
            for (int i = 0; i < postValues.length; i++) {
                KVPair pair = postValues[i];
                if(pair == null) continue;
                if(pair.value == null) continue;
                urlBuilder.append(pair.key).append('=').append(URLEncoder.encode(pair.value, "UTF-8"));
                if (i < postValues.length - 1) urlBuilder.append('&');
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        String urlParameters = urlBuilder.toString();
        try {
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            HttpsURLConnection conn = (HttpsURLConnection) (new URL(url)).openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String data = "";
            StringBuilder builder = new StringBuilder();
            while((data = reader.readLine()) != null){
                builder.append(data).append('\n');
            }
            testdb.dataReceived(builder.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }
    public String getSystem() {
        return System.getProperty("os.name");
    }
    public String getJVMVersion() {
        return System.getProperty("java.version");
    }
    public String getArch() {
        return System.getProperty("os.arch");
    }
    public String getSystemVersion() {
        return System.getProperty("os.name");
    }

    public void handleIntent(){
        System.out.println("INTENT: " + INTENT);
        if(INTENT == INTENT_NONE){
            return;
        } else if(INTENT == INTENT_CHECK_ACCESS){
            testdb.checkIfHasUsername();
        } else if(INTENT == INTENT_REGISTER){
            requester.requestUserPass(com.xemplarsoft.utils.testdb.DataRequester.ACTION_REGISTER, error);
        } else if(INTENT == INTENT_LOGIN){
            requester.requestUserPass(DataRequester.ACTION_LOGIN, error);
        } else if(INTENT == INTENT_ACCESS_SUCCESSFUL){
            requester.accessed(user, pass);
            INTENT = INTENT_NONE;
        } else if(INTENT == INTENT_LOGIN_ERROR){
            INTENT = INTENT_LOGIN;
            handleIntent();
        } else if(INTENT == INTENT_REGISTER_ERROR){
            INTENT = INTENT_REGISTER;
            handleIntent();
        }
    }

    public void handleIsRegistered(boolean registered) {
        Wars.registered = registered;
        if(!registered){
            System.out.println("You aren't registered.");
        } else {
            System.out.println("Your PID: " + testdb.getPID() + ", Your UID: " + testdb.getUID());
        }
    }

    public void handleExists(boolean exists, int uid) {
        this.exist = exists;
        this.UID = uid;

        if(INTENT != INTENT_CHECK_EMAIL && INTENT != INTENT_ACCESS_CHECK_EMAIL) {
            if (exists) {
                System.out.println("User account exists. UID: " + this.testdb.getUID());
                testdb.checkIfHasPID();
            }
        }

        INTENT = INTENT == INTENT_CHECK_EMAIL ? INTENT_NONE : INTENT;
        INTENT = INTENT == INTENT_ACCESS_CHECK_EMAIL ? INTENT_CHECK_ACCESS : INTENT;

        handleIntent();
    }

    public void handleDataPushed(boolean pushed, String message, String key) {
        if(!pushed) System.err.println(message);
    }

    public void handleDataGot(String message, String key, String data) {
        if(message != null) System.err.println(message);
        //System.out.println("Data push " + (pushed ? "successful" : "failed") + ".");
    }

    public void handleHasUsername(boolean has) {
        INTENT = has ? INTENT_LOGIN : INTENT_REGISTER;
        handleIntent();
    }

    public void handleLogin(boolean login, String data) {
        if(login){
            INTENT = INTENT_ACCESS_SUCCESSFUL;
        } else {
            INTENT = INTENT_LOGIN_ERROR;
            error = data;
        }
        handleIntent();
    }

    public void handleRegister(boolean register, String data) {
        if(register){
            INTENT = INTENT_ACCESS_SUCCESSFUL;
        } else {
            INTENT = INTENT_REGISTER_ERROR;
            error = data;
        }
        handleIntent();
    }
}
