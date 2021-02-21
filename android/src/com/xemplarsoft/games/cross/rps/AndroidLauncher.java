package com.xemplarsoft.games.cross.rps;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AndroidLauncher extends AndroidApplication implements IUnityAdsListener, AdProvider, NetworkProvider {
	private AndroidApplicationConfiguration config;
	private String unityGameID = "3892815";
	private String placementId = "video";
	private boolean testMode = false;
	private Wars wars;

	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		wars = new Wars(MainActivity.requester, this, this);

		UnityAds.addListener(this);
		UnityAds.initialize(this.getContext(), unityGameID, testMode);

		initialize(wars, config);
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
							View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
							View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
							View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	public void onUnityAdsReady(String placementId) {

	}

	public void onUnityAdsStart(String placementId) {

	}

	public void onBackPressed() {
		System.exit(0);
	}

	public void onUnityAdsFinish(String placementId, UnityAds.FinishState finishState) {
		// Implement conditional logic for each ad completion status:
		if(finishState.equals(UnityAds.FinishState.COMPLETED)){

		} else if (finishState == UnityAds.FinishState.SKIPPED) {

		} else if (finishState == UnityAds.FinishState.ERROR) {

		}
	}

	public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {

	}

	public String getIPAddress() {
		return NetworkUtils.getIPAddress(true);
	}

	public void sendBroadcast(String data){
		StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			InetAddress address = NetworkUtils.getBroadcastAddress(this);
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

	public void displayAd() {
		if (UnityAds.isReady(placementId)) {
			UnityAds.show (this, placementId);
		}
	}
}