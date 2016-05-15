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
public class NewsTitleDescriptionAdapter extends RecyclerView.Adapter<NewsTitleDescriptionAdapter.NewsTitleDescriptionHolder> {
    private Context context;
    private List<NewsItem> mList;

    public NewsTitleDescriptionAdapter(Context context, List<NewsItem> list) {
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

        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NewsTitleDescriptionHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text1)
        TextView textView1;
        @Bind(R.id.text2)
        TextView textView2;

        public NewsTitleDescriptionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
