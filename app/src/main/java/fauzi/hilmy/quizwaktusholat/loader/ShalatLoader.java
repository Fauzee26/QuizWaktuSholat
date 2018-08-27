package fauzi.hilmy.quizwaktusholat.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import fauzi.hilmy.quizwaktusholat.data.Shalat;

public class ShalatLoader extends AsyncTaskLoader<ArrayList<Shalat>> {
    private ArrayList<Shalat> mdata;
    private Boolean result = false;

    public ShalatLoader(@NonNull Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (result)
            deliverResult(mdata);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Shalat> data) {
        data = mdata;
        result = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (result) {
            onReleaseResource(mdata);
            mdata = null;
            result = false;
        }
    }

    private static final String API_KEY = "d1aa7cdf5e8c0d2d7f1f47811497a732";

    @Nullable
    @Override
    public ArrayList<Shalat> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Shalat> shalatArrayList = new ArrayList<>();
        String url = "http://muslimsalat.com/jakarta/daily.json?key=" + API_KEY + "&jsoncallback=?";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject shalat = results.getJSONObject(i);
                        Shalat shalatItems = new Shalat(shalat);
                        Log.d("State", "on success/Now Showing :" + shalatItems.getState());
                        shalatArrayList.add(shalatItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Cek Koneksi Dulu", Toast.LENGTH_SHORT).show();
                Log.e("Error: ", "cause ", error);
            }
        });

        return shalatArrayList;
    }

    private void onReleaseResource(ArrayList<Shalat> data) {

    }
}
