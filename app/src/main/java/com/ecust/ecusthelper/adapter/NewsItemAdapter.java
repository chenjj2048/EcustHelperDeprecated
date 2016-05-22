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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsTitleDescriptionHolder> {
    private final List<NewsItem> mList;
    private final Context context;

    public NewsItemAdapter(Context context, List<NewsItem> list) {
        Objects.requireNonNull(list);
        this.context = context;
        this.mList = list;
    }

    @Override
    public NewsTitleDescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_news_description, parent, false);
        return new NewsTitleDescriptionHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsTitleDescriptionHolder holder, int position) {
        final NewsItem item = mList.get(position);

        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class NewsTitleDescriptionHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.timediff)
        TextView timeDiff;

        public NewsTitleDescriptionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
