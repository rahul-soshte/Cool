package com.example.hunter.planstart.CoreFunctionality.Renters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hunter.planstart.Constants;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;



//https://www.androidhive.info/2014/12/android-uploading-camera-image-video-to-server-with-progress-bar/
//https://www.simplifiedcoding.net/android-upload-image-to-server/#comment-12074
public class LendActivity extends AppCompatActivity implements View.OnClickListener{

    //SessionManager instance
    SessionManager session;


    String email;
    //Declaring Views
    private EditText ProductName;
    private Button DoneButton;
    private ImageView imageView;
    private Button selectButton;
    private EditText rentPerDay;
    private EditText proddesc;
    private EditText contactNo;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        //Requesting storage permission
        requestStoragePermission();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
         email = user.get(SessionManager.KEY_EMAIL);

        //Initializing views
        ProductName=(EditText)findViewById(R.id.prodname);
        DoneButton=(Button)findViewById(R.id.donebutton);
        imageView=(ImageView)findViewById(R.id.imageView);
        selectButton=(Button)findViewById(R.id.selectbutton);
        rentPerDay=(EditText)findViewById(R.id.rentPerDay);
        proddesc=(EditText)findViewById(R.id.desc);
        contactNo=(EditText)findViewById(R.id.editText2);
        //Setting OnClickListener
        DoneButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);

    }



    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void uploadMultipart() {

        String ProductDescription=proddesc.getText().toString().trim();
        String Contact=contactNo.getText().toString().trim();

        //getting name for the image
        String name = ProductName.getText().toString().trim();
        //Getting Rent Value
        double rentperday=Double.parseDouble(rentPerDay.getText().toString().trim());
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .addParameter("rentperday",Double.toString(rentperday))//Adding rentperday parameter
                    .addParameter("user_email",email)
                    .addParameter("contactno",Contact)
                    .addParameter("productdesc",ProductDescription)
                    .setNotificationConfig((new UploadNotificationConfig()).setTitle("AD Upload").setCompletedMessage("Ad Uploaded Successfully"))
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == selectButton) {
            showFileChooser();
        }
        if (v == DoneButton) {
            uploadMultipart();
        }
    }

}
