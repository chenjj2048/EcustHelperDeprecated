package com.ecust.ecusthelper.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created on 2016/6/27
 *
 * @author chenjj2048
 */
public class GlobalGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        setDecodeFormat(builder);
        setMemoryCache(context, builder);
        setDiskCache(context, builder);
    }

    private void setDecodeFormat(GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    private void setMemoryCache(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    private void setDiskCache(Context context, GlideBuilder builder) {
        final int MB = 1024 * 1024;
        /**
         * Todo:先要做判断是否有外部缓存
         */
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", 100 * MB));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
