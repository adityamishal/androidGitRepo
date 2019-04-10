package com.example.menu;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackForm extends AppCompatActivity {

    private PostCandidateAPI.PostCandidateService mAPIService;
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;

    private EditText title_inter;
    private String fetched_feed_candidate;
    List<String> candidates1 = new ArrayList<String>();


    private static final String PROFILE_IMAGE = "profile_image";
    final int START_YEAR = 1900;
    private final int CHOOSER_REQUEST_CODE = 1002;
    private final int ERROR_DIALOG_REQUEST_CODE = 1003;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    private String fetched_round;
    private EditText titleEt;
    private String fetched_status;
    private String selected_id;
    private String final_path;
    private String final_name;
    private final int REQUEST_CAMERA_USAGE = 200;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        getSupportActionBar().setTitle("Feedback Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(FeedbackForm.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(FeedbackForm.this,
                            Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (FeedbackForm.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale
                                (FeedbackForm.this, Manifest.permission.CAMERA)) {

                    Snackbar.make(FeedbackForm.this.findViewById(android.R.id.content),
                            "Please Grant Permissions to upload profile photo",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    requestPermissions(
                                            new String[]{Manifest.permission
                                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            }).show();
                } else {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            } else {
                // write your logic code if permission already granted
            }

        } else {
            // write your logic here
        }


        if (isOnline1() == false) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FeedbackForm.this);
            alertDialogBuilder.setMessage("Would you like to enable it?")
                    .setTitle("No Internet Connection")
                    .setPositiveButton(" Enable Internet ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            FeedbackForm.this.startActivity(dialogIntent);
                        }
                    });

            alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }


        buttonChoose = (Button) findViewById(R.id.buttonChoose1);
        buttonUpload = (Button) findViewById(R.id.buttonUpload1);
        imageView = (ImageView) findViewById(R.id.imageView);
        mAPIService = PostCandidateAPI.getservice();


        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), CHOOSER_REQUEST_CODE);
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMultipart(final_path, final_name);
            }
        });


        final Spinner spinner1 = (Spinner) findViewById(R.id.add_round_list);
        String[] round_result = new String[]{
                "L1",
                "L2",
                "MR",
                "HR"
        };

        final List<String> plantsList1 = new ArrayList<>(Arrays.asList(round_result));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList1) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position % 2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#FFC9A3FF"));
                } else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                }
                return view;
            }
        };
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetched_round = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Spinner spinner2 = (Spinner) findViewById(R.id.add_status_list);
        // Initializing a String Array
        String[] status_result = new String[]{
                "select",
                "reject",
                "cancel"
        };

        final List<String> plantsList2 = new ArrayList<>(Arrays.asList(status_result));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList2) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position % 2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#FFC9A3FF"));
                } else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(spinnerArrayAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetched_status = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Spinner spinner_can = (Spinner) findViewById(R.id.add_candidate_list);

        Call<List<Candidate>> candidateList = CandidateAPI.getservice().getcandidateList();
        candidateList.enqueue(new Callback<List<Candidate>>() {
            @Override
            public void onResponse(Call<List<Candidate>> call, Response<List<Candidate>> response) {

                candidates1 = new ArrayList<String>();
                ;
                final List<Candidate> list = response.body();
                for (int i = 0; i < list.size(); i++)
                    candidates1.add(list.get(i).getName());

                ;

                // Initializing an ArrayAdapter
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        FeedbackForm.this, R.layout.spinner_item, candidates1) {
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position % 2 == 1) {
                            // Set the item background color
                            tv.setBackgroundColor(Color.parseColor("#FFC9A3FF"));
                        } else {
                            // Set the alternate item background color
                            tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                        }
                        return view;
                    }
                };
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                spinner_can.setAdapter(spinnerArrayAdapter);

                spinner_can.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selected_id = (String) list.get(position).getId();
                        fetched_feed_candidate = (String) list.get(position).getName();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Candidate>> call, Throwable t) {
                Toast.makeText(FeedbackForm.this, "Unable to fetch candidates", Toast.LENGTH_SHORT).show();
                Log.d("bk_test", "REason" + t.getMessage());
            }
        });


        titleEt = (EditText) findViewById(R.id.add_feedback_desc);
        title_inter = (EditText) findViewById(R.id.add_interviewer);

        Button submitBtn = (Button) findViewById(R.id.buttonUpload1);


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the FeedbackForm/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CHOOSER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    Uri imageUri = getPickImageResultUri(data);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imageView.setImageBitmap(bitmap);
                        saveImage(FeedbackForm.this, bitmap);
                    } catch (IOException e) {

                    }
                }
                break;
            case ERROR_DIALOG_REQUEST_CODE:
                break;
        }
    }

    public Uri getPickImageResultUri(android.content.Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public void uploadMultipart(String full_image_path, String full_file_name) {
        //Toast.makeText(FeedbackForm.this,"Pressed upload",Toast.LENGTH_LONG).show();
        String c_round = fetched_round.trim();
        RequestBody c_round1 = RequestBody.create(MediaType.parse("text/plain"), c_round);

        String c_desc = titleEt.getText().toString().trim();
        if (TextUtils.isEmpty(c_desc)) {
            c_desc = "Not Specified";
        }
        RequestBody c_desc1 = RequestBody.create(MediaType.parse("text/plain"), c_desc);

        String c_inter = title_inter.getText().toString().trim();
        if (TextUtils.isEmpty(c_inter)) {
            c_inter = "Not Specified";
        }
        RequestBody c_inter1 = RequestBody.create(MediaType.parse("text/plain"), c_inter);

        String c_status = fetched_status.trim();
        RequestBody c_status1 = RequestBody.create(MediaType.parse("text/plain"), c_status);

        String c_candi = selected_id;
        RequestBody c_candi1 = RequestBody.create(MediaType.parse("text/plain"), c_candi);
        try {

            File file = new File(full_image_path, full_file_name);
            if (file.exists()) {
                Log.d("bk_test", "method called with file");
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part c_path1 = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                if (!TextUtils.isEmpty(c_round) && !TextUtils.isEmpty(c_desc) && !TextUtils.isEmpty(c_inter) && !TextUtils.isEmpty(c_status) && !TextUtils.isEmpty(c_candi)) {
                    mAPIService.uploadImage_feedback(c_round1, c_desc1, c_path1, c_inter1, c_status1, c_candi1).enqueue(new Callback<Feedback>() {

                        @Override
                        public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                            Log.d("bk_test", "response received" + response.toString());
                            //Toast.makeText(FeedbackForm.this,"RESPONSE"+response,Toast.LENGTH_LONG).show();

                            if (response.isSuccessful()) {
                                Log.d("bk_test", "Successful response received" + response.toString());
                                Toast.makeText(FeedbackForm.this, "Successfully Submitted Feedback of candidate", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(FeedbackForm.this, MainActivity.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(Call<Feedback> call, Throwable t) {
                            if (t instanceof IOException) {
                                Log.d("bk_test", "Network failure");
                                Toast.makeText(FeedbackForm.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d("bk_test", "Failure reason" + t.toString());
                                Toast.makeText(FeedbackForm.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.d("bk_test", "Some fields are empty");
                    Toast.makeText(FeedbackForm.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(FeedbackForm.this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }



    public void saveImage(Context context, Bitmap finalBitmap) {
        Long timeStamp = System.currentTimeMillis() / 1000;
        String fileName = "feedback" + timeStamp + ".jpg";
        String filesDir = "/data/data/"
                + getApplicationContext().getPackageName()
                + "/images";

        File myDir = new File(filesDir);
        if (!myDir.exists())
            myDir.mkdirs();

        File file = new File(filesDir, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            Log.d("bk_test", "file added ");
            Log.d("bk_test", "file added with name:" + fileName);
            Log.d("bk_test", "file added with path:" + filesDir);
            Toast.makeText(FeedbackForm.this, "File Added" + fileName, Toast.LENGTH_LONG).show();
            Toast.makeText(FeedbackForm.this, "File name:" + filesDir + "/" + fileName, Toast.LENGTH_LONG).show();
            final_path = filesDir;
            final_name = fileName;


        }
    }

    public boolean isOnline1() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(FeedbackForm.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && readExternalFile) {
                        // write your logic here
                    } else {
                        Snackbar.make(FeedbackForm.this.findViewById(android.R.id.content),
                                "Please Grant Permissions to upload profile photo",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        requestPermissions(
                                                new String[]{Manifest.permission
                                                        .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                                PERMISSIONS_MULTIPLE_REQUEST);
                                    }
                                }).show();
                    }
                }
                break;
        }
    }
}
