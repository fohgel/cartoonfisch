package de.fohgel.cartoonfisch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements Permissions.PermissionsAvailableListener {
    private Permissions mPermissions;

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

    private void init() {
        MyDrawable myDrawable = new MyDrawable();

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(myDrawable);
        setContentView(imageView);
/*        View rootView = findViewById(android.R.id.content);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        Canvas canvas = new Canvas();
        canvas.drawLine(0, 0, 200, 200, paint);
        rootView.draw(canvas);*/
    }
}
