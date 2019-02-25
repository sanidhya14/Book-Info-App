package com.example.android.whowroteit;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;
    private WeakReference<TextView> mdescription;


    FetchBook(TextView titleText, TextView authorText, TextView describe) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
        this.mdescription = new WeakReference<>(describe);


    }

    @Override
    protected String doInBackground(String... strings) {

        return NetworkUtils.getBookInfo(strings[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null, authors = null, description = null;
            while (i < itemsArray.length() && title == null && authors == null && description == null) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    description = volumeInfo.getString("description");
                    if (title != null && authors != null && description != null) {
                        mTitleText.get().setText(title);
                        mAuthorText.get().setText(authors);
                        mdescription.get().setText(description);

                    } else {
                        mTitleText.get().setText(R.string.no_results);
                        mAuthorText.get().setText("");
                        mdescription.get().setText("");
                    }
                } catch (Exception e) {
                    mTitleText.get().setText(R.string.no_results);
                    mAuthorText.get().setText("");
                    mdescription.get().setText("");
                    e.printStackTrace();
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
