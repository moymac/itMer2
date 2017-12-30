package com.moymac.meritapp;

/**
 * Created by moymac on 11/18/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moymac.meritapp.Adapters.TemplateAdapterRV;
import com.moymac.meritapp.Models.TemplateItem;
import com.moymac.meritapp.Models.Templates;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private ApiInterface apiInterface;
    private RecyclerView theListView;
    private TemplateAdapterRV templateAdapter;
    private List<TemplateItem> templateList;


    int catID;
    ProgressBar progressBar;

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        progressBar = v.findViewById(R.id.fragment_progressbar);

        Bundle args = getArguments();
        catID = args.getInt("Id");
        int catOrdinal = args.getInt("Ordinal");
        String catName = args.getString("Name");
        String catDesc = args.getString("Desc");
        String catOwner = args.getString("Owner");
        int catViews = args.getInt("Views");

        templateList = new ArrayList<>();

        createApiInterface();
        progressBar.setVisibility(View.VISIBLE);

        apiInterface.getTemplates(catID).enqueue(templatesCallback);


        // Get the application context
        mContext = getContext();

        // Get the widgets reference from XML layout
        mRelativeLayout = v.findViewById(R.id.relative_layout);

        // get our list view
        theListView = v.findViewById(R.id.mainListView);
        templateAdapter = new TemplateAdapterRV(getContext(), templateList);

//        templateAdapter = new TemplateAdapterRV(getContext(),templateList, new CustomItemClickListener(){
//            @Override
//            public void onItemClick(View v, int position) {
//                theListView.smoothScrollToPosition(position);
//
//
//
//                Toast.makeText(getContext(),"onitemclick", Toast.LENGTH_SHORT);
//            }
//
//
//        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        theListView.setLayoutManager(mLayoutManager);
        //theListView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        theListView.setItemAnimator(new DefaultItemAnimator());
        theListView.setAdapter(templateAdapter);


        // SwipeRefreshLayout
        mSwipeRefreshLayout = v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        return v;
    }
    @Override
    public void onRefresh() {
        templateList.clear();
        // Fetching data from server
        apiInterface.getTemplates(catID).enqueue(templatesCallback);
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

    Callback<List<Templates>> templatesCallback = new Callback<List<Templates>>() {
        @Override
        public void onResponse(@NonNull Call<List<Templates>> call, @NonNull final Response<List<Templates>> response) {
            progressBar.setVisibility(View.GONE);
            final List<Templates> responseList = response.body();
            for (Templates template : responseList){

                //Log.e("Id", String.valueOf(template.getId()));
                //Log.e("Category", String.valueOf(template.getCategory()));
                //Log.e("Ordinal", String.valueOf(template.getOrdinal()));
                //Log.e("Name", template.getName());
                //Log.e("Description", template.getDescription());
                //Log.e("Owner", template.getOwner());
                //Log.e("Views", String.valueOf(template.getViews()));

                templateList.add(new TemplateItem(template.getId(),"https://image.flaticon.com/icons/png/128/181/181549.png",template.getName(), "author", template.getDescription(),"EASY",1,30, "20 mins"));


            }
           // templateList.add(new TemplateItem(5,"https://image.flaticon.com/icons/png/128/181/181549.png","dummy template", "dummy author", "this is just a dummy template","EASY",1,30, "20 mins"));
            mSwipeRefreshLayout.setRefreshing(false);

            templateAdapter.notifyDataSetChanged();

        }

        @Override
        public void onFailure(@NonNull Call<List<Templates>> call, @NonNull Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"TEMPLATES CALL FAILED",Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);

        }


    };

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}