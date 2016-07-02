package com.ecust.ecusthelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Objects;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.bean.news.NewsDetailItem;

import java.util.Locale;
import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/6/20
 *
 * @author chenjj2048
 */
public class NewsDetailAdapter extends RecyclerView.Adapter {
    private static final int HEAD_COUNT = 1;
    private NewsDetailItem mNewsDetailItem;

    public void setNewsDetailItem(NewsDetailItem newsDetailItem) {
        Objects.requireNonNull(newsDetailItem);
        this.mNewsDetailItem = newsDetailItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case VIEW_TYPE.HEADER:
                holder = HeaderHolder.newInstance(parent);
                break;
            case VIEW_TYPE.FOOTER:
                holder = FooterHolder.newInstantce(parent);
                break;
            case VIEW_TYPE.PLAIN_TEXT:
                holder = TextHolder.newInstance(parent);
                break;
            case VIEW_TYPE.IMAGE:
                holder = ImageHolder.newInstance(parent);
                break;
            default:
                throw new NoSuchElementException("没有对应的ViewHolder");
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
            return VIEW_TYPE.HEADER;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE.FOOTER;
        } else {
            NewsDetailItem.ContentLine item = mNewsDetailItem.getContentLineList().get(position - 1);
            if (item.isPicture()) {
                return VIEW_TYPE.IMAGE;
            } else {
                return VIEW_TYPE.PLAIN_TEXT;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mNewsDetailItem == null)
            return 0;
        else {
            int FOOTER_COUNT = 1;
            return HEAD_COUNT + mNewsDetailItem.getContentLineList().size() + FOOTER_COUNT;
        }
    }

    /**
     * View类型
     */
    static final class VIEW_TYPE {
        public static final int HEADER = 0;
        public static final int PLAIN_TEXT = 1;
        public static final int IMAGE = 2;
        public static final int FOOTER = 3;
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
            textView.setLineSpacing(0, 1.25f);
        }

        public static RecyclerView.ViewHolder newInstance(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TextHolder(view);
        }

        private String getItemText(NewsDetailItem data, int pos) {
            NewsDetailItem.ContentLine contentLine = data.getContentLineList().get(pos - HEAD_COUNT);
            return contentLine.getText();
        }

        @Override
        protected void updateUI(NewsDetailItem data, int pos) {
            String text = getItemText(data, pos);
            text = text.replace("\r\n\r\n", "\r\n").replace("\r\r", "\r").replace("\n\n", "\n");
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

        public static RecyclerView.ViewHolder newInstance(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_imageview, parent, false);
            return new ImageHolder(view);
        }

        private String getItemImageUrl(NewsDetailItem data, int pos) {
            NewsDetailItem.ContentLine contentLine = data.getContentLineList().get(pos - HEAD_COUNT);
            return contentLine.getPictureUrl();
        }

        @Override
        protected void updateUI(NewsDetailItem data, int pos) {
            final String imageUrl = getItemImageUrl(data, pos);
            final Context context = imageView.getContext();

            Glide.with(context)
                    .load(imageUrl)
                    .crossFade()
                    .placeholder(R.drawable.ic_picture_loading)
                    .error(R.drawable.ic_picture_loading_error)
                    .fitCenter()
//                    .bitmapTransform(new BlurTransformation(context, 50))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);
        }
    }

    /**
     * 底部信息（作者、摄影、编辑）
     */
    static class FooterHolder extends AbstarctViewHolder {
        TextView textView;

        public FooterHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setGravity(Gravity.END);
        }

        public static RecyclerView.ViewHolder newInstantce(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new FooterHolder(view);
        }

        @Override
        protected void updateUI(NewsDetailItem data, int pos) {
            String text = getItemText(data);
            textView.setText(text);
        }

        private String getItemText(NewsDetailItem data) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            if (!TextUtils.isEmpty(data.getAuthor()))
                sb.append("作者:").append(data.getAuthor()).append(" ");
            if (!TextUtils.isEmpty(data.getPhotoAuthor()))
                sb.append("摄影:").append(data.getPhotoAuthor()).append(" ");
            if (!TextUtils.isEmpty(data.getEditor()))
                sb.append("编辑:").append(data.getEditor()).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");

            return sb.toString();
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

        public static RecyclerView.ViewHolder newInstance(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_news_detail_header, parent, false);
            return new HeaderHolder(view);
        }

        @Override
        protected void updateUI(NewsDetailItem mData, int pos) {
            String text = String.format(
                    Locale.CHINA,
                    "稿件来源：%s  访问量：%s", mData.getNewsSource(), mData.getCountOfVisits());
            this.mMoreInfo.setText(text);
            this.mTitle.setText(mData.getTitle());
            this.mSection.setText(mData.getCatalog());
            this.mTime.setText(mData.getReleaseTime());
        }
    }
}
