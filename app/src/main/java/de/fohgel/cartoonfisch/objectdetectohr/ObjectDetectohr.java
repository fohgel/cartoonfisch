package de.fohgel.cartoonfisch.objectdetectohr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class ObjectDetectohr extends AppCompatActivity {

    private static final String TF_GRAPH = "file:///android_asset/frozen_interference_graph.pb";
    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/label_mapping.txt";
    private static final int TF_OD_API_INPUT_SIZE = 300;

    private static final String bitmap = "file:///android_asset/bitmap.bmp";

    private Classifier detector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        try {
            detector = TensorFlowObjectDetectionAPIModel.create(
                    getAssets(), TF_GRAPH, TF_OD_API_LABELS_FILE, TF_OD_API_INPUT_SIZE);
        } catch (IOException e) {
            Log.e(ObjectDetectohr.class.getSimpleName(), e.getLocalizedMessage());
        }

        Bitmap bihtmap;
        try {
            bihtmap = BitmapFactory.decodeStream(getAssets().open(bitmap));
            List<Classifier.Recognition> result = detector.recognizeImage(bihtmap);

            for (Classifier.Recognition rec : result) {
                Log.i(ObjectDetectohr.class.getSimpleName(), rec.getTitle());
            }
        } catch (IOException e) {
            Log.e(ObjectDetectohr.class.getSimpleName(), e.getLocalizedMessage());
        }
    }
}
