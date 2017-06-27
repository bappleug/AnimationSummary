package io.amosbake.animationsummary;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import io.amosbake.animationsummary.animationmisc.AnimationMiscActivity;
import io.amosbake.animationsummary.constraintlayouttransition.ConstraintTransitionActivity;
import io.amosbake.animationsummary.draggviewhelper.DragViewHelperActivity;
import io.amosbake.animationsummary.interpolators.InterpolatorActivity;
import io.amosbake.animationsummary.layoutanimation_api1.LayoutAnimationActivity;
import io.amosbake.animationsummary.layouttransition.LayoutTransitionActivity;
import io.amosbake.animationsummary.recyclerviewanim.RecyclerActivity;
import io.amosbake.animationsummary.ripplennreveal.RippleActivity;
import io.amosbake.animationsummary.scenetransition.SceneTransitionActivity;
import io.amosbake.animationsummary.scroller.ViewPositionActivity;
import io.amosbake.animationsummary.touchdemo.TouchActivity;
import io.amosbake.animationsummary.twitterslike.TwitterLikeActivity;
import io.amosbake.animationsummary.wiget.OvalDrawer;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ImageView ivAnimVector;
    private ImageView ivAnimColor;
    private ImageView ivAnimSearch;
    private ImageView ivAnimStar;
    private ImageView ivAnimFiveStar;
    private OvalDrawer ivOvalDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTransitionAnim();
        setupColorAnim();
        setupSearchAnim();
        setupPathTrimAnim();
        setupXmlMorphAnim();
        setupOvalDrawer();
    }

    private void setupOvalDrawer() {
        ivOvalDrawer = (OvalDrawer) findViewById(R.id.ivOvalDrawer);
        findViewById(R.id.btnOvalRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivOvalDrawer.rotate(20f);
            }
        });
    }

    private void setupXmlMorphAnim() {
        ivAnimFiveStar = (ImageView) findViewById(R.id.ivAnimFiveStar);
        findViewById(R.id.btnFiveStarAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable _drawable = ivAnimFiveStar.getDrawable();
                if (_drawable instanceof Animatable){
                    ((Animatable) _drawable).start();
                }
            }
        });
    }

    private void setupPathTrimAnim() {
        ivAnimStar = (ImageView) findViewById(R.id.ivAnimStar);
        findViewById(R.id.btnStarAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable _drawable = ivAnimStar.getDrawable();
                if (_drawable instanceof Animatable){
                    ((Animatable) _drawable).start();
                }
            }
        });
    }

    /**搜索框收缩动画*/
    private void setupSearchAnim() {
        ivAnimSearch = (ImageView) findViewById(R.id.ivAnimSearch);
        findViewById(R.id.btnSearchAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable _drawable = ivAnimSearch.getDrawable();
                if (_drawable instanceof Animatable){
                    ((Animatable) _drawable).start();
                }
            }
        });
    }
    /**颜色变换动画*/
    private void setupColorAnim() {
        ivAnimColor = (ImageView) findViewById(R.id.ivAnimColor);
        findViewById(R.id.btnColorAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable _drawable = ivAnimColor.getDrawable();
                if (_drawable instanceof Animatable){
                    ((Animatable) _drawable).start();
                }
            }
        });
    }
    /**group translateX 动画*/
    private void setupTransitionAnim() {
        ivAnimVector = (ImageView) findViewById(R.id.ivAnimVector);
        findViewById(R.id.btnVectorAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable _drawable = ivAnimVector.getDrawable();
                if (_drawable instanceof Animatable){
                    ((Animatable) _drawable).start();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent _intent = new Intent(this,BezierActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        }else if (id == R.id.action_morph){
            Intent _intent = new Intent(this,PathMorphActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_meature) {
            Intent _intent = new Intent(this, PathMeatureActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_draggable) {
            Intent _intent = new Intent(this, DragViewHelperActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_position) {
            Intent _intent = new Intent(this, ViewPositionActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_transition) {
            Intent _intent = new Intent(this, SceneTransitionActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_touchdemo) {
            Intent _intent = new Intent(this, TouchActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_likebutton) {
            Intent _intent = new Intent(this, TwitterLikeActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_constraint_transition) {
            Intent _intent = new Intent(this, ConstraintTransitionActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_layout_transition) {
            Intent _intent = new Intent(this, LayoutTransitionActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_recycler_view) {
            Intent _intent = new Intent(this, RecyclerActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_animation_misc) {
            Intent _intent = new Intent(this, AnimationMiscActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_layout_animation) {
            Intent _intent = new Intent(this, LayoutAnimationActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        } else if (id == R.id.action_interpolators) {
            Intent _intent = new Intent(this, InterpolatorActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        }else if (id == R.id.action_ripple_effects) {
            Intent _intent = new Intent(this, RippleActivity.class);
            this.startActivity(_intent);
//            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
