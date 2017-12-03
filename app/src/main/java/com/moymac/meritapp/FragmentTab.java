package com.moymac.meritapp;

/**
 * Created by moymac on 11/18/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moymac.meritapp.Adapters.StepsAdapter;
import com.moymac.meritapp.Adapters.TemplateCardAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTab extends Fragment {

    private Context mContext;
    private ApiInterface apiInterface;
    private ListView theListView;
    private RelativeLayout stepsRelativeLayout;
    ProgressBar progressBar;
    Context currentCardContext;

    RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        progressBar = v.findViewById(R.id.fragment_progressbar);

        Bundle args = getArguments();
        int catID = args.getInt("Id");
        int catOrdinal = args.getInt("Ordinal");
        String catName = args.getString("Name");
        String catDesc = args.getString("Desc");
        String catOwner = args.getString("Owner");
        int catViews = args.getInt("Views");

        createApiInterface();
        progressBar.setVisibility(View.VISIBLE);

        apiInterface.getTemplates(catID).enqueue(templatesCallback);


        // Get the application context
        mContext = getContext();

        // Get the widgets reference from XML layout
        mRelativeLayout = v.findViewById(R.id.relative_layout);

        // get our list view
        theListView = v.findViewById(R.id.mainListView);


        return v;
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
        public void onResponse(Call<List<Templates>> call, final Response<List<Templates>> response) {
            progressBar.setVisibility(View.GONE);
            final List<TemplateItem> items = new ArrayList<TemplateItem>();
            final List<Templates> responseList = response.body();
            for (Templates template : responseList){

                Log.e("Id", String.valueOf(template.getId()));
                Log.e("Category", String.valueOf(template.getCategory()));
                Log.e("Ordinal", String.valueOf(template.getOrdinal()));
                Log.e("Name", template.getName());
                Log.e("Description", template.getDescription());
                Log.e("Owner", template.getOwner());
                Log.e("Views", String.valueOf(template.getViews()));

                items.add(new TemplateItem("https://image.flaticon.com/icons/png/128/181/181549.png",template.getName(), "author", template.getDescription(),"EASY",1,30, "20 mins"));

            }
            final TemplateCardAdapter adapter = new TemplateCardAdapter(getActivity(), items);
            theListView.setAdapter(adapter);
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentCardContext = view.getContext();
                    apiInterface.getSteps(responseList.get(position).id).enqueue(stepsCallback);


                }
            });
        }

        @Override
        public void onFailure(Call<List<Templates>> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"TEMPLATES CALL FAILED",Toast.LENGTH_SHORT).show();
        }
    };

    Callback<List<Steps>> stepsCallback = new Callback<List<Steps>>() {
        @Override
        public void onResponse(Call<List<Steps>> call, Response<List<Steps>> response) {
            progressBar.setVisibility(View.INVISIBLE);

            final List<Steps> responseList = response.body();
            final List<StepItem> items = new ArrayList<>();
            Log.e("the call", call.toString());
            for(Steps step : responseList){
                //http://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png
               items.add(new StepItem("http://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png",step.getName(),"HARD",3,1.0,"20 mins","3 CHILDREN STEPS","•Brainstorm ideas for a subject\\n•Understand why you chose them\\n•Communicate the core value",0.99));

            }
            stepsRelativeLayout = theListView.findViewById(R.id.template_card_view);
            final StepsAdapter adapter = new StepsAdapter(currentCardContext, items);


            Toast.makeText(getActivity(),"STEPS CALL SUCCESSFUL!!",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<List<Steps>> call, Throwable t) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"STEPS CALL FAILED",Toast.LENGTH_SHORT).show();
        }
    };
}