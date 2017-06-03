package io.amosbake.animationsummary.recyclerviewanim;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lexing.lrecyclerview.adapter.RecyclerHolder;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by Ray on 2017/6/1.
 */

public class CustomViewHolder extends RecyclerHolder implements AnimateViewHolder {

    public CustomViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(itemView, itemView.getWidth() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationX(-itemView.getWidth() * 0.3f)
                .alpha(1)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .setListener(listener)
                .start();
    }

    @Override
    public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(itemView)
                .translationX(itemView.getWidth() * 0.3f)
                .alpha(0)
                .setDuration(300)
                .setListener(listener)
                .setInterpolator(new LinearInterpolator())
                .start();
    }
}
