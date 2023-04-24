package com.elkron.cordova.plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ElkronApiRequest {

	/** connect timeout in seconds */
	static final int HTTP_CONNECT_TIMEOUT = 5;

	String endpoint = null;
	String sessionCookie = null;

	public ElkronApiRequest(String endpoint) {
		this.endpoint = endpoint;

	}

	public ElkronApiRequest() {
		this("https://www.cloud.elkron.com/tool");
	}

	public ElkronApiResponse exec(String url, String method, String contentType, boolean withCredentials, String data)
			throws ElkronApiException {

		int code = -1;
		StringBuffer response = new StringBuffer();

		try {
			if (!url.startsWith("/"))
				url = "/" + url;
			URL address = new URL(endpoint + url);
			HttpsURLConnection conn = (HttpsURLConnection) address.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("Content-Language", "en-US");
			conn.setRequestProperty("charset", "utf-8");
			if (data != null && data.length() > 0) {
				conn.setRequestProperty("Content-Length", "" + data.getBytes().length);
			}
			if (withCredentials && sessionCookie != null && sessionCookie.length() > 0) {
				conn.setRequestProperty("Cookie", sessionCookie);
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "close");
			conn.setConnectTimeout(1000 * HTTP_CONNECT_TIMEOUT);
			conn.connect();

			// write posted data if given
			if (data != null && data.length() > 0) {
				try (OutputStream os = conn.getOutputStream()) {
					byte[] input = data.getBytes("utf-8");
					os.write(input, 0, input.length);
				}
			}

			// read http repsonse code from server
			code = conn.getResponseCode();

			// read headers
			for (int i = 0;; i++) {
				String headerName = conn.getHeaderFieldKey(i);
				String headerValue = conn.getHeaderField(i);
				if (headerName == null && headerValue == null) {
					// no more headers ...
					break;
				}
				if (headerName != null && headerName.equals("Set-Cookie") 
						&& headerValue.startsWith("session=")) {
					this.sessionCookie = headerValue;
				}
			}

			// read body
			BufferedReader br = code == 200 ? 
					new BufferedReader(new InputStreamReader(conn.getInputStream()))
					: new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line).append("\n");
			}
			br.close();

		} catch (Throwable t) {
			throw new ElkronApiException(t);
		}

		return new ElkronApiResponse(code, response.toString());

	}
	
	public ElkronApiResponse exec(String url, String method, String contentType, boolean withCredentials)
			throws ElkronApiException {
		return exec(url, method, contentType, withCredentials, null);
	}

}
