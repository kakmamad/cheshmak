package me.cheshmak.cordova;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.io.IOException;

import org.apache.cordova.LOG;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public final class CheshmakConfigReader {

   public static final String LOGTAG = "CheshmakConfigReader";

   private static final String preferenceTag = "preference"; //<preference name="AppKey" value="foo" />


   private static final String cheshmakAppKey = "AppKey";
   private static final String adsTestMode = "adsTestMode";


   private List<String> supportedKeys = new ArrayList<String>(Arrays.asList(cheshmakAppKey, adsTestMode));
   private Map<String, String> configs;

   private static int getConfigXmlResourceId(Context context){
      return context.getResources().getIdentifier("config", "xml", context.getPackageName());
   }


   public CheshmakConfigReader(Context context, Integer configXmlResourceId) throws CheshmakConfigException{
      int configXmlResId = (configXmlResourceId == null ? getConfigXmlResourceId(context) : configXmlResourceId);

      configs = loadConfigsFromXml(context.getResources(), configXmlResId);
      if(!hasRequiredParams()){
         throw new CheshmakConfigException(cheshmakAppKey + " is missing! Please set them in config.xml");
      }
   }

   public String getAppKey(){
      return configs.get(cheshmakAppKey);
   }
   public Boolean isAdsTestMode(){
      String adsTestMode =  configs.get("adsTestMode");

      return adsTestMode != null && adsTestMode.equals("true");
   }

   public String get(String key){
      return configs.get(key);
   }

   public Boolean hasRequiredParams(){
      return !(getAppKey() == null   || getAppKey().isEmpty());
   }

   private String matchSupportedKeyName(String testKey){
      //
      // If key matches, return the version with correct casing.
      // If not, return null.
      // O(n) here is okay because this is a short list of just a few items
      for(String realKey : supportedKeys){
         if( realKey.equalsIgnoreCase(testKey)){
            return realKey;
         }
      }
      return null;
   }

   private Map<String, String> loadConfigsFromXml(Resources res, int configXmlResourceId){
      XmlResourceParser xrp = res.getXml(configXmlResourceId);

      Map<String, String> configs = new HashMap<String, String>();

      //
      // walk the config.xml tree and save all <preference> tags related to Parse.Push
      //
      try{
         xrp.next();
         while(xrp.getEventType() != XmlResourceParser.END_DOCUMENT){
            if(preferenceTag.equals(xrp.getName())){
               String key = matchSupportedKeyName(xrp.getAttributeValue(null, "name"));
               if(key != null){
                  configs.put(key, xrp.getAttributeValue(null, "value"));
               }
            }
            xrp.next();
         }
      } catch(XmlPullParserException ex){
         Log.e(LOGTAG, ex.toString());
      }  catch(IOException ex){
         Log.e(LOGTAG, ex.toString());
      }

      return configs;
   }
}
