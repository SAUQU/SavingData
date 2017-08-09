package com.example.segundoauqui.savingdata;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    DataBaseHelper databaseHelper;

    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private static final String TAG = "TAG";

    // private static final int CAMERA_REQUEST = 2;

    EditText etName;
    EditText etPhoneNumber;
    EditText etEmail;
    EditText etCompany;
    EditText etCity;
    Button btnSaveData;
    ImageButton ibtnTakePicture;
    Bitmap bitmap;
    String filepath = "";
    String source = "";
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCompany = (EditText) findViewById(R.id.etCompany);
        etCity = (EditText) findViewById(R.id.etCity);

        btnSaveData = (Button) findViewById(R.id.btnSaveData);

        ibtnTakePicture = (ImageButton) findViewById(R.id.ibtnTakePicture);


        ibtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);

            }
        });


        Intent intent = getIntent();
        id = intent.getStringExtra(getString(R.string.ID));
        Log.d(TAG, "onCreate: "+ id);

        databaseHelper= new DataBaseHelper(this);


        try {
            ArrayList<MyContacs> contacts = databaseHelper.getContacs(id);

            etName.setText(contacts.get(0).getEtName());
            etCity.setText(contacts.get(0).getEtCity());
            etEmail.setText(contacts.get(0).getEtEmail());
            etCompany.setText(contacts.get(0).getEtCompany());
            etPhoneNumber.setText(contacts.get(0).getEtPhoneNumber());
            byte[] b = contacts.get(0).getB();
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            ibtnTakePicture.setImageBitmap(bitmap);






        }catch (Exception e){}




    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bitmap != null) {
            outState.putString("img", "uploaded");
            outState.putString("source", source);
            outState.putString("filepath", filepath);

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            if (savedInstanceState.getString("img").equals("uploaded")) {
                filepath = savedInstanceState.getString("filepath");
                File file = new File(filepath);
                source = savedInstanceState.getString("source");
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    if (!savedInstanceState.getString("source").equals("landscape")) {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        ibtnTakePicture.setImageBitmap(bitmap2);
                        ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                        ibtnTakePicture.setImageBitmap(bitmap);
                        ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                } else {

                    if (savedInstanceState.getString("source").equals("portrait")) {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        ibtnTakePicture.setImageBitmap(bitmap2);
                        ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                        ibtnTakePicture.setImageBitmap(bitmap);
                        ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            }
        } catch (Exception ex) {
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "image.jpg");
            filepath = file.getAbsolutePath();
            Log.d(TAG, "onActivityResult: " + file.getAbsolutePath());

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                source = "landscape";
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                ibtnTakePicture.setImageBitmap(bitmap);
                ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                source = "portrait";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ibtnTakePicture.setImageBitmap(bitmap2);
                ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
            }


        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }


    public void saveData(View view) {

        if (etName.getText().toString().isEmpty() && etPhoneNumber.getText().toString().isEmpty() && etEmail.getText().toString().isEmpty() && etCompany.getText().toString().isEmpty() && etCity.getText().toString().isEmpty()) {
            Toast.makeText(this, "Missing Entries", Toast.LENGTH_LONG).show();
        } else {



                byte[] lop = null;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                lop = stream.toByteArray();
                MyContacs contact = new MyContacs(-1,etName.getText().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString(), etCompany.getText().toString(), etCity.getText().toString(), lop);

                databaseHelper.saveNewContact(contact);

        }
    }


    public void Delete(View view) {

        try {
            if(Integer.parseInt(id)>=0){
                databaseHelper.delete(id);
                Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show();

                etName.setText(" ");
                etCity.setText(" ");
                etEmail.setText(" ");
                etCompany.setText(" ");
                etPhoneNumber.setText(" ");
                ibtnTakePicture.setImageResource(R.drawable.camera);
                bitmap = null;



            }
        }catch(Exception ex){}


    }
}
