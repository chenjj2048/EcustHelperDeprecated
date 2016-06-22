package com.ecust.ecusthelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.bean.news.NewsItem;

import java.util.List;
import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsItemAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;
    private final List<NewsItem> mList;
    private final Context context;
    private OnRecyclerViewItemClickListener mListener;

    public NewsItemAdapter(Context context, List<NewsItem> list) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(list);
        this.context = context;
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return VIEW_TYPE_FOOTER;
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = inflater.inflate(R.layout.item_news_description, parent, false);
                //绑定Item点击事件
                view.setOnClickListener(this);
                return new ContentHolder(view);
            case VIEW_TYPE_FOOTER:
                view = inflater.inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
            default:
                throw new NoSuchElementException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if (viewholder instanceof ContentHolder){
            final ContentHolder holder = (ContentHolder) viewholder;
            final NewsItem item = mList.get(position);

            holder.title.setText(item.getTitle());
            holder.time.setText(item.getTime());
            holder.relativeTime.setText(item.getRelativeTime());
        }
    }

    @Override
    public int getItemCount() {
        //+底部
        return mList.size() + 1;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            final ContentHolder holder = (ContentHolder) v.getTag();
            final int pos = holder.getLayoutPosition();
            mListener.onItemClick(v, mList.get(pos), pos);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View v, NewsItem newsItem, int pos);
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.relativeTime)
        TextView relativeTime;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }

    static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
