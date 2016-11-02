package com.agilecrmrestapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";
    private static String USERNAME = "ghanshyam.raut@agilecrm.com";
    private static String DOMAIN = "ghanshyam";
    private static String REST_API_KEY = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"Main activity run .... great");

        Button v = (Button)(findViewById(R.id.createContact));

        Button v1 = (Button)(findViewById(R.id.getContact));

        v.setOnClickListener(new View.OnClickListener() {
            private TextView result;
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button clicked successfully");
                String contactDetail = "{\"lead_score\":\"44\",  \"tags\":[\"tag1\", \"tag2\"], \"properties\":[{\"type\":\"SYSTEM\", \"name\":\"email\",\"value\":\"jason2088@gmail.com\"}, {\"type\":\"SYSTEM\", \"name\":\"first_name\", \"value\":\"First_name\"}, {\"type\":\"SYSTEM\", \"name\":\"last_name\", \"value\":\"Last_name\"}]}";
                try{

                    new RestOperation().execute("POST","contacts","application/json",contactDetail);
                }
                catch(Exception ex)
                {
                    Log.e("MYAPP", "exception", ex);
                }

            }
        });

        v1.setOnClickListener(new View.OnClickListener() {
            private TextView result;
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Button clicked successfully");

                try{

                    new RestOperation().execute("GET","contacts/5667450989314048","application/json",null);
                }
                catch(Exception ex)
                {
                    Log.e("MYAPP", "exception", ex);
                }

            }
        });


    }

    private class RestOperation extends AsyncTask< String, Void, Void> {

        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String data = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG,"Please Wait1 .......................");
        }

        @Override
        protected Void doInBackground(String... args) {
            String methodType = args[0];
            String urlPattern = args[1];
            String contentType = args[2];
            String dataInputRequest = args[3];

            String baseURL = "https://"+DOMAIN+".agilecrm.com/dev/api/"+urlPattern+"";

            String text = "";
            BufferedReader reader=null;
            int responseCode = 0;
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                // Defined URL  where to send data
                URL url = new URL(baseURL);

                // Establish connection with URL
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                final String basicAuth = "Basic " + Base64.encodeToString("ghanshyam.raut@agilecrm.com:123456".getBytes(), Base64.NO_WRAP);

                conn.setRequestProperty ("Authorization", basicAuth);
                conn.setRequestMethod(methodType);
                conn.setRequestProperty("Content-Type", contentType);
                conn.setRequestProperty("Accept", "application/json");

                switch (methodType){
                    case "GET":
                        // Get the server response

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        // Get the server response
                        Log.i(TAG, String.valueOf(conn.getResponseCode()));

                        while((line = reader.readLine()) != null)
                        {
                            // Append server response in string
                            sb.append(line + "\n");
                        }
                        reader.close();

                        text = sb.toString();
                        Log.i(TAG,text);
                        break;
                    case "POST":
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        OutputStream os = conn.getOutputStream();
                        os.write(dataInputRequest.getBytes("UTF-8"));
                        os.flush();
                        os.close();



                        // Get the server response
                        Log.i(TAG, String.valueOf(conn.getResponseCode()));

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        // Read Server Response
                        while((line = reader.readLine()) != null)
                        {
                            // Append server response in string
                            sb.append(line + "\n");
                        }
                        reader.close();

                        text = sb.toString();
                        Log.i(TAG,text);
                        break;
                    case "PUT":
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        OutputStream os1 = conn.getOutputStream();
                        os1.write(dataInputRequest.getBytes("UTF-8"));
                        os1.flush();
                        os1.close();



                        // Get the server response
                        Log.i(TAG, String.valueOf(conn.getResponseCode()));

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        // Read Server Response
                        while((line = reader.readLine()) != null)
                        {
                            // Append server response in string
                            sb.append(line + "\n");
                        }
                        reader.close();

                        text = sb.toString();
                        Log.i(TAG,text);
                        break;
                    case "DELETE":
                        // Get the server response

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        // Get the server response
                        Log.i(TAG, String.valueOf(conn.getResponseCode()));

                        while((line = reader.readLine()) != null)
                        {
                            // Append server response in string
                            sb.append(line + "\n");
                        }
                        reader.close();

                        text = sb.toString();
                        Log.i(TAG,text);
                        break;
                    default:
                        Log.i(TAG,"method type = Nothing ");
                        break;
                }
            }catch (Exception e){
                Log.e("MYAPP", "exception", e);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if(error !=null){
                Log.i(TAG,"Failed.......................");
            }else{
                Log.i(TAG,"Success.......................");
            }
        }
    }



}
