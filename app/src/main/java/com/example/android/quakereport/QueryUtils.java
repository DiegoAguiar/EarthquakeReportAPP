package com.example.android.quakereport;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    private static final String TAG = QueryUtils.class.getSimpleName();

    /** Sample JSON response for a USGS query */
    private static final String SERVICE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private static EarthquakeFilter mFilter = null;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(EarthquakeFilter filter) throws IOException {

        mFilter = filter;
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            String stringJson = makeHTTPRequest(SERVICE_URL);
            if(stringJson != null) {
                JSONObject jsonObject = new JSONObject(stringJson);
                JSONArray mFeatures = jsonObject.optJSONArray("features");

                int i = 0;
                while (i < mFeatures.length()) {
                    JSONObject mFeature = mFeatures.optJSONObject(i);
                    JSONObject mProperties = mFeature.optJSONObject("properties");
                    Double mag = mProperties.optDouble("mag");
                    String location = mProperties.optString("place");
                    Long dataMili = mProperties.getLong("time");
                    Date data = new Date(dataMili);
                    int magnitudeColor = getMagnitudeColor(mag);
                    String url = mProperties.getString("url");

                    Earthquake mEvent = new Earthquake(mag, location, data, magnitudeColor, url);
                    earthquakes.add(mEvent);
                    i++;
                }
            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static int getMagnitudeColor(Double magnitude){
        int index;
        int retorno;
        index = (int) Math.floor(magnitude);
        switch (index){
            case 0:
            case 1:
                retorno = R.color.magnitude1;
                break;
            case 2:
                retorno = R.color.magnitude2;
                break;
            case 3:
                retorno = R.color.magnitude3;
                break;
            case 4:
                retorno = R.color.magnitude4;
                break;
            case 5:
                retorno = R.color.magnitude5;
                break;
            case 6:
                retorno = R.color.magnitude6;
                break;
            case 7:
                retorno = R.color.magnitude7;
                break;
            case 8:
                retorno = R.color.magnitude8;
                break;
            case 9:
                retorno = R.color.magnitude9;
                break;
            default:
                retorno = R.color.magnitude10plus;
                break;
        }
        return retorno;
    }

    private static String makeHTTPRequest(String stringUrl) throws IOException {
        String jsonResult = null;
        if(stringUrl == null){
            return null;
        }
        URL url = createURL(stringUrl);
        HttpURLConnection urlConcection = null;
        InputStream inputStream = null;
        try {
            urlConcection = (HttpURLConnection) url.openConnection();
            urlConcection.setConnectTimeout(15000);
            urlConcection.setReadTimeout(10000);
            urlConcection.setRequestMethod("GET");
            urlConcection.connect();

            if(urlConcection.getResponseCode() == 200){
                inputStream = urlConcection.getInputStream();
                jsonResult = readFromStream(inputStream);
            }else{
                Log.e(TAG, "Error response code: " + urlConcection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConcection != null){
                urlConcection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResult;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String stringUrl){
        Uri baseURi = Uri.parse(stringUrl);
        Uri.Builder builder = baseURi.buildUpon();
        builder.appendQueryParameter("format",mFilter.getFormat());
        builder.appendQueryParameter("minmag",mFilter.getMinMagnitude());
        builder.appendQueryParameter("limit",mFilter.getLimit());
        builder.appendQueryParameter("orderby",mFilter.getOrderBy());
        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG,"Erro ao montar URL",e);
        }
        return url;
    }

}