package com.lira_turkish.dollarstocks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("TRY")
    @Expose
    private Double _try;
    @SerializedName("EUR")
    @Expose
    private Double eur;
    @SerializedName("GBP")
    @Expose
    private Double gbp;
    @SerializedName("BTC")
    @Expose
    private Double btc;
    @SerializedName("AUD")
    @Expose
    private Double aud;
    @SerializedName("SAR")
    @Expose
    private Double sar;
    @SerializedName("AED")
    @Expose
    private Double aed;
    @SerializedName("KWD")
    @Expose
    private Double kwd;
    @SerializedName("JOD")
    @Expose
    private Double jod;
    @SerializedName("QAR")
    @Expose
    private Double qar;
    @SerializedName("BHD")
    @Expose
    private Double bhd;
    @SerializedName("CAD")
    @Expose
    private Double cad;
    @SerializedName("RUB")
    @Expose
    private Double rub;
    @SerializedName("JPY")
    @Expose
    private Double jpy;
    @SerializedName("CNH")
    @Expose
    private Double cnh;
    @SerializedName("DZD")
    @Expose
    private Double dzd;
    @SerializedName("ARS")
    @Expose
    private Double ars;
    @SerializedName("CHF")
    @Expose
    private Double chf;
    @SerializedName("INR")
    @Expose
    private Double inr;
    @SerializedName("MAD")
    @Expose
    private Double mad;
    @SerializedName("IRR")
    @Expose
    private Integer irr;
    @SerializedName("LYD")
    @Expose
    private Double lyd;
    @SerializedName("EGP")
    @Expose
    private Double egp;
    @SerializedName("TND")
    @Expose
    private Double tnd;
    private final static long serialVersionUID = -9069774998481308573L;

    public Double getTry() {
        return _try;
    }

    public void setTry(Double _try) {
        this._try = _try;
    }

    public Double getEur() {
        return eur;
    }

    public void setEur(Double eur) {
        this.eur = eur;
    }

    public Double getGbp() {
        return gbp;
    }

    public void setGbp(Double gbp) {
        this.gbp = gbp;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getAud() {
        return aud;
    }

    public void setAud(Double aud) {
        this.aud = aud;
    }

    public Double getSar() {
        return sar;
    }

    public void setSar(Double sar) {
        this.sar = sar;
    }

    public Double getAed() {
        return aed;
    }

    public void setAed(Double aed) {
        this.aed = aed;
    }

    public Double getKwd() {
        return kwd;
    }

    public void setKwd(Double kwd) {
        this.kwd = kwd;
    }

    public Double getJod() {
        return jod;
    }

    public void setJod(Double jod) {
        this.jod = jod;
    }

    public Double getQar() {
        return qar;
    }

    public void setQar(Double qar) {
        this.qar = qar;
    }

    public Double getBhd() {
        return bhd;
    }

    public void setBhd(Double bhd) {
        this.bhd = bhd;
    }

    public Double getCad() {
        return cad;
    }

    public void setCad(Double cad) {
        this.cad = cad;
    }

    public Double getRub() {
        return rub;
    }

    public void setRub(Double rub) {
        this.rub = rub;
    }

    public Double getJpy() {
        return jpy;
    }

    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }

    public Double getCnh() {
        return cnh;
    }

    public void setCnh(Double cnh) {
        this.cnh = cnh;
    }

    public Double getDzd() {
        return dzd;
    }

    public void setDzd(Double dzd) {
        this.dzd = dzd;
    }

    public Double getArs() {
        return ars;
    }

    public void setArs(Double ars) {
        this.ars = ars;
    }

    public Double getChf() {
        return chf;
    }

    public void setChf(Double chf) {
        this.chf = chf;
    }

    public Double getInr() {
        return inr;
    }

    public void setInr(Double inr) {
        this.inr = inr;
    }

    public Double getMad() {
        return mad;
    }

    public void setMad(Double mad) {
        this.mad = mad;
    }

    public Integer getIrr() {
        return irr;
    }

    public void setIrr(Integer irr) {
        this.irr = irr;
    }

    public Double getLyd() {
        return lyd;
    }

    public void setLyd(Double lyd) {
        this.lyd = lyd;
    }

    public Double getEgp() {
        return egp;
    }

    public void setEgp(Double egp) {
        this.egp = egp;
    }

    public Double getTnd() {
        return tnd;
    }

    public void setTnd(Double tnd) {
        this.tnd = tnd;
}
    //tostring

    @Override
    public String toString() {
        return "Currency{" +
                "_try=" + _try +
                ", eur=" + eur +
                ", gbp=" + gbp +
                ", btc=" + btc +
                ", aud=" + aud +
                ", sar=" + sar +
                ", aed=" + aed +
                ", kwd=" + kwd +
                ", jod=" + jod +
                ", qar=" + qar +
                ", bhd=" + bhd +
                ", cad=" + cad +
                ", rub=" + rub +
                ", jpy=" + jpy +
                ", cnh=" + cnh +
                ", dzd=" + dzd +
                ", ars=" + ars +
                ", chf=" + chf +
                ", inr=" + inr +
                ", mad=" + mad +
                ", irr=" + irr +
                ", lyd=" + lyd +
                ", egp=" + egp +
                ", tnd=" + tnd +
                '}';
    }
}