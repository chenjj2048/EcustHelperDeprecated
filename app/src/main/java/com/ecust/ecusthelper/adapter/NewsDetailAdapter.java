package com.ecust.ecusthelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Objects;
import com.bumptech.glide.Glide;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.bean.news.NewsDetailItem;

import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/6/20
 *
 * @author chenjj2048
 */
public class NewsDetailAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_PLAIN_TEXT = 1;
    private static final int VIEW_TYPE_PICTURE = 2;
    private final Context context;
    private NewsDetailItem mNewsDetailItem;

    public NewsDetailAdapter(Context context) {
        this.context = context;
    }

    public void setNewsDetailItem(NewsDetailItem newsDetailItem) {
        Objects.requireNonNull(newsDetailItem);
        this.mNewsDetailItem = newsDetailItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder;
        View view;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                view = inflater.inflate(R.layout.item_news_detail_header, parent, false);
                holder = new HeaderHolder(view);
                break;
            case VIEW_TYPE_PLAIN_TEXT:
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new TextHolder(view);
                break;
            case VIEW_TYPE_PICTURE:
                view = new ImageView(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                view = inflater.inflate(R.layout.item_imageview, parent, false);
                holder = new ImageHolder(view);
                break;
            default:
                throw new NoSuchElementException();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((AbstarctViewHolder) viewHolder).updateUI(mNewsDetailItem, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            NewsDetailItem.ContentLine item = mNewsDetailItem.getContentLineList().get(position - 1);
            if (item.isPicture()) {
                return VIEW_TYPE_PICTURE;
            } else {
                return VIEW_TYPE_PLAIN_TEXT;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mNewsDetailItem == null) return 0;

        /**
         * 计算Item数量
         * 头部1个+文字+图片
         */
        return 1 + mNewsDetailItem.getContentLineList().size();
    }

    static abstract class AbstarctViewHolder extends RecyclerView.ViewHolder {
        public AbstarctViewHolder(View itemView) {
            super(itemView);
        }

        protected abstract void updateUI(NewsDetailItem data, int pos);
    }

    /**
     * 文字内容
     */
    static class TextHolder extends AbstarctViewHolder {
        TextView textView;

        public TextHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        protected void updateUI(NewsDetailItem data, int pos) {
            String text = data.getContentLineList().get(pos - 1).getText();
            this.textView.setText(text);
        }
    }

    /**
     * 图片内容
     */
    static class ImageHolder extends AbstarctViewHolder {
        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        protected void updateUI(NewsDetailItem data, int pos) {
            String imageUrl = data.getContentLineList().get(pos - 1).getPictureUrl();
            //Todo:解决View复用问题
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.default_loading_background)
                    .crossFade()
                    .into(imageView);
        }
    }

    /**
     * 顶部信息
     */
    static class HeaderHolder extends AbstarctViewHolder {
        /**
         * 版块名称
         */
        @Bind(R.id.news_detail_section)
        TextView mSection;

        /**
         * 新闻标题
         */
        @Bind(R.id.news_detail_title)
        TextView mTitle;

        /**
         * 新闻信息（稿件来源、作者、访问量等）
         */
        @Bind(R.id.news_detail_moreinfo)
        TextView mMoreInfo;

        /**
         * 新闻时间
         */
        @Bind(R.id.news_detail_time)
        TextView mTime;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void updateUI(NewsDetailItem mData, int pos) {
            String text = makeInfo(mData);
            this.mMoreInfo.setText(text);
            this.mTitle.setText(mData.getTitle());
            this.mSection.setText(mData.getCatalog());
            this.mTime.setText(mData.getReleaseTime());
        }

        /**
         * 产生详细的作者、来源、编辑等等信息
         */
        private String makeInfo(NewsDetailItem mData) {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(mData.getNewsSource()))
                sb.append("稿件来源：").append(mData.getNewsSource()).append("  ");
            if (!TextUtils.isEmpty(mData.getAuthor()))
                sb.append("作者：").append(mData.getAuthor()).append("  ");
            if (!TextUtils.isEmpty(mData.getPhotoAuthor()))
                sb.append("摄影：").append(mData.getPhotoAuthor()).append("  ");
            if (!TextUtils.isEmpty(mData.getEditor()))
                sb.append("编辑：").append(mData.getEditor()).append("  ");
            if (!TextUtils.isEmpty(mData.getCountOfVisits()))
                sb.append("访问量：").append(mData.getCountOfVisits()).append("  ");
            return sb.toString();
        }
    }
}
