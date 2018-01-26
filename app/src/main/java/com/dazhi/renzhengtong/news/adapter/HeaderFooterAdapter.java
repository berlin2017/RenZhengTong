package com.dazhi.renzhengtong.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public abstract class HeaderFooterAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<T> mData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private List<View> headerViews = new ArrayList<>();
    private List<View> footerViews = new ArrayList<>();

    public HeaderFooterAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public HeaderFooterAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
        layoutInflater = LayoutInflater.from(context);
    }

    public void removeHeaderView(View header) {
        if (headerViews != null && headerViews.contains(header)) {
            int index = headerViews.indexOf(header);
            headerViews.remove(header);
            notifyItemRemoved(index);
        }
    }

    public void removeAllHeaderView() {
        if (headerViews != null && headerViews.size() > 0) {
            notifyItemRangeRemoved(0, getHeaderCount());
            headerViews.clear();
        }
    }

    public void removeFooterView(View footer) {
        if (footerViews != null && footerViews.contains(footer)) {
            int index = footerViews.indexOf(footer);
            notifyItemRemoved(index + getHeaderCount() + mData.size());
            footerViews.remove(footer);
        }
    }

    public void removeAllFooterView() {
        if (footerViews != null && footerViews.size() > 0) {
            notifyItemRangeRemoved(getHeaderCount() + mData.size(), getItemCount());
            footerViews.clear();
        }
    }


    public void addHeaderView(View header, int position) {
        if (!validateAddViewParams(header, position, headerViews)) {
            return;
        }
        headerViews.add(position, header);
        int index = getHeaderCount() + position;
        notifyItemInserted(index);
    }

    public void addHeaderView(View header) {
        if (headerViews == null) {
            headerViews = new ArrayList<>();
            addHeaderView(header, 0);
        } else {
            addHeaderView(header, headerViews.size());
        }
    }

    public void addFooterView(View footer) {
        if (footerViews == null) {
            footerViews = new ArrayList<>();
            addFooterView(footer, 0);
        } else {
            addFooterView(footer, footerViews.size());
        }
    }


    public void addFooterView(View footer, int position) {
        if (!validateAddViewParams(footer, position, footerViews)) {
            return;
        }
        footerViews.add(position, footer);
        int index = getHeaderCount() + position + mData.size();
        notifyItemInserted(index);
    }


    public int getFooterCount() {
        return footerViews.size();
    }

    public int getHeaderCount() {
        return headerViews.size();
    }

    public abstract BaseViewHolder creatViewHolder(ViewGroup parent, int viewType);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View header = getHeaderFromType(viewType);
        if (header != null) {
            return new BaseViewHolder(header);
        }

        View footer = getFooterFromType(viewType);
        if (footer != null) {
            return new BaseViewHolder(footer);
        }

        BaseViewHolder holder = creatViewHolder(parent, viewType);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerViews.size() != 0 && position < getHeaderCount()) {
            return headerViews.get(position).hashCode();
        } else if (footerViews.size() != 0 && position >= mData.size() + getHeaderCount()) {
            return footerViews.get(position - mData.size() - getHeaderCount()).hashCode();
        }
        return super.getItemViewType(position);
    }

    private View getHeaderFromType(int viewType) {
        return this.getViewByHashCodeFromList(this.headerViews, viewType);
    }

    private View getFooterFromType(int viewType) {
        return this.getViewByHashCodeFromList(this.footerViews, viewType);
    }

    private View getViewByHashCodeFromList(List<View> views, int viewType) {
        if (views == null || views.size() == 0) {
            return null;
        }
        for (int i = 0; i < views.size(); i++) {
            if (viewType == views.get(i).hashCode()) {
                return views.get(i);
            }
        }
        return null;
    }

    @Override
    final
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getHeaderCount() != 0 && position < headerViews.size()) {
            return;
        }
        if (getFooterCount() != 0 && position >= mData.size() + headerViews.size()) {
            return;
        }
        convert(holder, position - getHeaderCount());
    }

    abstract
    public void convert(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() + headerViews.size() + footerViews.size();
    }

    /**
     * 参数校验
     *
     * @param view
     * @param position
     * @param views
     * @return
     */
    private boolean validateAddViewParams(View view, int position, List<View> views) {
        if (null == view) {
            return false;
        }
        if (null == views) {
            views = new ArrayList<>();
        }
        if (views.contains(view)) {
            Log.w("HeaderFooterAdapter", "Adapter had contains view");
            return false;
        }

        if (position < 0 || position > views.size()) {
            throw new IndexOutOfBoundsException("header or footer position out of bounds");
        }
        return true;
    }
}
