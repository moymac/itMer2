package com.moymac.meritapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moymac.meritapp.Adapters.InputsAdapterRVFixed;
import com.moymac.meritapp.Adapters.ProjectStepAdapter;
import com.moymac.meritapp.Models.Inputs;
import com.moymac.meritapp.Models.InputsItem;
import com.moymac.meritapp.Models.Steps;
import android.support.v7.widget.GridLayoutManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComposingFixed extends AppCompatActivity  implements View.OnClickListener{
    String projectTitle;
    int rowNum = 1;
    List<Integer> projectChildIds = new ArrayList<>();
    List<String> projectChildNames = new ArrayList<>();
    List<String> projectChildTexts = new ArrayList<>();
    List<Integer> projectChildTypes = new ArrayList<>();
    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }
  //  Object[] theInputObjectList;
    private ApiInterface apiInterface;

    int currentStepPosition = 0;

    RecyclerView compositionStepsRV;
    ProjectStepAdapter projectStepAdapter;
    List<Steps> projectStepsList;

    Toolbar toolbar;
    ImageView composingImageview;
    TextView toolbarTV;
    ImageButton toolbarButton;
    String mCurrentPhotoPath;
    //EditText composingText;
    //EditText currentlyWorkingET;
    RichEditor currentlyWorkingEditor;
    RelativeLayout tempRelativeLayout;

    //ImageButton boldBtn,itaBtn,indentBtn,outdentBtn;
    Button sendButton;

    RecyclerView compositionInputsRV;
    InputsAdapterRVFixed inputsAdapter;
    List<InputsItem> projectInputsList;

    int editorTextColor = Color.DKGRAY;
    int editorBackgroundColor = Color.TRANSPARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing);

        toolbar = findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        toolbarButton = findViewById(R.id.toolbar_button);
        toolbarButton.setOnClickListener(this);
        toolbarTV = findViewById(R.id.toolbar_text);

        sendButton = findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(this);

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

        toolbarTV.setText(projectTitle);

        createApiInterface();

        projectInputsList = new ArrayList<>();
        projectStepsList = new ArrayList<>();
        for (int i =0;i<projectChildIds.size();i++) {
            projectStepsList.add(new Steps(projectChildIds.get(i),projectChildNames.get(i),projectChildTexts.get(i),projectChildTypes.get(i)));
            projectInputsList.add(new InputsItem(projectChildIds.get(i),projectChildTypes.get(i),""));
        }

        ///make the call for inputs and if empty, create FIRST INPUT
       // projectInputsList.add(new InputsItem(projectChildIds.get(0),""));
        compositionInputsRV = findViewById(R.id.composition_main_RV);

        ///String[] theDataset = new String[20];
        //theDataset[0] = "somethin";
        inputsAdapter = new InputsAdapterRVFixed(this, projectInputsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
         //       Log.e("HuboClick", v.toString() + String.valueOf(position));

                currentStepPosition = position;
                compositionStepsRV.smoothScrollToPosition(currentStepPosition);
                compositionInputsRV.smoothScrollToPosition(currentStepPosition);

            }
        });
        RecyclerView.LayoutManager xLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false);

        compositionInputsRV.setLayoutManager(xLayoutManager);
        compositionInputsRV.setItemAnimator(new DefaultItemAnimator());
        compositionInputsRV.setAdapter(inputsAdapter);

        compositionStepsRV = findViewById(R.id.composition_steps_rv);

        projectStepAdapter = new ProjectStepAdapter(this, projectStepsList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentStepPosition = position;
                compositionInputsRV.getChildAt(position).requestFocusFromTouch();
                //composingText.setHint(projectChildTexts.get(position));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false);

        compositionStepsRV.setLayoutManager(mLayoutManager);
        compositionStepsRV.setItemAnimator(new DefaultItemAnimator());
        compositionStepsRV.setAdapter(projectStepAdapter);
        SnapHelper snapHelper = new LinearSnapHelper(){
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                int snapPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                // Do somethin g with snapPosition
//                Log.e("snap position",String.valueOf(snapPosition));
                if (snapPosition!= -1) compositionInputsRV.getChildAt(snapPosition).requestFocusFromTouch();

                return snapPosition;
            }
        };

        snapHelper.attachToRecyclerView(compositionStepsRV);
        compositionInputsRV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (recyclerViewReadyCallback != null) {
                    recyclerViewReadyCallback.onLayoutReady();
                }
                recyclerViewReadyCallback = null;
            }


        });
        recyclerViewReadyCallback = new RecyclerViewReadyCallback() {
            @Override
            public void onLayoutReady() {
                //
                //here comes your code that will be executed after all items has are laid down
                //
                tempRelativeLayout = (RelativeLayout) compositionInputsRV.getChildAt(0);
                currentlyWorkingEditor = tempRelativeLayout.findViewById(R.id.editor);

            }
        };

        findViewById(R.id.action_undo).setOnClickListener(this);
        findViewById(R.id.action_redo).setOnClickListener(this);
        findViewById(R.id.action_bold).setOnClickListener(this);
        findViewById(R.id.action_italic).setOnClickListener(this);
        findViewById(R.id.action_subscript).setOnClickListener(this);
        findViewById(R.id.action_superscript).setOnClickListener(this);
        findViewById(R.id.action_strikethrough).setOnClickListener(this);
        findViewById(R.id.action_underline).setOnClickListener(this);
        findViewById(R.id.action_heading1).setOnClickListener(this);
        findViewById(R.id.action_heading2).setOnClickListener(this);
        findViewById(R.id.action_heading3).setOnClickListener(this);
        findViewById(R.id.action_heading4).setOnClickListener(this);
        findViewById(R.id.action_heading5).setOnClickListener(this);
        findViewById(R.id.action_heading6).setOnClickListener(this);
        findViewById(R.id.action_txt_color).setOnClickListener(this);
        findViewById(R.id.action_bg_color).setOnClickListener(this);
        findViewById(R.id.action_indent).setOnClickListener(this);
        findViewById(R.id.action_outdent).setOnClickListener(this);
        findViewById(R.id.action_align_left).setOnClickListener(this);
        findViewById(R.id.action_align_center).setOnClickListener(this);
        findViewById(R.id.action_align_right).setOnClickListener(this);
        findViewById(R.id.action_blockquote).setOnClickListener(this);
        findViewById(R.id.action_insert_bullets).setOnClickListener(this);
        findViewById(R.id.action_insert_numbers).setOnClickListener(this);
        findViewById(R.id.action_insert_image).setOnClickListener(this);
        findViewById(R.id.action_insert_link).setOnClickListener(this);
        findViewById(R.id.action_insert_checkbox).setOnClickListener(this);

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

        //View inputCurrentlyWorkingView = null;
        for(int i =0;i<projectInputsList.size();i++){
            tempRelativeLayout = (RelativeLayout) compositionInputsRV.getChildAt(i);
            //currentlyWorkingET = tempRelativeLayout.findViewById(R.id.input_content_et);
            currentlyWorkingEditor = tempRelativeLayout.findViewById(R.id.editor);

           // inputCurrentlyWorkingView = tempRelativeLayout.findViewById(R.id.editor);
            if (currentlyWorkingEditor.hasFocus()){
                currentStepPosition = i;
            }

        }

        tempRelativeLayout = (RelativeLayout) compositionInputsRV.getChildAt(currentStepPosition);
        currentlyWorkingEditor = tempRelativeLayout.findViewById(R.id.editor);
        String inputString = currentlyWorkingEditor.getHtml();
       // String inputString = currentlyWorkingEditor.getHtml();

        switch (v.getId()){

            case R.id.action_undo:
                currentlyWorkingEditor.undo();
                break;
            case R.id.action_redo:
                currentlyWorkingEditor.redo();
                break;
            case R.id.action_bold:
                currentlyWorkingEditor.setBold();
                break;
            case R.id.action_italic:
                currentlyWorkingEditor.setItalic();
                break;
            case R.id.action_subscript:
                currentlyWorkingEditor.setSubscript();
                break;
            case R.id.action_superscript:
                currentlyWorkingEditor.setSuperscript();
                break;
            case R.id.action_strikethrough:
                currentlyWorkingEditor.setStrikeThrough();
                break;
            case R.id.action_underline:
                currentlyWorkingEditor.setUnderline();
                break;
            case R.id.action_heading1:
                currentlyWorkingEditor.setHeading(1);
                break;
            case R.id.action_heading2:
                currentlyWorkingEditor.setHeading(2);
                break;
            case R.id.action_heading3:
                currentlyWorkingEditor.setHeading(3);
                break;
            case R.id.action_heading4:
                currentlyWorkingEditor.setHeading(4);
                break;
            case R.id.action_heading5:
                currentlyWorkingEditor.setHeading(5);
                break;
            case R.id.action_heading6:
                currentlyWorkingEditor.setHeading(6);
                break;
            case R.id.action_txt_color:
                showColorPicker(1);
                break;
            case R.id.action_bg_color:
                showColorPicker(2);
                break;
            case R.id.action_indent:
                currentlyWorkingEditor.setIndent();
                break;
            case R.id.action_outdent:
                currentlyWorkingEditor.setOutdent();
                break;
            case R.id.action_align_left:
                currentlyWorkingEditor.setAlignLeft();
                break;
            case R.id.action_align_center:
                currentlyWorkingEditor.setAlignCenter();
                break;
            case R.id.action_align_right:
                currentlyWorkingEditor.setAlignRight();
                break;
            case R.id.action_blockquote:
                currentlyWorkingEditor.setBlockquote();
                break;
            case R.id.action_insert_bullets:
                currentlyWorkingEditor.setBullets();
                break;
            case R.id.action_insert_numbers:
                currentlyWorkingEditor.setNumbers();
                break;
            case R.id.action_insert_image:
                currentlyWorkingEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
                break;
            case R.id.action_insert_link:
                currentlyWorkingEditor.insertLink("https://github.com/wasabeef", "wasabeef");
                break;
            case R.id.action_insert_checkbox:
                currentlyWorkingEditor.insertTodo();
                break;
            case R.id.button_chatbox_send:

                projectInputsList.get(currentStepPosition).setContent(inputString);
                for (int i=0; i<projectStepsList.size();i++){
                    apiInterface.postInputs(String.valueOf(projectStepsList.get(i).getId()),(String) projectInputsList.get(i).getContent()).enqueue(inputsCallback);

                }

                if (currentStepPosition<projectStepsList.size()-1){
                    currentStepPosition++;
                }
                compositionStepsRV.smoothScrollToPosition(currentStepPosition);
                compositionInputsRV.getChildAt(currentStepPosition).requestFocusFromTouch();

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
             //   Log.e("Id", String.valueOf(input.getId()));
             //   Log.e("Step", String.valueOf(input.getStep()));
             //   Log.e("Text", String.valueOf(input.getText()));
             //   Log.e("TimesIterated", String.valueOf(input.getTimesIterated()));

            } else{
                Toast.makeText(ComposingFixed.this,response.message(),Toast.LENGTH_SHORT).show();
            }
            // templateList.add(new TemplateItem(5,"https://image.flaticon.com/icons/png/128/181/181549.png","dummy template", "dummy author", "this is just a dummy template","EASY",1,30, "20 mins"));
        }

        @Override
        public void onFailure(@NonNull Call<Inputs> call, @NonNull Throwable t) {
            Toast.makeText(ComposingFixed.this,"INPUTS POST FAILED",Toast.LENGTH_SHORT).show();

        }

    };

    public void showColorPicker(final int source){
        final Context context = ComposingFixed.this;
        int initialColor = (source == 1)?editorTextColor : editorBackgroundColor;

        ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        changeColor(source,selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void changeColor(int source, int selectedColor){
        switch (source){
            case 1:
                editorTextColor = selectedColor;
                currentlyWorkingEditor.setTextColor(selectedColor);
                break;
            case 2:
                editorBackgroundColor = selectedColor;
                currentlyWorkingEditor.setTextBackgroundColor(selectedColor);
                break;
        }
    }
}
