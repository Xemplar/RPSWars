package com.xemplarsoft.games.cross.rps;

import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;
import com.unity3d.services.banners.UnityBanners;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AndroidLauncher extends AndroidApplication implements IUnityAdsListener, AdProvider {
	private AndroidApplicationConfiguration config;
	private String unityGameID = "4021323";
	private String placementId = "Interstitial_Android";
	private String bannerId = "Banner_Android";
	private boolean testMode = false;
	private Wars wars;
	
	private BannerView banner;
	private RelativeLayout bannerContainer;

	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		config.useWakelock = true;
		
		wars = new Wars(this);

		UnityAds.addListener(this);
		UnityAds.initialize(this.getContext(), unityGameID, testMode, true);
		banner = new BannerView(this, bannerId, new UnityBannerSize(320, 50));
		banner.setListener(new UnityBannerListener());
		banner.load();

		View libgdx = initializeForView(wars, config);
		setContentView(R.layout.main);
		RelativeLayout root = findViewById(R.id.game);
		root.addView(libgdx, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		
		bannerContainer = findViewById(R.id.banner);
	}
	
	private static class UnityBannerListener implements BannerView.IListener {
		@Override
		public void onBannerLoaded(BannerView bannerAdView) {
			Log.d("SupportTest", "Banner Loaded");
		}
		
		@Override
		public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
			Log.d("SupportTest", "Banner Error");
			Log.d("SupportTest", errorInfo.errorMessage);
			// Note that the BannerErrorInfo object can indicate a no fill (see API documentation).
		}
		
		@Override
		public void onBannerClick(BannerView bannerAdView) {
			// Called when a banner is clicked.
		}
		
		@Override
		public void onBannerLeftApplication(BannerView bannerAdView) {
			// Called when the banner links out of the application.
		}
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
	
	public void displayAd() {
		if (UnityAds.isReady(placementId)) {
			UnityAds.show(this, placementId);
		}
	}
	
	public void displayBannerAd(final boolean display) {
		runOnUiThread(new Runnable() {
			public void run() {
				if(display){
					bannerContainer.removeAllViews();
					bannerContainer.addView(banner);
					UnityAds.show(AndroidLauncher.this, bannerId);
				} else {
					bannerContainer.removeAllViews();
				}
			}
		});
	}
}
