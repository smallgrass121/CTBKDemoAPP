package com.tw.cathaybk.ctbkdemoapp.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequestTask extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    private HttpGetRequestListener listener;
    private String actionType;
    private String id = null;

    public HttpGetRequestTask(HttpGetRequestListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params){
        Log.i("HttpGetRequestTask doInBackground","start");

        id = params[0]; //  for img
        final String stringUrl = params[1];
        actionType = params[2]; //  for img

        if( (null == stringUrl || stringUrl.length() == 0) ||
                (null == actionType || actionType.length() == 0) ||
                ( (actionType.equals("IMG") && (null == id || id.length() == 0) ) )
        ){
            listener.onRequestFail("params error");
        }

        String result;
        String inputLine;
        BufferedReader reader = null;
        InputStreamReader streamReader = null;

        try {
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //fix java.net.ProtocolException: unexpected end of stream
            connection.setRequestProperty("Connection", "close");
            connection.connect();

            streamReader = new InputStreamReader(connection.getInputStream());

            reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            result = stringBuilder.toString();

        } catch(IOException e){
            e.printStackTrace();
            result = null;
            listener.onRequestFail(e.getMessage());
        } finally {
            if(null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onRequestFail(e.getMessage());
                }
            }
            if(null != streamReader){
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onRequestFail(e.getMessage());
                }
            }
        }
        Log.i("HttpGetRequestTask doInBackground","end");
        return result;
    }

    protected void onPostExecute(String result){
        Log.i("HttpGetRequestTask onPostExecute","");
        super.onPostExecute(result);
        if( null != result){
            Log.i("HttpGetRequestTask onPostExecute , result=", result);
            if(actionType.equals("API")){
                listener.onRequestFinish(result);
            }else if (actionType.equals("IMG")){
                listener.onImgRequestFinish(id, result);
            }else{
                listener.onRequestFail("actionType not match");
            }
        }else{
            listener.onRequestFail("result is empty");
        }
    }
}