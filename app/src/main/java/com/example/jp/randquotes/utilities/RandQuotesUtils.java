package com.example.jp.randquotes.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandQuotesUtils {

    private static final String TAG = "RandQuotesUtils: ";

    public static final String RANDQUOTES_BASE_URL = "https://talaikis.com/api/quotes/random/";

    public interface AsyncResponse {
        void processFinish(String output1, String output2);
    }

    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        // Callback interface
        public AsyncResponse delegate = null;

        public placeIdTask(AsyncResponse asyncResponse) {
            // Assigning callback interface through constructor
            delegate = asyncResponse;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonQuotes = null;

            try {
                jsonQuotes = getQuotesJSON();

            } catch (Exception e) {
                Log.e("ERROR: ", "Cannot process JSON results ", e);
            }

            return jsonQuotes;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if(json != null) {
                    String quote = json.getString("quote");
                    String author = json.getString("author");

                    Log.d(TAG, "Quote is: " + quote + " and Author is " + author);

                    delegate.processFinish(quote, author);
                }
                else {
                    Log.e(TAG, "onPostExecute() json returned null");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Failed to get JSON data");
            }
        }

        public static JSONObject getQuotesJSON() {
            try {
                URL url = new URL(String.format(RANDQUOTES_BASE_URL));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while((tmp = reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());

                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
