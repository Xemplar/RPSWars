package com.xemplarsoft.games.cross.rps;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.xemplarsoft.games.cross.che.R;
import com.xemplarsoft.utils.savedb.RawDataHandler;
import com.xemplarsoft.utils.savedb.SaveDB;
import com.xemplarsoft.utils.testdb.Base64;
import com.xemplarsoft.utils.testdb.DataRequester;

public class MainActivity extends AppCompatActivity implements DataRequester {
    public static final String POLICY_XEMPLAR = "https://xemplarsoft.com/testdb/chess_policy";
    public static final String POLICY_UNITY = "https://unity3d.com/legal/privacy-policy";

    public static final String SHARED_PREFS = "com.xemplarsoft.accounts.xgh";

    public static final String XGH = "com.xemplarsoft.android.xgh";
    public static final String APPCODE = "wars";
    public static AndroidTestDBApplication requester;

    public static String username, email;
    public static int pid, uid;
    public static byte[] pass;
    public static SaveDB save;

    private RelativeLayout layout;
    private ScrollView content;
    private SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        content = findViewById(R.id.content);

        layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.loading, content, false);
        this.content.removeAllViews();
        this.content.addView(layout);

        requester = new AndroidTestDBApplication(this, this);
        prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        save = new SaveDB(false, new RawDataHandler() {
            private final SharedPreferences.Editor edit = prefs.edit();
            public void putBytes(String key, byte[] value) {
                edit.putString(key, Base64.encodeToString(value));
            }

            public byte[] getBytes(String key, byte[] defaultValue) {
                return Base64.decode(prefs.getString(key, Base64.encodeToString(defaultValue)));
            }

            public void flush() {
                edit.apply();
            }
        });

        if(isXGHInstalled()){
            //TODO Make app, Doesn't exist yet lol
        } else {
            try {
                pid = Integer.parseInt(save.getString(Wars.PREF_PID, "-1"));
                uid = Integer.parseInt(save.getString(Wars.PREF_UID, "-1"));
            } catch (Exception e){
                pid = -1;
                uid = -1;
            }
            username = save.getString(Wars.PREF_USER, "");
            email = save.getString(Wars.PREF_EMAIL, "");
            try {
                pass = save.getString(Wars.PREF_PASS, "").getBytes("UTF-8");
            } catch (Exception e){
                pass = new byte[]{};
            }
            requester.setPubkey(save.getString(Wars.PREF_PUBKEY, ""));
        }

        if(isNetworkAvailable()) {
            requester.checkSignedIn(uid, pid, username, pass);
        } else {
            Intent i = new Intent(this, AndroidLauncher.class);
            System.out.println("Skipping Check, No Network Access.");
            Wars.registered = true;
            startActivity(i);
        }
    }

    public void requestEmail(final int action) {
        System.out.println("Requested Email");
        this.action = action;

        layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.email, content, false);
        this.content.removeAllViews();
        this.content.addView(layout);

        TextView error_label = layout.findViewById(R.id.error);
    }

    private int action;
    public void requestUserPass(final int action, final String error) {
        System.out.println("Requested User Pass");

        layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.login, content, false);
        this.content.removeAllViews();
        this.content.addView(layout);

        TextView user_label = layout.findViewById(R.id.login_user_label);
        user_label.setText(action == ACTION_LOGIN ? R.string.login_user_help : R.string.login_user_help_no_user);

        TextView pass_label = layout.findViewById(R.id.login_pass_label);
        pass_label.setText(action == ACTION_LOGIN ? R.string.login_pass_help : R.string.login_pass_help_no_user);

        TextView error_label = layout.findViewById(R.id.error);
        error_label.setText(error);

        this.action = action;
    }

    public void formSubmit(View v){
        switch (action){
            case ACTION_LOGIN:
            case ACTION_REGISTER: {
                EditText user = layout.findViewById(R.id.login_user);
                EditText pass = layout.findViewById(R.id.login_pass);
                if(pass.getText().toString().equals("") || user.getText().toString().equals("")) {
                    String error = "";
                    error += user.getText().toString().equals("") ? "You cannot leave the username field blank.\n" : "";
                    error += pass.getText().toString().equals("") ? "You cannot leave the username field blank.\n" : "";

                    TextView error_label = layout.findViewById(R.id.error);
                    error_label.setText(error);
                } else {
                    requester.userPassReceived(user.getText().toString(), pass.getText().toString().getBytes(), action);
                    layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.loading, content, false);
                    this.content.removeAllViews();
                    this.content.addView(layout);
                }
                break;
            }
            case ACTION_EMAIL:{
                EditText email = layout.findViewById(R.id.login_email);
                CheckBox accept_xemplar = layout.findViewById(R.id.accept_xemplar);
                CheckBox accept_unity = layout.findViewById(R.id.accept_unity);
                CheckBox accept_age = layout.findViewById(R.id.accept_age);

                TextView error_label = layout.findViewById(R.id.error);

                String error = "";
                if(!accept_xemplar.isChecked()) error += "You must accept Xemplar Softworks, LLC's privacy policy. \n";
                if(!accept_unity.isChecked()) error += "You must accept Unity Technologies' privacy policy. \n";
                if(!accept_age.isChecked()) error += "You must acknowledge the COPPA declaration. \n";
                if(email.getText().toString().equals("")) error += "You must enter your email address.";

                if(error.equals("")) {
                    requester.emailReceived(email.getText().toString());
                    layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.loading, content, false);
                    this.content.removeAllViews();
                    this.content.addView(layout);
                } else {
                    error_label.setText(error);
                }
                break;
            }
        }
    }

    public void policyX(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(POLICY_XEMPLAR));
        startActivity(browserIntent);
    }

    public void policyU(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(POLICY_UNITY));
        startActivity(browserIntent);
    }

    public void accessed(String username, byte[] pass) {
        save.putString(Wars.PREF_PID, requester.getTestDB().getPID() + "");
        save.putString(Wars.PREF_UID, requester.getTestDB().getUID() + "");
        save.putString(Wars.PREF_EMAIL, requester.getEmail());
        save.putString(Wars.PREF_USER, username);
        save.putString(Wars.PREF_PASS, new String(pass));
        save.putString(Wars.PREF_PUBKEY, requester.getPEM());
        save.flush();

        Intent i = new Intent(this, AndroidLauncher.class);
        Wars.registered = true;
        startActivity(i);
    }

    public void checkAccounts(){
        if (checkSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(this).getAccounts();
            if(accounts.length == 0){
                Intent i = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, "Select the account you registered on the website.", null, null, null);
                startActivityForResult(i, 0xFF);
            } else {
                Intent i = new Intent(this, AndroidLauncher.class);
                startActivity(i);
            }
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS)) {

        } else {
            requestPermissions(new String[] { Manifest.permission.GET_ACCOUNTS }, 0x01);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isXGHInstalled() {
        try {
            getPackageManager().getPackageInfo(XGH, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
