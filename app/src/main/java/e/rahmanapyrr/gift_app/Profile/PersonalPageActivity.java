package e.rahmanapyrr.gift_app.Profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import e.rahmanapyrr.gift_app.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PersonalPageActivity extends AppCompatActivity {

    private static final String AUTHORITY = "e.rahmanapyrr.gift_app";

    Button logoutButton;
    Button captureButton;
    Button updateButton;
    ImageView photoView;
    public Activity activity;
    File photoFile;
    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    ParseFile parseFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        logoutButton = findViewById(R.id.logoutBtn);
        captureButton = findViewById(R.id.captureBtn);
        updateButton = findViewById(R.id.updateBtn);
        photoView = findViewById(R.id.photo);
        activity = this;

        final ParseUser user = ParseUser.getCurrentUser();
        final ParseFile pic = user.getParseFile("ProfilePic");
        if(pic != null) {
            Glide.with(this).load(pic.getUrl())
                    .apply(bitmapTransform(new CircleCrop()))
                    .into(photoView);
        }

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLaunchCamera(view);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser user = ParseUser.getCurrentUser();
                if(photoFile == null){
                    Toast.makeText(PersonalPageActivity.super.getBaseContext(), "Must include an image in order to post", Toast.LENGTH_LONG).show();
                    return;
                }

                parseFile = new ParseFile(photoFile);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        user.put("ProfilePic", parseFile);
                        user.saveInBackground();
                        Glide.with(PersonalPageActivity.super.getBaseContext()).load(parseFile.getUrl())
                                .apply(bitmapTransform(new CircleCrop()))
                                .into(photoView);
                        Toast.makeText(PersonalPageActivity.super.getBaseContext(), "Profile pic updated", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);
        System.out.println(photoFile);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(PersonalPageActivity.this, AUTHORITY, photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the file for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use 'getExternalFilesDir' on Context to access package-specific directories
        // This way, we don't need to request external read/write runtime permissions
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }
}
