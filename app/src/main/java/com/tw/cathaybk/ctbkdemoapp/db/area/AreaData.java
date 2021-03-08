package com.tw.cathaybk.ctbkdemoapp.db.area;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "areadata")
public class AreaData {
    @PrimaryKey
    @NonNull
    private String E_no;

    private String E_Category;
    private String E_Name;
    private String E_Pic_URL;
    private String E_Info;
    private String E_Memo;
    private String E_Geo;
    private String E_URL;

    private String image;

    public String getE_no() {
        return E_no;
    }

    public void setE_no(String e_no) {
        E_no = e_no;
    }

    public String getE_Category() {
        return E_Category;
    }

    public void setE_Category(String e_Category) {
        E_Category = e_Category;
    }

    public String getE_Name() {
        return E_Name;
    }

    public void setE_Name(String e_Name) {
        E_Name = e_Name;
    }

    public String getE_Pic_URL() {
        return E_Pic_URL;
    }

    public void setE_Pic_URL(String e_Pic_URL) {
        E_Pic_URL = e_Pic_URL;
    }

    public String getE_Info() {
        return E_Info;
    }

    public void setE_Info(String e_Info) {
        E_Info = e_Info;
    }

    public String getE_Memo() {
        return E_Memo;
    }

    public void setE_Memo(String e_Memo) {
        E_Memo = e_Memo;
    }

    public String getE_Geo() {
        return E_Geo;
    }

    public void setE_Geo(String e_Geo) {
        E_Geo = e_Geo;
    }

    public String getE_URL() {
        return E_URL;
    }

    public void setE_URL(String e_URL) {
        E_URL = e_URL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString(){

        String imageData = "";
        if(null != image){
            imageData = ", image='" + image + '\'' ;
        }

        return "AreaData{" +
                "E_no='" + E_no + '\'' +
                ", E_Category='" + E_Category + '\'' +
                ", E_Name='" + E_Name + '\'' +
                ", E_Pic_URL='" + E_Pic_URL + '\'' +
                ", E_Info='" + E_Info + '\'' +
                ", E_Memo='" + E_Memo + '\'' +
                ", E_Geo='" + E_Geo + '\'' +
                ", E_URL='" + E_URL + '\'' +
                imageData +
                '}';
    }
}
