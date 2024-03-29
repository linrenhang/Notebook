package com.example.notebook;

import org.litepal.crud.LitePalSupport;

public class MainNote extends LitePalSupport {
    private int id;//int为21亿
    private int position;//在笔记中的位置
    private boolean show;
    private String text=null;
    private String extendText=null;
    private int belongId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getExtendText() {
        return extendText;
    }

    public void setExtendText(String extendText) {
        this.extendText = extendText;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }
}
