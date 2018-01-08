package com.moymac.meritapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moymac.meritapp.Adapters.InputsAdapterRV;
import com.moymac.meritapp.Adapters.ProjectStepAdapter;
import com.moymac.meritapp.Models.Inputs;
import com.moymac.meritapp.Models.InputsItem;
import com.moymac.meritapp.Models.Steps;
import com.moymac.meritapp.Models.TemplateItem;
import com.moymac.meritapp.Models.Templates;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Composing extends AppCompatActivity  implements View.OnClickListener{
    String projectTitle;
    List<Integer> projectChildIds = new ArrayList<>();
    List<String> projectChildNames = new ArrayList<>();
    List<String> projectChildTexts = new ArrayList<>();
    List<Integer> projectChildTypes = new ArrayList<>();

  //  Object[] theInputObjectList;
    private ApiInterface apiInterface;

    int currentStepPosition;
    int currentInputPosition = 0;

    RecyclerView compositionStepsRV;
    ProjectStepAdapter projectStepAdapter;
    List<Steps> projectStepsList;

    Toolbar toolbar;
    ImageView composingImageview;
    TextView toolbarTV;
    ImageButton toolbarButton;
    String mCurrentPhotoPath;
    //EditText composingText;

    ImageButton photoButton;
    ImageButton galleryButton;
    ImageButton recordButton;
    Button sendButton;

    RecyclerView compositionInputsRV;
    InputsAdapterRV inputsAdapter;
    List<InputsItem> projectInputsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing);

        toolbar = findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        toolbarButton = findViewById(R.id.toolbar_button);
        toolbarButton.setOnClickListener(this);
        toolbarTV = findViewById(R.id.toolbar_text);
