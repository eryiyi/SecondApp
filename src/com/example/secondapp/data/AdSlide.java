package com.example.secondapp.data;

/**
 * Created by Administrator on 2015/8/12.
 *         "id": "9",
 "url": "http://www.baidu.com/",
 "dateline": "1446422704",
 "imgurl": "/Uploads/2015-11-02/5636a8b031af8.png"
 */
public class AdSlide {
    private String id;
    private String url;
    private String dateline;
    private String imgurl;

    public AdSlide(String id, String url, String dateline, String imgurl) {
        this.id = id;
        this.url = url;
        this.dateline = dateline;
        this.imgurl = imgurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
