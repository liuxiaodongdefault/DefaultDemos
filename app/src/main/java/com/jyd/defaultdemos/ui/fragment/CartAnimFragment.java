package com.jyd.defaultdemos.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.jyd.cartllibrary.ShoppingCartAddAnimation;
import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.adapter.ProductRvAdapter;
import com.jyd.defaultdemos.callback.CartAddListener;
import com.jyd.defaultdemos.model.ProductModel;
import com.jyd.defaultdemos.util.MyLog;
import com.jyd.defaultdemos.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by dongxiaoliu on 16/7/4.
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
public class CartAnimFragment extends BaseFragment {

    private PtrClassicFrameLayout mRefresh;
    private RecyclerView mRecylerView;
    private ImageView fab;
    private ProductRvAdapter mAdapter;
    private List<ProductModel> mProductList;
    private int page = 1;
    private ShoppingCartAddAnimation cartAddAnimation;
    private int[] end_location;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_cart_anim);

        mRefresh = getViewById(R.id.refresh_cart_frag);
        mRecylerView = getViewById(R.id.rv_cart_frag);
        fab = getViewById(R.id.fab);
    }

    @Override
    protected void setListener() {
        mRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //模拟加载网络数据，延时3秒
                        page = 0;
                        mProductList = initData(0);
                        mAdapter.notifyDataSetChanged();
                        mRefresh.refreshComplete();
                    }
                }, 3000);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "I'm shoppingcart!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setRecylerView();
    }

    public void setRecylerView() {
        mProductList = new ArrayList<>();
        List<ProductModel> productModelList = initData(page);
        mProductList.addAll(productModelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2, LinearLayoutManager.VERTICAL, false);
        mRecylerView.setLayoutManager(gridLayoutManager);
        mAdapter = new ProductRvAdapter(mActivity, mProductList);
        mRecylerView.setAdapter(mAdapter);
        mAdapter.setCartAddListener(new CartAddListener() {
            @Override
            public void cartAdd(Drawable drawable, int[] start_location) {
                if (cartAddAnimation == null) {
                    cartAddAnimation = new ShoppingCartAddAnimation(mActivity);
                }
                end_location = new int[2];
                fab.getLocationInWindow(end_location);
                cartAddAnimation.doAnim(drawable, start_location, end_location, false);
                MyLog.d(TAG, "start:  " + start_location[0] + "  " + start_location[1]);
                MyLog.d(TAG, "end:  " + end_location[0] + "  " + end_location[1]);
            }
        });
    }

    /**
     * 模拟数据
     */
    public List<ProductModel> initData(int page) {
        List<ProductModel> productModelList = new ArrayList<>();
        for (int i = page * 10 - 10; i < page * 10; i++) {
            ProductModel productModel = new ProductModel();
            productModel.setTitle("我是商品" + i);
            productModel.setPrice("￥99.99");
            if (i < 34) {
                productModel.setImg("http://o9sc60f6f.bkt.clouddn.com/" + i);
            } else {
                productModel.setImg("http://o9sc60f6f.bkt.clouddn.com/" + (i - 34));
            }
            productModelList.add(productModel);
        }
        return productModelList;
    }

}
