package com.jyd.defaultdemos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.callback.RvOnItemClickListener;
import com.jyd.defaultdemos.model.ProductModel;
import com.jyd.defaultdemos.util.ImageDisplayer;

import java.util.List;

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
public class ProductRvAdapter extends RecyclerView.Adapter<ProductRvAdapter.ViewHolder> {

    private RvOnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<ProductModel> mList;
    private ImageDisplayer displayer;

    public ProductRvAdapter(Context context, List<ProductModel> modelList) {
        this.mContext = context;
        this.mList = modelList;
        displayer = new ImageDisplayer();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_cart_frag, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.productIv = (ImageView) view.findViewById(R.id.iv_img_cart_frag);
        viewHolder.cartAddIv = (ImageView) view.findViewById(R.id.iv_add_cart_frag);
        viewHolder.titleTv = (TextView) view.findViewById(R.id.tv_title_cart_frag);
        viewHolder.priceTv = (TextView) view.findViewById(R.id.tv_price_cart_frag);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.titleTv.setText(mList.get(position).getTitle());
        holder.priceTv.setText(mList.get(position).getPrice());
        displayer.displayImage(mContext, mList.get(position).getImg(), holder.productIv);
        holder.cartAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(RvOnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productIv, cartAddIv;
        TextView titleTv, priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
