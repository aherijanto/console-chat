/*-----------------------------
Created by bistique 2020
 */
package Network;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ConnectURI {
    private final String USER_AGENT = "Mozilla/5.0";
    public StringBuffer response;
    public static URL buildURL(String urlQuery){
        URL url=null;
        try {
            url = new URL(urlQuery.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public void postJSON(URL address, String jsonData) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) address.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "UTF-8");

        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(jsonData.toString());
        outputStreamWriter.flush();

        int responseCode = con.getResponseCode();
        //System.out.println("Response Code : " + responseCode+"\n");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
           response.append(inputLine);
        }
        in.close();

        //System.out.println("Responses are = "+response.toString());
    }
}
