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

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof OnUIupdateListener)
            ((OnUIupdateListener) viewHolder).onUpdateUI(mNewsDetailItem, position);
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
            return VIEW_TYPE.HEADER_COUNT + mNewsDetailItem.getContentLineList().size() + VIEW_TYPE.FOOTER_COUNT;
        }
    }

    @SuppressWarnings("unused")
    interface OnUIupdateListener<T, R> {
        void onUpdateUI(T data, int pos);

        R getItem(T data, int pos);
    }

    /**
     * View类型
     */
    static final class VIEW_TYPE {
        /**
         * View类型
         */
        public static final int HEADER = 0;
        public static final int PLAIN_TEXT = 1;
        public static final int IMAGE = 2;
        public static final int FOOTER = 3;
        /**
         * 数量
         */
        public static final int HEADER_COUNT = 1;
        public static final int FOOTER_COUNT = 1;
    }

    /**
     * 文字内容
     */
    static class TextHolder extends RecyclerView.ViewHolder implements OnUIupdateListener<NewsDetailItem, String> {
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

        /**
         * 获取文本内容
         *
         * @param data data
         * @param pos  pos
         * @return 文字内容
         */
        @Override
        public String getItem(NewsDetailItem data, int pos) {
            pos -= VIEW_TYPE.HEADER_COUNT;
            NewsDetailItem.ContentLine contentLine = data.getContentLineList().get(pos);
            String text = contentLine.getText();
            text = text.replace("\r\n\r\n", "\r\n").replace("\r\r", "\r").replace("\n\n", "\n");
            return text;
        }

        @Override
        public void onUpdateUI(NewsDetailItem data, int pos) {
            String text = getItem(data, pos);
            this.textView.setText(text);
        }
    }

    /**
     * 图片内容
     */
    static class ImageHolder extends RecyclerView.ViewHolder implements OnUIupdateListener<NewsDetailItem, String> {
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

        /**
         * 获取图片URL地址
         *
         * @param data data
         * @param pos  pos
         * @return 图片URL地址
         */
        @Override
        public String getItem(NewsDetailItem data, int pos) {
            pos -= VIEW_TYPE.HEADER_COUNT;
            NewsDetailItem.ContentLine contentLine = data.getContentLineList().get(pos);
            return contentLine.getPictureUrl();
        }

        @Override
        public void onUpdateUI(NewsDetailItem data, int pos) {
            final String imageUrl = getItem(data, pos);
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
    static class FooterHolder extends RecyclerView.ViewHolder implements OnUIupdateListener<NewsDetailItem, String> {
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
        public void onUpdateUI(NewsDetailItem data, int pos) {
            String text = getItem(data, pos);
            textView.setText(text);
        }

        @Override
        public String getItem(NewsDetailItem data, int pos) {
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
    static class HeaderHolder extends RecyclerView.ViewHolder implements OnUIupdateListener<NewsDetailItem, String> {
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
        public void onUpdateUI(NewsDetailItem data, int pos) {
            String text = getItem(data, pos);
            this.mMoreInfo.setText(text);
            this.mTitle.setText(data.getTitle());
            this.mSection.setText(data.getCatalog());
            this.mTime.setText(data.getReleaseTime());
        }

        @Override
        public String getItem(NewsDetailItem data, int pos) {
            return String.format(Locale.CHINA,
                    "稿件来源：%s  访问量：%s", data.getNewsSource(), data.getCountOfVisits());
        }
    }
}
