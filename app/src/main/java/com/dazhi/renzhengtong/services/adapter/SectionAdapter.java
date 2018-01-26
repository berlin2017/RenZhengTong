package com.dazhi.renzhengtong.services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dazhi.renzhengtong.news.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public abstract class SectionAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    private List<List<T>> mData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private List<View> headerViews = new ArrayList<>();
    private List<View> footerViews = new ArrayList<>();
    private final int IS_SECTION_TYPE = 1;

    public SectionAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public SectionAdapter(Context context, List<List<T>> data) {
        mContext = context;
        mData = data;
        layoutInflater = LayoutInflater.from(context);
    }

    public abstract void convert(RecyclerView.ViewHolder holder, int position);

    public abstract void onBindSectionViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract BaseViewHolder creatViewHolder(ViewGroup parent, int viewType);

    public abstract RecyclerView.ViewHolder creatSectionViewHolder(ViewGroup parent, int viewType);

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
        int index = getHeaderCount() + position + getTotalDataCount();
        notifyItemInserted(index);
    }

    public int getFooterCount() {
        return footerViews.size();
    }

    public int getHeaderCount() {
        return headerViews.size();
    }

    public int getTotalDataCount() {
        int i = 0;
        for (List<T> list : mData) {
            i += list.size() + 1;
        }
        return i;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View header = getHeaderFromType(viewType);
        if (header != null) {
            return new BaseViewHolder(header);
        }

        View footer = getFooterFromType(viewType);
        if (footer != null) {
            return new BaseViewHolder(footer);
        }
        if (viewType == IS_SECTION_TYPE) {
            return creatSectionViewHolder(parent, viewType);
        }
        BaseViewHolder holder = creatViewHolder(parent, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getHeaderCount() != 0 && position < headerViews.size()) {
            return;
        }
        if (getFooterCount() != 0 && position >= mData.size() + headerViews.size()) {
            return;
        }
        if (isSection(position)) {
            onBindSectionViewHolder(holder, position);
            return;
        }
        convert(holder, position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getTotalDataCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position)) {
            return IS_SECTION_TYPE;
        }
        if (headerViews.size() != 0 && position < getHeaderCount()) {
            return headerViews.get(position).hashCode();
        } else if (footerViews.size() != 0 && position >= getTotalDataCount() + getHeaderCount()) {
            return footerViews.get(position - getTotalDataCount() - getHeaderCount()).hashCode();
        }
        return super.getItemViewType(position);
    }

    public boolean isSection(int position) {
        for (int i = 0; i < mData.size(); i++) {
            if (position == getUpCount(i) + getHeaderCount()) {
                return true;
            }
        }
        return false;
    }

    public int getUpCount(int index) {
        int count = 0;
        for (int i = 0; i < index; i++) {
            count += mData.get(i).size() + 1;
        }
        return count;
    }
}

