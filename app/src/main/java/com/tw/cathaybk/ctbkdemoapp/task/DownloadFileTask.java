package com.tw.cathaybk.ctbkdemoapp.task;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class DownloadFileTask extends AsyncTask<String, Void, Bitmap>{

    private Context context;
    private DownloadFileListener listener;

    private String id = "";
    private String folder = "";

    public DownloadFileTask(Context context, DownloadFileListener listener){
        this.context = context;
        this.listener = listener;
    }

    // Before the tasks execution
    protected void onPreExecute(){

    }

    protected Bitmap doInBackground(String...params){
        URL url;
        HttpURLConnection connection = null;

        try{
            folder = params[0];
            id = params[1];
            url = new URL(params[2]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
            return bmp;

        }catch(IOException e){
            e.printStackTrace();
            listener.onDownloadFail(e.getMessage());
        }finally{
            connection.disconnect();
        }
        return null;
    }

    protected void onPostExecute(Bitmap result){
        // Hide the progress dialog
        //mProgressDialog.dismiss();

        if(result!=null){
            Uri imageInternalUri = saveImageToInternalStorage(result);
            listener.onDownloadFinish(id, imageInternalUri.getPath());
        }else {
            listener.onDownloadFail("DownloadFileTask onPostExecute error");
        }
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap){
        ContextWrapper wrapper = new ContextWrapper(context);
        File file = wrapper.getDir(folder, MODE_PRIVATE);

        file = new File(file, id +".jpg");

        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        return savedImageURI;
    }
}