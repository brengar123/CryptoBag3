package com.example.cryptobag;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.example.cryptobag.Entities.CoinLoreResponse;
import com.example.cryptobag.Entities.Coin;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private CoinAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CoinAdapter(this, new ArrayList<Coin>(), mTwoPane);
        new GetCoinTask().execute();
    }
        private class GetCoinTask extends AsyncTask<Void, Void, List<Coin>> {
            @Override
            protected List<Coin> doInBackground(Void... voids) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.coinlore.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    CoinService service = retrofit.create(CoinService.class);
                    Call<CoinLoreResponse> coinsCall = service.getCoins();

                    Response<CoinLoreResponse> coinResponse = coinsCall.execute();
                    List<Coin> coins = coinResponse.body().getData();
                    return coins;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(List<Coin> coins) {
                mAdapter.setCoins(coins);
                mRecyclerView.setAdapter(mAdapter);
            }
    }
}
