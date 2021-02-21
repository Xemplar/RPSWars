package com.xemplarsoft.games.cross.rps.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.NetworkProvider;
import com.xemplarsoft.games.cross.rps.NoneAdProvider;
import com.xemplarsoft.utils.testdb.DataRequester;
import com.xemplarsoft.utils.savedb.SaveDB;
import com.xemplarsoft.utils.testdb.TestDB;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import static com.xemplarsoft.games.cross.rps.Wars.*;

public class DesktopLauncher implements NetworkProvider, DataRequester {
	public DesktopTestDBApplication application;
	public TestDB testDB;
	public SaveDB save;

	public volatile boolean ready = false;

	public int uid = -1, pid = -1;
	public String username, email;
	public byte[] pass;

	public DesktopLauncher(){
		application = new DesktopTestDBApplication(this);
		testDB = new TestDB(application, application, Wars.APPCODE);
		save = new SaveDB(false, new DesktopDataHandler("Xemplar", null, "credentials.exd"));

		System.out.println("TestDB and SaveDB Created");

		try {
			pid = Integer.parseInt(save.getString(PREF_PID, "-1"));
			uid = Integer.parseInt(save.getString(PREF_UID, "-1"));
		} catch (Exception e){
			save.putInt(PREF_PID, -1);
			save.putInt(PREF_UID, -1);
			pid = -1;
			uid = -1;
		}
		username = save.getString(PREF_USER, "");
		email = save.getString(PREF_EMAIL, "");
		try {
			pass = save.getString(PREF_PASS, "").getBytes(StandardCharsets.UTF_8);
		} catch (Exception e){
			pass = new byte[]{};
		}
		save.flush();


		System.out.println("Got all prefs");
		application.setPubkey(save.getString(PREF_PUBKEY, ""));

		System.out.println("Checking Signin");
		System.out.println("UID: " + uid);
		System.out.println("PID: " + pid);
		System.out.println("Username: " + username);
		System.out.println("Password: " + new String(pass));
		application.checkSignedIn(uid, pid, username, pass);
	}

	private void launch(){
		registered = true;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = getAppName();
		config.addIcon("raw/app_icon_rounded.png", Files.FileType.Local);
		config.width = 1280;
		config.height = 512;
		new LwjglApplication(new Wars(application, new NoneAdProvider(), this), config);
	}

	public String getIPAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()){
				NetworkInterface net = interfaces.nextElement();
				String name = net.getName();
				Enumeration<InetAddress> addresses = net.getInetAddresses();
				while(addresses.hasMoreElements()){
					String addr = addresses.nextElement().getHostAddress();
					System.out.println("Found " + addr + " on " + name);
					if(addr.startsWith("192.168.")) return addr;
					if(addr.startsWith("10.")) return addr;
					if(addr.startsWith("172.")){
						int nd = Integer.parseInt(addr.split("\\.")[1]);
						if(nd >= 16 && nd <= 31) return addr;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return "0.0.0.0";
	}

	public void sendBroadcast(String data) {
		try {
			InetAddress address = InetAddress.getByAddress(new byte[]{(byte)255, (byte)255, (byte)255, (byte)255});
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			byte[] sendData = data.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, Wars.PORT);
			socket.send(sendPacket);
			System.out.println(getClass().getName() + "Broadcast packet sent to: " + address.getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestEmail(int action) {
		new EmailRetriever(this);
		System.out.println("Email Request Window Open");
	}

	public void requestUserPass(int action, String error) {
		new UserPassRetriver(this, action);
	}

	public void accessed(String username, byte[] password) {
		save.putString(PREF_PID, application.getTestDB().getPID() + "");
		save.putString(PREF_UID, application.getTestDB().getUID() + "");
		save.putString(PREF_EMAIL, application.getEmail());
		save.putString(PREF_USER, username);
		save.putString(PREF_PASS, new String(password));
		save.putString(PREF_PUBKEY, application.getPem());
		save.flush();

		ready = true;
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			e.printStackTrace();
		}
		DesktopLauncher l = new DesktopLauncher();
		while(!l.ready){ }
		l.launch();
	}

	public static String getAppName(){
		String ret = APPCODE;
		ret = ((ret.charAt(0) + "").toUpperCase()) + ret.substring(1);
		ret += " v" + APP_VERSION + " " + APP_ENCLAVE;

		return ret;
	}
}
