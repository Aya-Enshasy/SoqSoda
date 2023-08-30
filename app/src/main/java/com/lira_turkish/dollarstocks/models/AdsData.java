package com.lira_turkish.dollarstocks.models;

public class AdsData {

    private String image;
    private String Link;
    private String desc;
    private String state;

    public AdsData() {
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getLink() {
        return Link;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "AdsData{" +
                "image='" + image + '\'' +
                ", Link='" + Link + '\'' +
                ", desc='" + desc + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
