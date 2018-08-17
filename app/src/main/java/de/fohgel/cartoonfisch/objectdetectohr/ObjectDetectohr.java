package de.fohgel.cartoonfisch.objectdetectohr;

import android.app.Activity;

import java.io.IOException;

public class ObjectDetectohr extends Activity {

    private static final String TF_GRAPH = "file:///android_asset/frozen_interference_graph.pb";
    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/label_mapping.txt";
    private static final int TF_OD_API_INPUT_SIZE = 300;

    private Classifier detector;

    public ObjectDetectohr() {
        try {
            detector = TensorFlowObjectDetectionAPIModel.create(
                    getAssets(), TF_GRAPH, TF_OD_API_LABELS_FILE, TF_OD_API_INPUT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
