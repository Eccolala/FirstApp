package com.just.firstapp;


public class Bean {
    private Integer pic;
    private String name;
    private String num;
    private String quality;
    private String evaluate;

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }


    public Integer getSrc() {
        return pic;
    }

    public void setSrc(Integer src) {
        this.pic = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
