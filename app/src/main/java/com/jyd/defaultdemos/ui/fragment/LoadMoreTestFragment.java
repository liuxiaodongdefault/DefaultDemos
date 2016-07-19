package com.jyd.defaultdemos.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongxiaoliu on 16/7/19.
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
public class LoadMoreTestFragment extends BaseFragment {

    private ListView mLv;
    private List<String> mStrList;
    private int mPage = 1;
    private View mFooterView;
    private LinearLayout mFooter;
    private LoadMoreListAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_loadmore_test);
        mLv = getViewById(R.id.loadmore_fragment_lv);
        mFooterView = View.inflate(mActivity, R.layout.footer_list_loadmore, null);
        mFooter = (LinearLayout) mFooterView.findViewById(R.id.footer_layout);
    }

    @Override
    protected void setListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("点击了item" + position);
            }
        });
        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (mPage == 5) {
                            mFooter.setVisibility(View.GONE);
                            return;
                        }
                        initData();
                        ThreadUtil.runInUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        }, 2000);

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mStrList = new ArrayList<>();
        initData();
        mAdapter = new LoadMoreListAdapter(mActivity);
        mLv.addFooterView(mFooterView);
        mLv.setAdapter(mAdapter);
        if (mStrList.size() < 5) {
            mFooter.setVisibility(View.GONE);
        }
    }

    public void initData() {
        for (int i = 0; i < 15; i ++) {
            mStrList.add("第" + mPage + "页" + "第" + i + "个");
        }
        mPage ++;
    }

    class LoadMoreListAdapter extends BaseAdapter {

        Context context;

        public LoadMoreListAdapter (Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mStrList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
                holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mStrList.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
