package com.example.todoapplication;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class PagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position >= -1 || position <= 1){
            //Modify the default slider transition to shrink the page as well
            final float height = page.getHeight();
            final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            final float vertMargin = height * ( 1 - scaleFactor) / 2;
            final float horzMargin = page.getWidth() * ( 1 - scaleFactor) / 2;

            //center vertically

            page.setPivotY((0.5f * height));

            if(position < 0){
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            //scale the page down between MIN_SCALE and 1
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            //fade the page relative to its size

            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / ( 1 - MIN_SCALE) * ( 1 - MIN_ALPHA));
        }
    }
}

