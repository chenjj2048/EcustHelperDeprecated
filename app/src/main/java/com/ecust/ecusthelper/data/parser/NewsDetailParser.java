package com.ecust.ecusthelper.data.parser;

import android.annotation.SuppressLint;
import android.text.Html;

import com.annimon.stream.function.Function;
import com.ecust.ecusthelper.bean.news.NewsDetailItem;
import com.ecust.ecusthelper.util.log.logUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2016/6/19
 *
 * @author chenjj2048
 */
public class NewsDetailParser implements Function<String, NewsDetailItem> {
    private static final String TAG = NewsDetailParser.class.getCanonicalName();
    private final String mCatalogName;

    public NewsDetailParser(String catalogName) {
        this.mCatalogName = catalogName;
        logUtil.d(this, "数据解析准备就绪 " + catalogName);
    }

    //解析新闻标题（可能两行，甚至更多）
    private static String parse_Title(Element content) {
        Element content_title = content.getElementsByClass("content_title").first();
        Elements collection_h2 = content_title.getElementsByTag("h2");

        String title = "";
        for (int i = 0; i < collection_h2.size(); i++) {
            Element h2 = collection_h2.get(i);
            String line = h2.text().trim();
            if (!line.equals("")) {
                if (title.length() == 0)
                    title += line;
                else
                    title += "\r\n" + line;
            }
        }
        return title;
    }

    /**
     * 2.解析新闻头部
     * 一般版块为；稿件来源、作者、摄影、编辑、访问量
     * 通知公告版块为：发表日期、来稿单位、访问量
     * <p>
     * 对照网页源代码具体来看这意思
     *
     * @param content html中对应的标签
     * @param target  解析后的数据要保存的位置
     */
    private static void parseHeader(Element content, NewsDetailItem target) {
        /**
         *  解析头部信息
         */
        Element titles = content.getElementsByClass("titles").first();
        String moreinfo = titles.toString().replace("amp;", "").replace("&nbsp;", " ");
        moreinfo = Html.fromHtml(moreinfo).toString();
        moreinfo = moreinfo.replace("：", ":");
        moreinfo = moreinfo.replace("\r", "").replace("\n", "").trim();

        /**
         * moreinfo字符串此时类似于以下形式
         * 一般版块：稿件来源: 社会学院  |   作者:社会学院  |  摄影:社会学院  |  编辑:亦枫  |  访问量:452
         * 通知公告版块：发表日期：2015-06-01 |  来稿单位:党委宣传部  |  访问量:894
         */

        String[] collection_info = moreinfo.split(" \\| ");       //分割|
        for (String part_info : collection_info) {
            part_info = part_info.trim();

            if (part_info.endsWith(":"))
                continue;                                      //没有名字就跳过，分析下一个

            String[] s = part_info.split(":");               //分割如  "访问量:1456"
            String leftString = s[0].trim();
            String rightString = s[1].trim();
            switch (leftString) {
                case "发表日期":
                    target.setReleaseTime(rightString);                   //如：2015-06-01
                    break;
                case "稿件来源":
                case "来稿单位":
                    target.setNewsSource(rightString);
                    break;
                case "作者":
                    target.setAuthor(rightString);
                    break;
                case "摄影":
                    target.setPhotoAuthor(rightString);
                    break;
                case "编辑":
                    target.setEditor(rightString);
                    break;
                case "访问量":
                    target.setCountOfVisits(rightString);
                    break;
            }
        }
    }

    /**
     * 解析新闻内容部分
     */
    private static String extractBody(Element content) {
        Element content_main = content.getElementsByClass("content_main").first();
        Element titles = content_main.getElementsByClass("titles").first();

        String body = Html.fromHtml(content_main.toString()).toString();
        String title = Html.fromHtml(titles.toString()).toString();
        //去除头部消息
        body = body.replace(title, "");
        //去除尾部消息
        int pos = body.lastIndexOf("发布日期");
        if (pos <= 0) {
            pos = body.lastIndexOf("分享文章");
        }
        body = body.substring(0, pos);

        //替换出图片标签[img]
        final String flag_img = Html.fromHtml("<img src=\"/UploadFile/1234.jpg\" alt=\"\" />").toString();
        body = body.replace(flag_img, "[img]");

        return body;
    }

