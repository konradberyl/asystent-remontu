package com.example.apps.asystentremontu.myhistory.newelement;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.asystentremontu.R;
import com.example.apps.asystentremontu.database.AssistantUserConfig;
import com.example.apps.asystentremontu.myhistory.actual_history.AdapterForRecyclerView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Beryl
 * on 11.05.2016
 */
public class NewElementActivity extends CommunicationWithDatabase {
    private ImageView placeToInsertPhoto;
    private Button createPhotoButton, addNewElement;
    private TextView actualData;
    private EditText typeDescription, typePrice;
    private String imagePath;
    private File storageDir;
    int poz = -1;
    private File actualFile;
    private boolean modified = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_element_in_history);

        Intent intent = getIntent();
        poz = intent.getIntExtra(AdapterForRecyclerView.EDIT_ITEM, -1);

        placeToInsertPhoto = (ImageView) findViewById(R.id.place_to_photo_imageview);
        createPhotoButton = (Button) findViewById(R.id.create_photo_button);
        actualData = (TextView) findViewById(R.id.actual_date_textview);
        String date = DateFormat.getDateTimeInstance().format(new Date());
        actualData.setText(date);
        typeDescription = (EditText) findViewById(R.id.type_description_edittext);
        typePrice = (EditText) findViewById(R.id.price_edittext);
        addNewElement = (Button) findViewById(R.id.add_new_element_button);
        if (poz >= 0) {
            modified = true;
            File imgFile = new File(ConfigListSingleton.getConfigInstance().getConfig().get(poz).pathToImg);
            Picasso.with(this).load(imgFile).resize(600, 800).centerInside().into(placeToInsertPhoto);
            actualData.setText(ConfigListSingleton.getConfigInstance().getConfig().get(poz).date);
            typeDescription.setText(ConfigListSingleton.getConfigInstance().getConfig().get(poz).comment);
            typePrice.setText(ConfigListSingleton.getConfigInstance().getConfig().get(poz).price);

        } else {
            modified = false;
            Picasso.with(this).load(R.color.white_transparent).resize(600, 800).centerInside().into(placeToInsertPhoto);
            actualData.setText(date);
            typeDescription.setText("");
            typePrice.setText("");
        }


        createPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });

        addNewElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDescription.getText().toString().equals("") || typePrice.getText().toString().equals("") || placeToInsertPhoto.getResources() == null) {
                    Toast.makeText(getApplicationContext(), "wprowadź dane!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!modified) {
                        AssistantUserConfig assistantUserConfig = new AssistantUserConfig(typeDescription.getText().toString(), imagePath, actualData.getText().toString(), typePrice.getText().toString());
                        ConfigListSingleton.getConfigInstance().getConfig().add(assistantUserConfig);
                        createItemToDb(ConfigListSingleton.getConfigInstance().getConfig());
                        Toast.makeText(getApplicationContext(), "dodano nowy element", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        if (imagePath == null) {
                            imagePath = ConfigListSingleton.getConfigInstance().getConfig().get(poz).pathToImg;
                        } else {
                            actualFile = new File(ConfigListSingleton.getConfigInstance().getConfig().get(poz).pathToImg);
                            if (actualFile.exists()) {
                                actualFile.delete();
                            }
                        }
                        AssistantUserConfig assistantUserConfig = new AssistantUserConfig(typeDescription.getText().toString(), imagePath, actualData.getText().toString(), typePrice.getText().toString());
                        ConfigListSingleton.getConfigInstance().getConfig().remove(poz);
                        ConfigListSingleton.getConfigInstance().getConfig().add(poz, assistantUserConfig);
                        createItemToDb(ConfigListSingleton.getConfigInstance().getConfig());
                        Toast.makeText(getApplicationContext(), "dokonano edycji", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }

        });
    }

    private void takePictureIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {

                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(imagePath);
            Picasso.with(this).load(imgFile).resize(600, 800).centerInside().into(placeToInsertPhoto);
            removeImage(getLastImageId());
            //refleshGalleryPictures();
        } else {
            Toast.makeText(getApplicationContext(), "nie zaakceptowano", Toast.LENGTH_SHORT).show();
        }

    }

    private File createImageFile() throws IOException {
        if (storageDir == null) {
            //storageDir = getActivity().getExternalFilesDir("remont");
            storageDir = new File(Environment.getExternalStorageDirectory(), "AsystentRemontu");
            storageDir.mkdirs();
            Log.d("createImageFIle: ", "make folder: " + storageDir.toString());
            String timeStamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(new Date());
            File image = new File(storageDir, timeStamp + ".jpg");
            imagePath = image.getAbsolutePath();
            Log.d("createImageFIle: ", "show mCurrentPhoto value: " + imagePath);
            Log.d("createImageFIle: ", " image value : " + image.toString());
            return image;
        }
        if (storageDir != null) {
            String timeStamp = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(new Date());
            File image = new File(storageDir, timeStamp + ".jpg");
            imagePath = image.getAbsolutePath();
            Log.d("createImageFIle: ", "show mCurrentPhoto value: " + imagePath);
            Log.d("createImageFIle: ", "image value : " + image.toString());
            return image;

        }
        return null;
    }


    private void refleshGalleryPictures() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private int getLastImageId() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
        if (imageCursor.moveToFirst()) {
            int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Log.d(getLocalClassName(), "getLastImageId::id " + id);
            Log.d(getLocalClassName(), "getLastImageId::path " + fullPath);
            //imageCursor.close();
            return id;
        } else {
            return 0;
        }
    }

    private void removeImage(int id) {
        ContentResolver cr = getContentResolver();
        cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=?", new String[]{Long.toString(id)});
    }
}
