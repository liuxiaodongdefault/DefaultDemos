package com.jyd.cartllibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class ShoppingCartAddAnimation {

    private final String TAG = this.getClass().getSimpleName();

    private Activity activity;
    private View shoppingCart;
    //动画时间
    private int AnimationDuration = 900;
    // 正在执行的动画数量
    private int number = 0;
    // 是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;

    private TranslateAnimation horizontalTransAnim;
    private TranslateAnimation downTransAnim;
    private TranslateAnimation upTransAnim;
    private Animation mScaleAnimation;

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {

                    }

                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };

    public int getAnimationDuration() {
        return AnimationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        if (animationDuration > 900) {
            AnimationDuration = animationDuration;
        } else {
            AnimationDuration = 900;
        }
    }

    public ShoppingCartAddAnimation(Activity activity){
        this.activity = activity;
        this.shoppingCart = shoppingCart;
        animation_viewGroup = createAnimLayout();
    }

    public void doAnim(Drawable drawable, int[] start_location, int[] end_location, Boolean isDownToUp) {
        if (!isClean) {
            setAnim(drawable, start_location, end_location, isDownToUp);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location, end_location, isDownToUp);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }
    private FrameLayout createAnimLayout() {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(activity);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
//        lp.setMargins(0, Tool.dip2px(activity, 46f)+statusBarHeight, 0, 0);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    /**
     * @deprecated 将要执行动画的view 添加到动画层
     * @param vg
     *            动画运行的层 这里是frameLayout
     * @param view
     *            要运行动画的View
     * @param location
     *            动画的起始位置
     * @return
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(Tool.dip2px(activity,
                90), Tool.dip2px(activity, 90));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * 商品详情动画效果设置
     *
     * @param drawable
     *            将要加入购物车的商品
     * @param start_location
     *            起始位置
     */
    private void setAnim(Drawable drawable, int[] start_location, int[] end_location, Boolean isDownToUp) {

        Animation mRotateAnimation = new RotateAnimation(0, 900,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setDuration(AnimationDuration);
        AnimationSet mAnimationSet = new AnimationSet(true);
        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);

        final ImageView iview = new ImageView(activity);
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview,
                start_location);
        view.setAlpha(0.6f);

        int endX = end_location[0] - start_location[0];
        int endY = end_location[1] - start_location[1];
        Log.d(TAG, "start:  " + start_location[0] + "  " + start_location[1]);
        Log.d(TAG, "end:  " + end_location[0] + "  " + end_location[1]);
        horizontalTransAnim = new TranslateAnimation(0, endX, 0, 0);
        horizontalTransAnim.setDuration(AnimationDuration);
        if (isDownToUp) {
            downTransAnim = new TranslateAnimation(0, 0, 0, endY - 200);
            downTransAnim.setDuration(800);
            mScaleAnimation = new ScaleAnimation(1.2f, 0.0f, 1.2f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
        } else {
            upTransAnim = new TranslateAnimation(0, 0, 0, -200);
            downTransAnim = new TranslateAnimation(0, 0, 0, endY + 200);
            upTransAnim.setDuration(200);
            downTransAnim.setDuration(800);
            downTransAnim.setStartOffset(200);
            mAnimationSet.addAnimation(upTransAnim);
            mScaleAnimation = new ScaleAnimation(1.2f, 0.0f, 1.2f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                    0.3f);
        }
        mScaleAnimation.setDuration(AnimationDuration);
        mScaleAnimation.setFillAfter(true);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(horizontalTransAnim);
        mAnimationSet.addAnimation(downTransAnim);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub

                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
        view.startAnimation(mAnimationSet);
    }
}