    /**
     * 解析出图片部分
     *
     * @param content html
     * @return 返回的图片地址数据
     */
    private static List<String> parseImageList(Element content) {
        List<String> result = new ArrayList<>();
        final String URL_PREFIX = "http://news.ecust.edu.cn";

        //获取图片标签
        //也可以用这句 Elements collection_img = content.select("img");
        Elements collection_img = content.getElementsByTag("img");

        //遍历取出图片
        for (int i = 0; i < collection_img.size(); i++) {
            Element img = collection_img.get(i);
            String img_url = img.attr("src");             //网页图片的地址

            //加入src="/UploadFile/DES/2015/143617254256974.jpg"
            //这时就添加http://news.ecust.edu.cn前缀
            if (!img_url.startsWith("http://"))
                img_url = URL_PREFIX + img_url;

            //添加图片地址到数据集,过滤掉底部四个推广链接的图片（微信订阅号、新浪微博、腾讯微博）
            if (!img_url.contains("/assets/news/")) {
                result.add(img_url);
            }
        }
        return result;
    }


    /**
     * @param bodyString 文字集合
     * @param imageList  图片集合
     * @return 组合的数据结构
     */
    private static List<NewsDetailItem.ContentLine> parseTextAndPicture(String bodyString, List<String> imageList) {
        List<NewsDetailItem.ContentLine> result = new ArrayList<>();
        String[] lines = bodyString.split("\\[img\\]");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.equals("")) {
                //添加文本
                result.add(NewsDetailItem.ContentLine.ofText(line));
            }
            /**
             * 文字会整块整块连在一起
             * 所以下面必定是图片URL
             */

            //非最后一行
            if (i != lines.length - 1) {
                final String pictureUrl = imageList.get(i);
                NewsDetailItem.ContentLine picLine = NewsDetailItem.ContentLine.ofPicture(pictureUrl);
                result.add(picLine);     //添加图片加载地址
            }
        }
        return result;
    }

    //解析全部数据
    public void parseAllData(String html, NewsDetailItem target) {
        logUtil.d(this, "开始解析网络数据");
        Document doc = Jsoup.parse(html);
        Element content = doc.getElementsByClass("content").first();

        target.setCatalog(mCatalogName);

        //1.解析新闻标题(新闻可能多行)
        logUtil.d(this, "开始解析标题");
        String title = parse_Title(content);
        target.setTitle(title);

        //2.解析头部数据
        logUtil.d(this, "开始解析头部数据");
        parseHeader(content, target);

        //3.解析底部日期(通知公告版块没有底部日期)
        if (!mCatalogName.equals("通知公告")) {
            logUtil.d(this, "开始解析时间");
            try {
                String time = parseTime(content);
                target.setReleaseTime(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //4.解析新闻主体部分
        logUtil.d(this, "开始提取主体内容");
        String bodyString = extractBody(content);

        //5.解析图片网址
        logUtil.d(this, "开始提取图片地址");
        List<String> mImageList = parseImageList(content);

        //6.从纯文本中解析出一行行内容（图、文字交替）
        logUtil.d(this, "开始合成文字内容及图片");
        List<NewsDetailItem.ContentLine> mList = parseTextAndPicture(bodyString, mImageList);
        target.setContentLineList(mList);
    }

    /**
     * 解析出时间
     *
     * @param content content
     * @return 如：2015-07-02 10:32
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    private String parseTime(Element content) throws ParseException {
        String body = Html.fromHtml(content.toString()).toString();
        String date = body.substring(body.lastIndexOf("发布日期"));
        date = date.substring(0, date.indexOf("分") + 1);      //如：2015年07月02日10时32分

        SimpleDateFormat format1 = new SimpleDateFormat("发布日期：yyyy年MM月dd日HH时mm分");
        Date time = format1.parse(date);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date = format2.format(time);
        return date;
    }

    @Override
    public NewsDetailItem apply(String value) {
        NewsDetailItem result = new NewsDetailItem();
        parseAllData(value, result);
        return result;
    }
}