package com.lira_turkish.dollarstocks.models;

import java.util.ArrayList;

public class Response {
    ApiResult<ArrayList<CurrencyData>> currencyDataTypeOne;
    ApiResult<ArrayList<CurrencyData>> currencyDataTypeFour;
    ApiResult<ArrayList<CurrencyData>> currencyDataTypeFive;
    ApiResult<ArrayList<CurrencyData>> currencyDataTypeSix;
    ApiResult<ArrayList<History>> history;
    ArrayList<CurrentCurrency> worldCurrency;
    ApiResult<ArrayList<Screen>> screen;
    ArrayList<Day> days;

    public ApiResult<ArrayList<CurrencyData>> getCurrencyDataTypeOne() {
        return currencyDataTypeOne;
    }

    public void setCurrencyDataTypeOne(ApiResult<ArrayList<CurrencyData>> currencyDataTypeOne) {
        this.currencyDataTypeOne = currencyDataTypeOne;
    }

    public ApiResult<ArrayList<CurrencyData>> getCurrencyDataTypeFour() {
        return currencyDataTypeFour;
    }

    public void setCurrencyDataTypeFour(ApiResult<ArrayList<CurrencyData>> currencyDataTypeFour) {
        this.currencyDataTypeFour = currencyDataTypeFour;
    }

    public ApiResult<ArrayList<CurrencyData>> getCurrencyDataTypeFive() {
        return currencyDataTypeFive;
    }

    public void setCurrencyDataTypeFive(ApiResult<ArrayList<CurrencyData>> currencyDataTypeFive) {
        this.currencyDataTypeFive = currencyDataTypeFive;
    }

    public ApiResult<ArrayList<CurrencyData>> getCurrencyDataTypeSix() {
        return currencyDataTypeSix;
    }

    public void setCurrencyDataTypeSix(ApiResult<ArrayList<CurrencyData>> currencyDataTypeSix) {
        this.currencyDataTypeSix = currencyDataTypeSix;
    }

    public ApiResult<ArrayList<History>> getHistory() {
        return history;
    }

    public void setHistory(ApiResult<ArrayList<History>> history) {
        this.history = history;
    }

    public ArrayList<CurrentCurrency> getWorldCurrency() {
        return worldCurrency;
    }

    public void setWorldCurrency(ArrayList<CurrentCurrency> worldCurrency) {
        this.worldCurrency = worldCurrency;
    }

    public ApiResult<ArrayList<Screen>> getScreen() {
        return screen;
    }

    public void setScreen(ApiResult<ArrayList<Screen>> screen) {
        this.screen = screen;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public void updateDays(ArrayList<Day> days) {
        this.days.addAll(days);
    }


    public ArrayList<Day> getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Response{" +
                ", currencyDataTypeOne=" + currencyDataTypeOne +
                ", currencyDataTypeFour=" + currencyDataTypeFour +
                ", currencyDataTypeFive=" + currencyDataTypeFive +
                ", currencyDataTypeSix=" + currencyDataTypeSix +
                ", history=" + history +
                ", worldCurrency=" + worldCurrency +
                ", screen=" + screen +
                '}';
    }
}