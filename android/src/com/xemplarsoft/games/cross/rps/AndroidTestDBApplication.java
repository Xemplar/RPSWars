package com.xemplarsoft.games.cross.rps;

import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.badlogic.gdx.utils.Queue;
import com.xemplarsoft.utils.testdb.*;
import com.xemplarsoft.utils.testdb.DataRequester;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import static com.xemplarsoft.utils.testdb.DataRequester.*;
import static com.xemplarsoft.games.cross.rps.MainActivity.APPCODE;

public class AndroidTestDBApplication implements TestDBApplication, DataReceiver, CryptoProvider {
    private final Queue<String> queue = new Queue<>();
    private final TestDB testdb;

    private final RequestQueue requestQueue;
    private final Cache cache;
    private final Network network;
    private final Activity con;

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
    private RSAPublicKey pubkey;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public AndroidTestDBApplication(Activity con, com.xemplarsoft.utils.testdb.DataRequester requester){
        this.testdb = new TestDB(this, this,  APPCODE);
        this.con = con;
        this.requester = requester;

        this.cache = new DiskBasedCache(con.getCacheDir(), 1024 * 1024);
        this.network = new BasicNetwork(new HurlStack());
        this.requestQueue = new RequestQueue(cache, network);
        this.requestQueue.start();
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
        if(this.pem == null) this.pem = "";
        if(!this.pem.equals(pem)){
            this.pem = pem;
            try {
                String key = new String(Base64.decode(pem, Base64.DEFAULT));
                key = key.replace("-----BEGIN PUBLIC KEY-----", "");
                key = key.replace("-----END PUBLIC KEY-----", "");
                key = key.replace("\n", "");
                System.out.println(key);

                KeyFactory kf = KeyFactory.getInstance("RSA");
                X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT));
                pubkey = (RSAPublicKey) kf.generatePublic(keySpecX509);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public String getPEM(){
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
        return this.testdb;
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

    // TestDB Requester Methods
    public void postRequest(String url, final KVPair[] postValues) {
        StringRequest request = new AndroidTestDBRequest(postValues, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                System.out.println("Response Text: " + response);
                queue.addLast(response);
                con.runOnUiThread(run);
            }
        });
        requestQueue.add(request);
    }
    public Runnable run = new Runnable(){ public void run(){ while(!queue.isEmpty()){ testdb.dataReceived(queue.removeFirst()); }}};
    public String getEmail() {
        return email;
    }
    public String getTestDBURL() {
        return Wars.TEST_DB_URL;
    }
    public String getSystem() {
        return "Android";
    }
    public String getJVMVersion() {
        return System.getProperty("java.vm.version");
    }
    public String getArch() {
        return System.getProperty("os.arch");
    }
    public String getSystemVersion() {
        return Build.VERSION.SDK_INT + "";
    }
    public String getAppVersion() {
        return Wars.APP_VERSION;
    }
    public String getReleaseEnclave() {
        return Wars.APP_ENCLAVE;
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
            String data = android.os.Build.MODEL + ":" + android.os.Build.MANUFACTURER;
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