//
//        photoButton = findViewById(R.id.composing_camera);
//        photoButton.setOnClickListener(this);
//        galleryButton = findViewById(R.id.composing_gallery);
//        galleryButton.setOnClickListener(this);
//        recordButton = findViewById(R.id.composing_audio);
//        recordButton.setOnClickListener(this);
//        sendButton = findViewById(R.id.button_chatbox_send);
//        sendButton.setOnClickListener(this);

   //     composingImageview = findViewById(R.id.composing_imageview);
    //    composingText = findViewById(R.id.composing_editText);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                projectTitle= null;
                projectChildIds = null;
                projectChildNames = null;
                projectChildTexts = null;
                projectChildTypes = null;
            } else {
                projectTitle = extras.getString("parentTitle");
                projectChildIds = extras.getIntegerArrayList("childIds");
                projectChildNames = extras.getStringArrayList("childNames");
                projectChildTexts = extras.getStringArrayList("childTexts");
                projectChildTypes = extras.getIntegerArrayList("childTypes");
            }
        } else {
            projectTitle= (String) savedInstanceState.getSerializable("parentTitle");
            projectChildIds = (List<Integer>) savedInstanceState.getSerializable("childIds");
            projectChildNames = (List<String>) savedInstanceState.getSerializable("childNames");
            projectChildTexts = (List<String>) savedInstanceState.getSerializable("childTexts");
            projectChildTypes = (List<Integer>) savedInstanceState.getSerializable("childTypes");

        }
   //     theInputObjectList = new Object[projectChildIds.size()];
   //     for(int i=0;i<projectChildIds.size();i++){
    //        theInputObjectList[i]="";
     //   }

        toolbarTV.setText(projectTitle);

        createApiInterface();



        projectInputsList = new ArrayList<>();
        ///make the call for inputs and if empty, create FIRST INPUT
        projectInputsList.add(new InputsItem(projectChildIds.get(0),1,""));
        compositionInputsRV = findViewById(R.id.composition_main_RV);

        ///String[] theDataset = new String[20];
        //theDataset[0] = "somethin";
        inputsAdapter = new InputsAdapterRV(this, projectInputsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
               // Log.e("HuboClick", v.toString() + String.valueOf(position));

                currentInputPosition = position;
                currentStepPosition = position;
                compositionStepsRV.smoothScrollToPosition(currentStepPosition);
                compositionInputsRV.smoothScrollToPosition(currentInputPosition);


//                EditText theEditText = v.findViewById(R.id.input_content_et);
//                ImageView theImageView = v.findViewById(R.id.input_content_iv);
//                VideoView theVideoView = v.findViewById(R.id.input_content_video);
//                if(theEditText.getVisibility()==View.VISIBLE) inputCurrentlyWorkingView = theEditText;
//                if(theImageView.getVisibility()==View.VISIBLE) inputCurrentlyWorkingView = theImageView;
//                if(theVideoView.getVisibility()==View.VISIBLE) inputCurrentlyWorkingView = theVideoView;

            }
        });
        RecyclerView.LayoutManager xLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false);
        compositionInputsRV.setLayoutManager(xLayoutManager);
        compositionInputsRV.setItemAnimator(new DefaultItemAnimator());
        compositionInputsRV.setAdapter(inputsAdapter);


        projectStepsList = new ArrayList<>();
        compositionStepsRV = findViewById(R.id.composition_steps_rv);
        for (int i =0;i<projectChildIds.size();i++) {
            projectStepsList.add(new Steps(projectChildIds.get(i),projectChildNames.get(i),projectChildTexts.get(i),projectChildTypes.get(i)));
        }
        projectStepAdapter = new ProjectStepAdapter(this, projectStepsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentStepPosition = position;
                currentInputPosition = position;
                compositionInputsRV.getChildAt(position).requestFocus();
                //composingText.setHint(projectChildTexts.get(position));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false);
        compositionStepsRV.setLayoutManager(mLayoutManager);
        compositionStepsRV.setItemAnimator(new DefaultItemAnimator());
        compositionStepsRV.setAdapter(projectStepAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(compositionStepsRV);

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            composingImageview.setImageBitmap(imageBitmap);
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
//            case R.id.composing_camera:
//                dispatchTakePictureIntent();
//                break;
//            case R.id.composing_audio:
//                break;
//            case R.id.composing_gallery:
//                break;
            case R.id.button_chatbox_send:

                RelativeLayout tempRelativeLayout;

                for(int i =0;i<projectInputsList.size();i++){
                    tempRelativeLayout = (RelativeLayout) compositionInputsRV.getChildAt(i);
                    View inputCurrentlyWorkingView = tempRelativeLayout.findViewById(R.id.input_content_et);
                    if (inputCurrentlyWorkingView.hasFocus()){
                        currentInputPosition = i;
                    }

                }
                tempRelativeLayout = (RelativeLayout) compositionInputsRV.getChildAt(currentInputPosition);
                View inputCurrentlyWorkingView = tempRelativeLayout.findViewById(R.id.input_content_et);

                EditText currentlyWorkingET = (EditText) inputCurrentlyWorkingView;
                String inputString = currentlyWorkingET.getText().toString();
      //          theInputObjectList[currentInputPosition] = inputString;
               // Log.e("LO dEL EDITTEXT", String.valueOf(inputString));
                projectInputsList.get(currentInputPosition).setContent(inputString);
                apiInterface.postInputs(String.valueOf(projectStepsList.get(currentInputPosition).getId()),inputString).enqueue(inputsCallback);
                if (currentStepPosition < projectStepsList.size()-1 && projectInputsList.size() < projectStepsList.size()) {
                    Steps clickedStep = projectStepsList.get(currentStepPosition);

                    int dataType = clickedStep.getDataType();
                    switch (dataType) {
                        case 0:
                            projectInputsList.add(new InputsItem(clickedStep.getId(),1, ""));

                            break;
                        case 1:
                            projectInputsList.add(new InputsItem(clickedStep.getId(), 2,"this is supposed to be an image"));
                    }
                }else {
                    Toast.makeText(this,"You have finished the project!",Toast.LENGTH_LONG);
                }
              //  projectInputsList.add(new InputsItem(inputCurrentlyWorkingView.getId(),thisDate,composingText.getText().toString()));
                if (currentStepPosition<projectStepsList.size()-1){
                  //  currentStepPosition++;
                    currentInputPosition++;
                }
                compositionStepsRV.smoothScrollToPosition(currentInputPosition);
                inputsAdapter.notifyDataSetChanged();
           //     Log.e("projectListSize",String.valueOf(projectInputsList.size()));
         //       compositionInputsRV.getChildAt(projectInputsList.size()-1).requestFocus();

                break;
            case R.id.toolbar_button:
                this.finish();
                break;
        }
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

    Callback<Inputs> inputsCallback = new Callback<Inputs>() {
        @Override
        public void onResponse(@NonNull Call<Inputs> call, @NonNull final Response<Inputs> response) {
            final Inputs input = response.body();
            if (input != null) {
                //Log.e("Id", String.valueOf(input.getId()));
                //Log.e("Step", String.valueOf(input.getStep()));
                //Log.e("Text", String.valueOf(input.getText()));
                //Log.e("TimesIterated", String.valueOf(input.getTimesIterated()));

            } else{
                Toast.makeText(Composing.this,response.message(),Toast.LENGTH_SHORT).show();

            }


            // templateList.add(new TemplateItem(5,"https://image.flaticon.com/icons/png/128/181/181549.png","dummy template", "dummy author", "this is just a dummy template","EASY",1,30, "20 mins"));


        }

        @Override
        public void onFailure(@NonNull Call<Inputs> call, @NonNull Throwable t) {
            Toast.makeText(Composing.this,"INPUTS POST FAILED",Toast.LENGTH_SHORT).show();

        }


    };
}
