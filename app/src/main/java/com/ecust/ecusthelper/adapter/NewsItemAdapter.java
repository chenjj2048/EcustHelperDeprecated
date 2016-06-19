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
import com.ecust.ecusthelper.util.RelativeDateFormat;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsItemAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private static final RelativeDateFormat relativeDate = new RelativeDateFormat(
            new SimpleDateFormat("yyyy-MM-dd"), false);
    private final List<NewsItem> mList;
    private final Context context;

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
                return new NewsTitleDescriptionHolder(view);
            case VIEW_TYPE_FOOTER:
                view = inflater.inflate(R.layout.footer_loading, parent, false);
                return new FooterHolder(view);
            default:
                throw new NoSuchElementException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                final NewsItem item = mList.get(position);
                final NewsTitleDescriptionHolder holder = (NewsTitleDescriptionHolder) viewholder;

                final String relativeTime = relativeDate.parseDateAndTime(item.getTimeValue());

                holder.title.setText(item.getTitle());
                holder.time.setText(item.getTime());
                holder.relativeTime.setText(relativeTime);
                break;
            case VIEW_TYPE_FOOTER:
                break;
            default:
                throw new NoSuchElementException();
        }
    }

    @Override
    public int getItemCount() {
        //+底部
        return mList.size() + 1;
    }

    static class NewsTitleDescriptionHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.relativeTime)
        TextView relativeTime;

        public NewsTitleDescriptionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
