package com.example.krystiano.asystentremontu.myhistory.newelement;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public class NewElementFragment extends Fragment {
    private ImageView placeToInsertPhoto;
    private Button createPhotoButton, addNewElement;
    private TextView actualData;
    private EditText typeDescription, typePrice;
    private String imagePath;
    private File storageDir;
    private OnNewElementAdded onNewElementAdded;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_element_in_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeToInsertPhoto = (ImageView) view.findViewById(R.id.place_to_photo_imageview);
        createPhotoButton = (Button) view.findViewById(R.id.create_photo_button);
        actualData = (TextView) view.findViewById(R.id.actual_date_textview);
        String date = DateFormat.getDateTimeInstance().format(new Date());
        actualData.setText(date);
        typeDescription = (EditText) view.findViewById(R.id.type_description_edittext);
        typePrice = (EditText) view.findViewById(R.id.price_edittext);
        addNewElement = (Button) view.findViewById(R.id.add_new_element_button);

        createPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent();
            }
        });

        addNewElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDescription.getText().toString().equals("") ||typePrice.getText().toString().equals("") || placeToInsertPhoto.getResources()==null){
                    Toast.makeText(getContext(), "wprowadź dane!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                     AssistantUserConfig assistantUserConfig = new AssistantUserConfig(typeDescription.getText().toString(), imagePath, actualData.getText().toString(), typePrice.getText().toString());
                     onNewElementAdded.onAdded(assistantUserConfig);
                    Toast.makeText(getContext(), "dodano nowy element", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void setCallbacks(OnNewElementAdded onNewElementAdded) {
        this.onNewElementAdded = onNewElementAdded;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(imagePath);
            Picasso.with(getContext()).load(imgFile).into(placeToInsertPhoto);
            removeImage(getLastImageId());
            refleshGalleryPictures();
        } else {
            Toast.makeText(getContext(), "nie zaakceptowano", Toast.LENGTH_SHORT).show();
        }

    }

    private int getLastImageId() {
        final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor imageCursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
        if (imageCursor.moveToFirst()) {
            int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Log.d(getActivity().getLocalClassName(), "getLastImageId::id " + id);
            Log.d(getActivity().getLocalClassName(), "getLastImageId::path " + fullPath);
            //imageCursor.close();
            return id;
        } else {
            return 0;
        }
    }

    private void removeImage(int id) {
        ContentResolver cr = getActivity().getContentResolver();
        cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=?", new String[]{Long.toString(id)});
    }

    private void takePictureIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
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
        getActivity().sendBroadcast(mediaScanIntent);
    }

}
