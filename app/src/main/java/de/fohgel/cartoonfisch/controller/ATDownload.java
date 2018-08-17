package de.fohgel.cartoonfisch.controller;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class ATDownload extends AsyncTask<String, String, String> {

    public ATDownload(String url, String object) {
        this.execute(url, object);
    }

    /**
     * Before starting background thread
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... sth) {
        int count;
            String root = Environment.getExternalStorageDirectory().toString();

            if (new File(root + sth[1]).exists()) {
                System.out.printf("File already on disk");
                return null;
            }

        try {
            System.out.println("Downloading");
            URL url = new URL(sth[0] + sth[1]);

            URLConnection connection = url.openConnection();
            connection.connect();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(root + sth[1]);
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        System.out.println("Downloaded");
    }

}