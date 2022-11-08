//code adapted from https://steemit.com/android/@faisalamin/how-to-fetch-data-from-a-json-file-to-android-using-the-url-with-android-studio
package com.example.fetchrewardsapprenticeshipapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void, Void, Void>
{
    String result;

    //this method will retrieve the data from the URL, and onPostExecute will be called once it's finished
    @Override
    protected Void doInBackground(Void... voids)
    {
        try
        {
            URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null)
            {
                line = bufferedReader.readLine();
                result += line;
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute (Void aVoid)
    {
        super.onPostExecute(aVoid);
        DisplayDataActivity.thisActivity.SortAndDisplayData(result);
    }
}
