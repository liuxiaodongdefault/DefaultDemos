package com.jyd.defaultdemos.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jyd.defaultdemos.App;
import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.util.StringUtils;
import com.jyd.defaultdemos.util.ThreadUtil;
import com.jyd.defaultdemos.util.ToastUtil;
import com.jyd.defaultdemos.widget.LoadingDialog;

import java.text.ParseException;

/**
 * Created by dongxiaoliu on 16/6/25.
 * * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected String TAG;
    protected App mApp;
    private LoadingDialog mLoadingDialog;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mApp = App.getInstance();
        context=this;
        initView(savedInstanceState);
        setListener();
        try {
            processLogic(savedInstanceState);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState) throws ParseException;

    /**
     * 需要处理点击事件时，重写该方法
     *
     * @param v
     */
    public void onClick(View v) {
    }

    protected void showToast(String text) {
        ToastUtil.show(text);
    }

    public void showLoadingDialog(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, R.style.MyDialog);
            if (StringUtils.isEmpty(text)) {
                mLoadingDialog.setLoadingText("加载中...");
            } else {
                mLoadingDialog.setLoadingText(text);
            }
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.dismiss();
                }
            }, 300);

        }

    }
}
