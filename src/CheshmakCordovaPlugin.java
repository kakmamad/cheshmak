package me.cheshmak.cordova;

import android.app.Activity;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.util.Log;
import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.advertise.CheshmakInterstitialAd;
import me.cheshmak.android.sdk.advertise.InterstitialCallback;

public class CheshmakCordovaPlugin extends CordovaPlugin {
    private static final String TAG = "cheshmak_cordova";
    public CordovaInterface cordova = null;
    private Activity activity = null;
    private CallbackContext interstitialAdcallbackContext = null;
    private CheshmakInterstitialAd cheshmakInterstitialAd = null;



    @Override
    public void initialize(CordovaInterface initCordova, CordovaWebView webView) {
        Log.e(TAG, "CheshmakCordovaPlugin initialize");
        cordova = initCordova;
        activity = this.cordova.getActivity();
        try {
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    cheshmakInterstitialAd = new CheshmakInterstitialAd(activity);
                    cheshmakInterstitialAd.setCallback(new InterstitialCallback() {

// PluginResult result = new PluginResult(PluginResult.Status.ERROR, "YOUR_ERROR_MESSAGE");

                        @Override
                        public void onAdLoaded() {

                            Log.d(TAG, "onAdLoaded InterstitialCallback");
                            if (interstitialAdcallbackContext != null) {
//                                interstitialAdcallbackContext.success("onAdLoaded");
                                PluginResult result = new PluginResult(PluginResult.Status.OK, "onAdLoaded");
                                result.setKeepCallback(true);
                                interstitialAdcallbackContext.sendPluginResult(result);
                            }
                        }

                        @Override
                        public void onAdOpened() {
                            Log.d(TAG, "onAdOpened InterstitialCallback");
                            if (interstitialAdcallbackContext != null) {
//                                interstitialAdcallbackContext.success("onAdOpened");
                                PluginResult result = new PluginResult(PluginResult.Status.OK, "onAdOpened");
                                result.setKeepCallback(true);
                                interstitialAdcallbackContext.sendPluginResult(result);
                            }
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            Log.d(TAG, "onAdFailedToLoad InterstitialCallback");
                            if (interstitialAdcallbackContext != null) {
//                                interstitialAdcallbackContext.success("onAdFailedToLoad");
                                PluginResult result = new PluginResult(PluginResult.Status.OK, "onAdFailedToLoad");
                                result.setKeepCallback(true);
                                interstitialAdcallbackContext.sendPluginResult(result);
                            }
                        }

                        @Override
                        public void onAdClicked() {
                            Log.d(TAG, "onAdClicked InterstitialCallback");
                            if (interstitialAdcallbackContext != null) {
//                                interstitialAdcallbackContext.success("onAdClicked");
                                PluginResult result = new PluginResult(PluginResult.Status.OK, "onAdClicked");
                                result.setKeepCallback(true);
                                interstitialAdcallbackContext.sendPluginResult(result);
                            }

                        }

                        @Override
                        public void onAdLeftApplication() {

                        }

                        @Override
                        public void onAdClosed() {

                        }

                        @Override
                        public void onAdImpression() {

                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        super.initialize(cordova, webView);

//

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("addTag".equals(action)) {
            addTag(args, callbackContext);
            return true;
        }
        if ("deleteTag".equals(action)) {
            deleteTag(args, callbackContext);
            return true;
        }
        if ("deleteAllTags".equals(action)) {
            deleteAllTags(callbackContext);
            return true;
        }
        if ("startView".equals(action)) {
            startView(callbackContext);
            return true;
        }
        if ("stopView".equals(action)) {
            stopView(callbackContext);
            return true;
        }

//        if ("setTest".equals(action)) {
//            setTest(args, callbackContext);
//            return true;
//        }
        if ("getData".equals(action)) {
            getData(args, callbackContext);
            return true;
        }
        if ("showInterstitialAds".equals(action)) {
            showInterstitialAds(args, callbackContext);
            return true;
        }
        if ("disableAdvertises".equals(action)) {
            disableAdvertises(args, callbackContext);
            return true;
        }
        if ("enableAdvertises".equals(action)) {
            enableAdvertises(args, callbackContext);
            return true;
        }
        if ("getInterstitialEvents".equals(action)) {
            getInterstitialEvents(args, callbackContext);
            return true;
        }


        return false;
    }

    public void addTag(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        try {
            String tagName = args.getString(0);
            Cheshmak.sendTag(tagName);
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    public void deleteTag(JSONArray args, CallbackContext callbackContext) {
        try {
            String tagName = args.getString(0);
            Cheshmak.deleteTag(tagName);
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    public void deleteAllTags(final CallbackContext callbackContext) {
        Cheshmak.deleteAllTags();
        callbackContext.success();
    }

    public void startView(final CallbackContext callbackContext) {
        Cheshmak.startView(cordova.getActivity().toString());
        callbackContext.success();
    }

    public void stopView(CallbackContext callbackContext) {
        Cheshmak.stopView(cordova.getActivity().toString());
        callbackContext.success();
    }

//
//    public void setTest(JSONArray args, final CallbackContext callbackContext) throws JSONException {
//        try {
//            boolean test = args.getBoolean(0);
//            Cheshmak.isTestDevice(test);
//            callbackContext.success();
//        } catch (JSONException e) {
//            callbackContext.error(e.getMessage());
//        }
//    }

    public void getData(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Intent intent = cordova.getActivity().getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                callbackContext.success(intent.getExtras().getString("me.cheshmak.data"));
            }
        } else {
            callbackContext.error(0);
        }
    }


    //region Advertise

    public void showInterstitialAds(JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if (cheshmakInterstitialAd != null) {
            Log.d("CheshmakCordovaPlugin", " showInterstitialAds is not null");
            try {
                this.cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (cheshmakInterstitialAd.isLoaded()) {
                            cheshmakInterstitialAd.show();
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Log.d("CheshmakCordovaPlugin", " showInterstitialAds is  null !");
        }


        callbackContext.success();
    }

    public void disableAdvertises(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Cheshmak.disableAdvertises();
        callbackContext.success();
    }

    public void enableAdvertises(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Cheshmak.enableAdvertises();
        callbackContext.success();
    }
    public void getInterstitialEvents(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        interstitialAdcallbackContext = callbackContext;
        //callbackContext.success();
    }
}