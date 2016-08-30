package com.ecust.ecusthelper.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.util.SizeUtil;
import com.ecust.ecusthelper.util.gitrepository.TextDrawable;

import java.util.List;
import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
//Todo:增加Sticy效果，年月放在上面
public class NewsItemAdapter extends RecyclerView.Adapter {
    public static final int COUNT_OF_FOOTER = 1;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;
    private final List<NewsItem> mList;
    private final Context context;
    private OnItemViewClickListener mListener;
    /**
     * true时为正在加载，false时为已无更多数据
     */
    private boolean isShowLoadingMore = true;

    public NewsItemAdapter(Context context, List<NewsItem> list) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(list);
        this.context = context;
        this.mList = list;
    }

    /**
     * 隐藏底部的“正在加载”字样
     * 滑至最底部无更多数据时调用
     */
    public void hideFooterLoaing() {
        this.isShowLoadingMore = false;
        this.notifyDataSetChanged();
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
        if (viewholder instanceof ContentHolder) {
            final ContentHolder holder = (ContentHolder) viewholder;
            final NewsItem item = mList.get(position);

            holder.updateUI(item);
        } else if (viewholder instanceof FooterHolder) {
            final FooterHolder holder = (FooterHolder) viewholder;
            if (!isShowLoadingMore) {
                holder.progressBar.setVisibility(View.GONE);
                holder.textView.setText("已无更多数据加载");
            }
        }
    }

    @Override
    public int getItemCount() {
        //+底部*1
        return mList.size() + 1;
    }

    public void setOnItemClickListener(OnItemViewClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemViewClickListener {
        void onItemClick(View v, NewsItem newsItem, int pos);
    }

    static class FooterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.textView)
        TextView textView;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ContentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.pictureTextDrawable)
        ImageView pictureImageView;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
            drawPictureTextDrawable();
        }

        /**
         * 绘制图片边框文字Drawable
         */
        private void drawPictureTextDrawable() {
            int color = pictureImageView.getResources().getColor(android.R.color.holo_red_light);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(1, color)
                    .fontSize(SizeUtil.dp2px(context, 12))
                    .textColor(color)
                    .endConfig()
                    .buildRoundRect("图", Color.TRANSPARENT, SizeUtil.dp2px(context, 14));
            pictureImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                final ContentHolder holder = (ContentHolder) v.getTag();
                final int pos = holder.getLayoutPosition();
                mListener.onItemClick(v, mList.get(pos), pos);
            }
        }

        public void updateUI(NewsItem item) {
            final String str = "[图文]";
            title.setText(item.getTitle().replace(str, ""));
            time.setText(item.getTime() + "  " + item.getRelativeTimeFromNow());
            pictureImageView.setVisibility(item.getTitle().contains(str) ? View.VISIBLE : View.GONE);
        }
    }
}
