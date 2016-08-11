package com.example.lenovo.timescroller.Adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by chris on 16/4/15.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> lists;

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public abstract boolean isPinnedItem(int viewType);
/**
* @Description: 当未设置lists的时候会导致recyclerView数据为空
* @param ${tags}
* @return ${return_type}
* @throws
*/
    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }
}
