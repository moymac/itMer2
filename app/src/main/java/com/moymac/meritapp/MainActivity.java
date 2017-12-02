package com.moymac.meritapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView testTV;
    private ApiInterface apiInterface;
    private FragmentTabHost mTabHost;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        createApiInterface();

        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getCategories().enqueue(categoriesCallback);


    }


    private void createApiInterface() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


//            case android.R.btn_post:
//                stepNum =
//                textInput =
//                apiInterface.postInputs(stepNum, textInput).enqueue(inputsCallback);
//                break;

        }
    }


    Callback<List<Categories>> categoriesCallback = new Callback<List<Categories>>() {
        @Override
        public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
            if (response.isSuccessful()) {
//
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("Id", String.valueOf(response.body().get(0).getId()));
                Log.e("Ordinal", String.valueOf(response.body().get(0).getOrdinal()));
                Log.e("Name", response.body().get(0).getName());
                Log.e("Desc", response.body().get(0).getDesc());
                Log.e("Owner", String.valueOf(response.body().get(0).getOwner()));
                Log.e("Views", String.valueOf(response.body().get(0).getViews()));
                mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
                mTabHost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);
                int i = 0;
                for (Categories category : response.body()) {
                    Bundle args = new Bundle();
                    args.putInt("Id", category.getId());
                    args.putInt("Ordinal", category.getOrdinal());
                    args.putString("Name", category.getName());
                    args.putString("Desc", category.getDesc());
                    args.putInt("Owner", category.getOwner());
                    args.putInt("Views", category.getViews());
                    mTabHost.addTab(
                            mTabHost.newTabSpec("tag" + i).setIndicator(getTabIndicator(mTabHost.getContext(), category.getName(), android.R.drawable.star_on)),
                            FragmentTab.class, args);
                    i++;
                }
//
            }
        }

        @Override
        public void onFailure(Call<List<Categories>> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);

        }
    };
    private View getTabIndicator(Context context, String title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }
}