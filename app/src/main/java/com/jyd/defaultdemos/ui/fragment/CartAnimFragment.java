package com.jyd.defaultdemos.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.adapter.ProductRvAdapter;
import com.jyd.defaultdemos.model.ProductModel;
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
    private ProductRvAdapter mAdapter;
    private List<ProductModel> mProductList;
    private int page = 1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_cart_anim);

        mRefresh = getViewById(R.id.refresh_cart_frag);
        mRecylerView = getViewById(R.id.rv_cart_frag);
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
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setRecylerView();
    }

    public void setRecylerView() {
        mProductList = new ArrayList<>();
        List<ProductModel> productModelList = initData(page);
        mProductList.addAll(productModelList);
        for (ProductModel model : mProductList) {
            Log.d(TAG, model.getTitle());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecylerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ProductRvAdapter(mActivity, mProductList);
        mRecylerView.setAdapter(mAdapter);
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
