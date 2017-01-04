package io.amosbake.animationsummary.scenetransition;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/1/4.
 */

public class SceneTransitionActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;

    private Scene scene1;
    private Scene scene2;
    private TransitionManager transManager;

    private android.support.transition.Scene sceneCompat1;
    private android.support.transition.Scene sceneCompat2;
    private android.support.transition.TransitionManager transManagerCompat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_trans);
        sceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            sceneCompat1 = android.support.transition.Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene1, this);
            sceneCompat2 = android.support.transition.Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene2, this);
            transManagerCompat = new android.support.transition.TransitionManager();
            android.support.transition.TransitionSet transSet = new TransitionSet();
            transSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
            transSet.addTransition(new android.support.transition.Fade(Fade.OUT))
                    .addTransition(new android.support.transition.ChangeBounds())
                    .addTransition(new android.support.transition.Fade(Fade.IN));
            transManagerCompat.setTransition(sceneCompat1, sceneCompat2, transSet);
        } else {
            scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene1, this);
            scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene2, this);
            transManager = TransitionInflater.from(this).inflateTransitionManager(R.transition.trans_mgr, sceneRoot);
        }
    }

    void goScene1Auto(View view){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.support.transition.TransitionManager.go(sceneCompat1);
        } else {
            TransitionManager.go(scene1);
        }
    }

    void goScene2Auto(View view){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.support.transition.TransitionManager.go(sceneCompat2);
        } else {
            TransitionManager.go(scene2);
        }
    }

    void goScene1Custom(View view){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            transManagerCompat.transitionTo(sceneCompat1);
        } else {
            transManager.transitionTo(scene1);
        }
    }

    void goScene2Custom(View view){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            transManagerCompat.transitionTo(sceneCompat2);
        } else {
            transManager.transitionTo(scene2);
        }
    }
}
