package com.example.remember.newsapp.beans.newsbeans;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Remember on 2017/9/2.
 */

public class News extends DataSupport implements Serializable{

        /**
         * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/8259b113c948415b9c842cf24c1a0fc720170902204833.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/4d76bd17f694404fb2567d42b7043ff620170902204833.jpeg"}]
         * template : normal1
         * skipID : 00AP0001|2273543
         * lmodify : 2017-09-03 00:08:46
         * postid : PHOT25C87000100A
         * source : 中国新闻网
         * title : 百年禅寺大雄宝殿平移30米 系国内首例
         * mtime : 2017-09-03 00:08:46
         * hasImg : 1
         * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
         * digest :
         * photosetID : 00AP0001|2273543
         * boardid : photoview_bbs
         * alias : Top News
         * hasAD : 1
         * imgsrc : http://cms-bucket.nosdn.127.net/c6cbef88f2f241f68cfde564199e8f6320170902204833.jpeg
         * ptime : 2017-09-02 20:50:24
         * daynum : 17412
         * hasHead : 1
         * order : 1
         * votecount : 3224
         * hasCover : false
         * docid : 9IG74V5H00963VRO_CTBUL6DSbjqiaojingupdateDoc
         * tname : 头条
         * priority : 354
         * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2273555","tag":"photoset","title":"无视争议!美第一夫人再穿高跟鞋赴灾区","imgsrc":"http://cms-bucket.nosdn.127.net/5f095b2e1f7f40a4921ce552935853f420170902221703.jpeg","url":"00AO0001|2273555"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2273521","tag":"photoset","title":"航拍美国得州飓风灾区 房屋被洪水围困","imgsrc":"http://cms-bucket.nosdn.127.net/ec077d1614934988ae4e261997534f5620170902182652.jpeg","url":"00AO0001|2273521"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2273522","tag":"photoset","title":"新生报到数千车辆接送 体育场变停车场","imgsrc":"http://cms-bucket.nosdn.127.net/1a45b6055e5e43afa3d1e96d352bf8bf20170902173111.jpeg","url":"00AP0001|2273522"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2273516","tag":"photoset","title":"俄驻旧金山领馆冒黑烟 此前FBI欲搜查","imgsrc":"http://cms-bucket.nosdn.127.net/be33dfb20f934f7c84ea3da9d631679920170902163835.jpeg","url":"00AO0001|2273516"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2273515","tag":"photoset","title":"男童掉入数十米深井 十余台挖掘机救援","imgsrc":"http://cms-bucket.nosdn.127.net/8669c56955e6409982dd40f7d8194aa020170902165259.jpeg","url":"00AP0001|2273515"}]
         * ename : androidnews
         * replyCount : 3426
         * imgsum : 8
         * hasIcon : false
         * skipType : photoset
         * cid : C1348646712614
         * url_3w : http://news.163.com/17/0902/23/CTC7GOU30001875N.html
         * url : http://3g.163.com/news/17/0902/23/CTC7GOU30001875N.html
         * ltitle : 东风导弹发射成功 他奖励官兵仅2个煮熟的土豆
         * subtitle :
         */
        private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    private int id;
    private int type;     //类别类型

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //        private String template;
//        private String skipID;
//        private String lmodify;
//        private String postid;
        private String source;
        private String title;
        private String mtime;
//        private int hasImg;
//        private String topic_background;
//        private String digest;
//        private String photosetID;
//        private String boardid;
//        private String alias;
//        private int hasAD;
        private String imgsrc;
//        private String ptime;
//        private String daynum;
//        private int hasHead;
//        private int order;
//        private int votecount;
//        private boolean hasCover;
//        private String docid;
//        private String tname;
//        private int priority;
//        private String ename;
//        private int replyCount;
//        private int imgsum;
//        private boolean hasIcon;
//        private String skipType;
//        private String cid;
//        private String url_3w;
//        private String url;
//        private String ltitle;
//        private String subtitle;
//        @SerializedName("imgextra")
//        private List<ImageSrcBean> imageSrcList;
//        private List<AdsBean> ads;
//
//        public String getTemplate() {
//            return template;
//        }
//
//        public void setTemplate(String template) {
//            this.template = template;
//        }
//
//        public String getSkipID() {
//            return skipID;
//        }
//
//        public void setSkipID(String skipID) {
//            this.skipID = skipID;
//        }
//
//        public String getLmodify() {
//            return lmodify;
//        }
//
//        public void setLmodify(String lmodify) {
//            this.lmodify = lmodify;
//        }
//
//        public String getPostid() {
//            return postid;
//        }
//
//        public void setPostid(String postid) {
//            this.postid = postid;
//        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

//        public int getHasImg() {
//            return hasImg;
//        }
//
//        public void setHasImg(int hasImg) {
//            this.hasImg = hasImg;
//        }
//
//        public String getTopic_background() {
//            return topic_background;
//        }
//
//        public void setTopic_background(String topic_background) {
//            this.topic_background = topic_background;
//        }
//
//        public String getDigest() {
//            return digest;
//        }
//
//        public void setDigest(String digest) {
//            this.digest = digest;
//        }
//
//        public String getPhotosetID() {
//            return photosetID;
//        }
//
//        public void setPhotosetID(String photosetID) {
//            this.photosetID = photosetID;
//        }
//
//        public String getBoardid() {
//            return boardid;
//        }
//
//        public void setBoardid(String boardid) {
//            this.boardid = boardid;
//        }
//
//        public String getAlias() {
//            return alias;
//        }
//
//        public void setAlias(String alias) {
//            this.alias = alias;
//        }
//
//        public int getHasAD() {
//            return hasAD;
//        }
//
//        public void setHasAD(int hasAD) {
//            this.hasAD = hasAD;
//        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
//
//        public String getPtime() {
//            return ptime;
//        }
//
//        public void setPtime(String ptime) {
//            this.ptime = ptime;
//        }
//
//        public String getDaynum() {
//            return daynum;
//        }
//
//        public void setDaynum(String daynum) {
//            this.daynum = daynum;
//        }
//
//        public int getHasHead() {
//            return hasHead;
//        }
//
//        public void setHasHead(int hasHead) {
//            this.hasHead = hasHead;
//        }
//
//        public int getOrder() {
//            return order;
//        }
//
//        public void setOrder(int order) {
//            this.order = order;
//        }
//
//        public int getVotecount() {
//            return votecount;
//        }
//
//        public void setVotecount(int votecount) {
//            this.votecount = votecount;
//        }
//
//        public boolean isHasCover() {
//            return hasCover;
//        }
//
//        public void setHasCover(boolean hasCover) {
//            this.hasCover = hasCover;
//        }
//
//        public String getDocid() {
//            return docid;
//        }
//
//        public void setDocid(String docid) {
//            this.docid = docid;
//        }
//
//        public String getTname() {
//            return tname;
//        }
//
//        public void setTname(String tname) {
//            this.tname = tname;
//        }
//
//        public int getPriority() {
//            return priority;
//        }
//
//        public void setPriority(int priority) {
//            this.priority = priority;
//        }
//
//        public String getEname() {
//            return ename;
//        }
//
//        public void setEname(String ename) {
//            this.ename = ename;
//        }
//
//        public int getReplyCount() {
//            return replyCount;
//        }
//
//        public void setReplyCount(int replyCount) {
//            this.replyCount = replyCount;
//        }
//
//        public int getImgsum() {
//            return imgsum;
//        }
//
//        public void setImgsum(int imgsum) {
//            this.imgsum = imgsum;
//        }
//
//        public boolean isHasIcon() {
//            return hasIcon;
//        }
//
//        public void setHasIcon(boolean hasIcon) {
//            this.hasIcon = hasIcon;
//        }
//
//        public String getSkipType() {
//            return skipType;
//        }
//
//        public void setSkipType(String skipType) {
//            this.skipType = skipType;
//        }
//
//        public String getCid() {
//            return cid;
//        }
//
//        public void setCid(String cid) {
//            this.cid = cid;
//        }
//
//        public String getUrl_3w() {
//            return url_3w;
//        }
//
//        public void setUrl_3w(String url_3w) {
//            this.url_3w = url_3w;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getLtitle() {
//            return ltitle;
//        }
//
//        public void setLtitle(String ltitle) {
//            this.ltitle = ltitle;
//        }
//
//        public String getSubtitle() {
//            return subtitle;
//        }
//
//        public void setSubtitle(String subtitle) {
//            this.subtitle = subtitle;
//        }
//
//        public List<ImageSrcBean> getImgextra() {
//            return imageSrcList;
//        }
//
//        public void setImgextra(List<ImageSrcBean> imageSrcList) {
//            this.imageSrcList = imageSrcList;
//        }
//
//        public List<AdsBean> getAds() {
//            return ads;
//        }
//
//        public void setAds(List<AdsBean> ads) {
//            this.ads = ads;
//        }
//
}
