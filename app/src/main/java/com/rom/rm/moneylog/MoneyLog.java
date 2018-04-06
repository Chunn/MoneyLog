package com.rom.rm.moneylog;

import java.util.Date;

/**
 * Created by Rơm on 4/6/2018.
 */

public class MoneyLog {
    private int id;
    private String content;
    private int amount;
    private String note;
    private Date date;
    private int mType;

    public MoneyLog(int id, String content, int amount, String note, Object date, int mType) {
    }

    public MoneyLog(int id, String content, int amount, String note, Date date, int mType) {
        this.id = id;
        this.content = content;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.mType = mType;
    }

    public MoneyLog(String content, int amount, String note, Date date, int mType) {
        this.content = content;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.mType = mType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "MoneyLog{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
