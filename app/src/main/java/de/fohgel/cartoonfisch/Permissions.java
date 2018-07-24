package de.fohgel.cartoonfisch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;

class Permissions {
    private static final int P_ID = 42;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private final WeakReference<Activity> mWeakActivityReference;

    interface PermissionsAvailableListener {
        void onPermissionsAvailable();
    }

    Permissions(@NonNull Activity activity) {
        if (!(activity instanceof PermissionsAvailableListener)) {
            throw new RuntimeException();
        }
        mWeakActivityReference = new WeakReference<>(activity);
    }

    void checkPermissions() {
        if (arePermissonsGranted()) {
            // check if activity is still alive
            Activity activity = mWeakActivityReference.get();
            if (activity != null) {
                PermissionsAvailableListener listener = (PermissionsAvailableListener) activity;
                listener.onPermissionsAvailable();
            }
        } else {
            requestPermissions();
        }
    }

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == P_ID && arePermissionsGranted(grantResults)) {
            // check if activity is still alive
            Activity activity = mWeakActivityReference.get();
            if (activity != null) {
                PermissionsAvailableListener listener = (PermissionsAvailableListener) activity;
                listener.onPermissionsAvailable();
            }
        } else {
            requestPermissions();
        }
    }

    private boolean arePermissonsGranted() {
        // check if activity is still alive
        Activity activity = mWeakActivityReference.get();
        if (activity == null) {
            return false;
        }

        // check permissions
        for (final String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        // check if activity is still alive
        Activity activity = mWeakActivityReference.get();
        if (activity == null) {
            return;
        }

        ActivityCompat.requestPermissions(activity, PERMISSIONS, P_ID);
    }

    private boolean arePermissionsGranted(@NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
