package de.fohgel.cartoonfisch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements Permissions.PermissionsAvailableListener {
    private Permissions mPermissions;
    private final List<File> fooohtooohFileList = new ArrayList<>();
    private static final int CAMERA_REQUEST_MINIMUM = 42;
    @BindView(R.id.textView2)
    TextView mTextView;
    @BindView(R.id.imageView)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissions = new Permissions(this);
        mPermissions.checkPermissions();
        // put init code in init()
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsAvailable() {
        init();
    }

    @DebugLog
    private void init() {
//        MyDrawable myDrawable = new MyDrawable();
//
//        ImageView imageView = new ImageView(this);
//        imageView.setImageDrawable(myDrawable);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        View rootView = findViewById(android.R.id.content);
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        Canvas canvas = new Canvas();
//        canvas.drawLine(0, 0, 200, 200, paint);
//        rootView.draw(canvas);
    }

    @DebugLog
    @OnClick(R.id.button)
    void startCamera() {
        Intent fooohtooohIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (fooohtooohIntent.resolveActivity(getPackageManager()) != null) {

            File fooohtooohFile = null;
            try {
                fooohtooohFile = getImageFile();
            } catch (IOException e) {
                Log.e(MainActivity.class.getSimpleName(), "no image file available", e);
            }

            if (fooohtooohFile != null) {
                Uri fooohtooohURI = FileProvider.getUriForFile(
                        this,
                        "de.fohgel.cartoonfisch.android.fileprovider",
                        fooohtooohFile
                );

                fooohtooohIntent.putExtra(MediaStore.EXTRA_OUTPUT, fooohtooohURI);

                int nextIndex = fooohtooohFileList.size();
                fooohtooohFileList.add(nextIndex, fooohtooohFile);
                startActivityForResult(fooohtooohIntent, CAMERA_REQUEST_MINIMUM + nextIndex);
            }
        }
    }

    @DebugLog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode >= CAMERA_REQUEST_MINIMUM && resultCode == RESULT_OK) {
            File fooohtooohFile = fooohtooohFileList.get(requestCode - CAMERA_REQUEST_MINIMUM);
            onFooohtooohAvailable(fooohtooohFile);
        }
    }

    @DebugLog
    private void onFooohtooohAvailable(@NonNull File fooohtooohFile) {
        mTextView.setText(fooohtooohFile.getAbsolutePath());

        // TODO: URI speichern statt File
        Uri fooohtooohURI = FileProvider.getUriForFile(
                this,
                "de.fohgel.cartoonfisch.android.fileprovider",
                fooohtooohFile
        );

        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fooohtooohURI);
        } catch (FileNotFoundException e) {
            Log.e(MainActivity.class.getSimpleName(), "file not found :(", e);
            return;
        } catch (IOException e) {
            Log.e(MainActivity.class.getSimpleName(), "böze exception :(", e);
            return;
        }

        onBitmapAvailable(bitmap);
    }

    @DebugLog
    private void onBitmapAvailable(@NonNull Bitmap image) {
        mImageView.setImageBitmap(image);
    }

    private static Bitmap crupAndScale(Bitmap source, int scale) {
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    private File getImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String prefix = "JPEG_" + timeStamp + "_";
        String suffix = ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(prefix, suffix, storageDir);
    }
}
