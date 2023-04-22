package com.elkron.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ElkronApiPlugin extends CordovaPlugin {
	

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

		try {
		
			if (action.equalsIgnoreCase("doRequest")) {
			
				JSONObject options = args.getJSONObject(0);
				String url = options.getString("url");
				String method = options.isNull("type") ?  "GET" : options.getString("type").toUpperCase();
				String contentType = options.isNull("contentType") ? "application/json" : options.getString("contentType");
				
				int responseCode = -1;
				StringBuffer responseText = new StringBuffer();

				//
				// TBD ..
				//
				responseCode = 200;
				
				boolean ok = (responseCode == 200 || responseCode == 201 || responseCode == 202 || responseCode == 204);
								
				if (responseText.length() > 0 && responseText.toString().trim().startsWith("{")) {
					callbackContext.success( new JSONObject( responseText.toString() ) );				
					return true;
				}
				else if (responseText.length() > 0 && responseText.toString().trim().startsWith("[")) {
					callbackContext.success( new JSONArray( responseText.toString() ) );				
					return true;
				}
				else {
					callbackContext.success( responseText.toString() );				
					return true;					
				}

			}			
			

		} catch (JSONException e) {
			callbackContext.error("Error encountered: " + e.getMessage());
			return false;
		}
	
		// Send a positive result to the callbackContext
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
		callbackContext.sendPluginResult(pluginResult);
		
		return true;
	}

}
