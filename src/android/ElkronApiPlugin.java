package com.elkron.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ElkronApiPlugin extends CordovaPlugin {
	
	private ElkronApiRequest client = null;

	private synchronized ElkronApiRequest getClient() {
		if (client == null)
			client = new ElkronApiRequest();
		return client;
	}
	
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

		try {
		
			if (action.equalsIgnoreCase("doRequest")) {
			
				JSONObject options = args.getJSONObject(0);
				String url = options.getString("url");
				String method = options.isNull("type") ?  "GET" : options.getString("type").toUpperCase();
				String contentType = options.isNull("contentType") ? "application/json" : options.getString("contentType");
				String data = options.isNull("data") ?  null : options.getString("data");
				boolean withCredentials = (url.indexOf("private") >= 0);
				
				client = getClient();
				ElkronApiResponse r = client.exec(url, method, contentType, withCredentials, data);
			
				boolean ok = (r.code == 200 || r.code == 201 || r.code == 202 || r.code == 204);
								
				if (ok) {
					callbackContext.success( r.textResponse );				
					return true;					
				}
				else {
					callbackContext.error( r.textResponse );				
					return false;					
				}
			}			
			

		}
		catch (ElkronApiException e) {
			callbackContext.error("Error encountered: " + e.getMessage());
			return false;
		} 
		catch (JSONException e) {
			callbackContext.error("Error encountered: " + e.getMessage());
			return false;
		}
	
		PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR);
		callbackContext.sendPluginResult(pluginResult);		
		return false;
		
	}

}
