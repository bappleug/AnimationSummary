package io.amosbake.animationsummary.scenetransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/1/4.
 */

public class SceneTransitionActivity extends AppCompatActivity {

    private final String TAG_NEWBUTTON = "new button";
    private ViewGroup sceneRoot;

    private Scene scene1;
    private Scene scene2;
    private TransitionManager transManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_trans);
        sceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);

        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene2, this);
        transManager = new TransitionManager();
        TransitionSet transSetAll = new TransitionSet();
        transSetAll.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        TransitionSet resizeRecolorSet = new TransitionSet();
        resizeRecolorSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        resizeRecolorSet.addTransition(new ChangeBounds())
                .addTransition(new TextSizeScale())
                .addTransition(new Recolor());
        transSetAll.addTransition(new Fade(Fade.OUT))
                .addTransition(resizeRecolorSet)
                .addTransition(new Fade(Fade.IN));
        transManager.setTransition(scene1, scene2, transSetAll);
        transManager.setTransition(scene2, scene1, transSetAll);
    }

    public void goScene1Auto(View view){
        TransitionManager.go(scene1);
    }

    public void goScene2Auto(View view){
        TransitionManager.go(scene2);
    }

    public void goScene1Custom(View view){
        transManager.transitionTo(scene1);
    }

    public void goScene2Custom(View view){
        transManager.transitionTo(scene2);
    }

    public void transWithoutSceneAddButton(View view){
        if(sceneRoot.findViewWithTag(TAG_NEWBUTTON) != null){
            return;
        }
        TransitionManager.beginDelayedTransition(sceneRoot, new Slide(Gravity.RIGHT));
        Button button = new Button(this);
        button.setText("New Button，click to remove");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        button.setLayoutParams(layoutParams);
        button.setTag(TAG_NEWBUTTON);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transWithoutSceneRemoveButton();
            }
        });
        sceneRoot.addView(button);
    }

    public void transWithoutSceneRemoveButton(){
        TransitionManager.beginDelayedTransition(sceneRoot, new Slide(Gravity.RIGHT));
        sceneRoot.removeView(sceneRoot.findViewWithTag(TAG_NEWBUTTON));
    }

    public void transTranslation(View view){
        TransitionManager.beginDelayedTransition(sceneRoot, new TranslationTransition(TranslationTransition.TRANSLATION_X));
        if(findViewById(R.id.btn4).getTranslationX() != 0){
            findViewById(R.id.btn4).setTranslationX(0);
        } else {
            findViewById(R.id.btn4).setTranslationX(100);
        }
    }

}
