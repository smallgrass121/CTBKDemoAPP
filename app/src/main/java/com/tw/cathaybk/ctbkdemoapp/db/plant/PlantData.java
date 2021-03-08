package com.tw.cathaybk.ctbkdemoapp.db.plant;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plantdata")
public class PlantData {
    @PrimaryKey
    @NonNull
    private String F_Name_Ch;

    private String F_Summary;
    private String F_Keywords;
    private String F_AlsoKnown;
    private String F_Geo;
    private String F_Location;
    private String F_Name_En;
    private String F_Name_Latin;
    private String F_Family;
    private String F_Genus;
    private String F_Brief;
    private String F_Feature;
    private String F_FunctionAndApplication;
    private String F_Code;
    private String F_Pic01_ALT;
    private String F_Pic01_URL;
    private String F_Pic02_ALT;
    private String F_Pic02_URL;
    private String F_Pic03_ALT;
    private String F_Pic03_URL;
    private String F_Pic04_ALT;
    private String F_Pic04_URL;
    private String F_pdf01_ALT;
    private String F_pdf01_URL;
    private String F_pdf02_ALT;
    private String F_pdf02_URL;
    private String F_Voice01_ALT;
    private String F_Voice01_URL;
    private String F_Voice02_ALT;
    private String F_Voice02_URL;
    private String F_Voice03_ALT;
    private String F_Voice03_URL;
    private String F_Vedio_URL;
    private String F_Update;
    private String F_CID;

    private String image;

    @NonNull
    public String getF_Name_Ch() {
        return F_Name_Ch;
    }

    public void setF_Name_Ch(@NonNull String f_Name_Ch) {
        F_Name_Ch = f_Name_Ch;
    }

    public String getF_Summary() {
        return F_Summary;
    }

    public void setF_Summary(String f_Summary) {
        F_Summary = f_Summary;
    }

    public String getF_Keywords() {
        return F_Keywords;
    }

    public void setF_Keywords(String f_Keywords) {
        F_Keywords = f_Keywords;
    }

    public String getF_AlsoKnown() {
        return F_AlsoKnown;
    }

    public void setF_AlsoKnown(String f_AlsoKnown) {
        F_AlsoKnown = f_AlsoKnown;
    }

    public String getF_Geo() {
        return F_Geo;
    }

    public void setF_Geo(String f_Geo) {
        F_Geo = f_Geo;
    }

    public String getF_Location() {
        return F_Location;
    }

    public void setF_Location(String f_Location) {
        F_Location = f_Location;
    }

    public String getF_Name_En() {
        return F_Name_En;
    }

    public void setF_Name_En(String f_Name_En) {
        F_Name_En = f_Name_En;
    }

    public String getF_Name_Latin() {
        return F_Name_Latin;
    }

    public void setF_Name_Latin(String f_Name_Latin) {
        F_Name_Latin = f_Name_Latin;
    }

    public String getF_Family() {
        return F_Family;
    }

    public void setF_Family(String f_Family) {
        F_Family = f_Family;
    }

    public String getF_Genus() {
        return F_Genus;
    }

    public void setF_Genus(String f_Genus) {
        F_Genus = f_Genus;
    }

    public String getF_Brief() {
        return F_Brief;
    }

    public void setF_Brief(String f_Brief) {
        F_Brief = f_Brief;
    }

    public String getF_Feature() {
        return F_Feature;
    }

    public void setF_Feature(String f_Feature) {
        F_Feature = f_Feature;
    }

    public String getF_FunctionAndApplication() {
        return F_FunctionAndApplication;
    }

    public void setF_FunctionAndApplication(String f_FunctionAndApplication) {
        F_FunctionAndApplication = f_FunctionAndApplication;
    }

    public String getF_Code() {
        return F_Code;
    }

    public void setF_Code(String f_Code) {
        F_Code = f_Code;
    }

    public String getF_Pic01_ALT() {
        return F_Pic01_ALT;
    }

    public void setF_Pic01_ALT(String f_Pic01_ALT) {
        F_Pic01_ALT = f_Pic01_ALT;
    }

    public String getF_Pic01_URL() {
        return F_Pic01_URL;
    }

    public void setF_Pic01_URL(String f_Pic01_URL) {
        F_Pic01_URL = f_Pic01_URL;
    }

    public String getF_Pic02_ALT() {
        return F_Pic02_ALT;
    }

    public void setF_Pic02_ALT(String f_Pic02_ALT) {
        F_Pic02_ALT = f_Pic02_ALT;
    }

    public String getF_Pic02_URL() {
        return F_Pic02_URL;
    }

    public void setF_Pic02_URL(String f_Pic02_URL) {
        F_Pic02_URL = f_Pic02_URL;
    }

    public String getF_Pic03_ALT() {
        return F_Pic03_ALT;
    }

    public void setF_Pic03_ALT(String f_Pic03_ALT) {
        F_Pic03_ALT = f_Pic03_ALT;
    }

    public String getF_Pic03_URL() {
        return F_Pic03_URL;
    }

    public void setF_Pic03_URL(String f_Pic03_URL) {
        F_Pic03_URL = f_Pic03_URL;
    }

    public String getF_Pic04_ALT() {
        return F_Pic04_ALT;
    }

    public void setF_Pic04_ALT(String f_Pic04_ALT) {
        F_Pic04_ALT = f_Pic04_ALT;
    }

    public String getF_Pic04_URL() {
        return F_Pic04_URL;
    }

    public void setF_Pic04_URL(String f_Pic04_URL) {
        F_Pic04_URL = f_Pic04_URL;
    }

    public String getF_pdf01_ALT() {
        return F_pdf01_ALT;
    }

    public void setF_pdf01_ALT(String f_pdf01_ALT) {
        F_pdf01_ALT = f_pdf01_ALT;
    }

    public String getF_pdf01_URL() {
        return F_pdf01_URL;
    }

    public void setF_pdf01_URL(String f_pdf01_URL) {
        F_pdf01_URL = f_pdf01_URL;
    }

    public String getF_pdf02_ALT() {
        return F_pdf02_ALT;
    }

    public void setF_pdf02_ALT(String f_pdf02_ALT) {
        F_pdf02_ALT = f_pdf02_ALT;
    }

    public String getF_pdf02_URL() {
        return F_pdf02_URL;
    }

    public void setF_pdf02_URL(String f_pdf02_URL) {
        F_pdf02_URL = f_pdf02_URL;
    }

    public String getF_Voice01_ALT() {
        return F_Voice01_ALT;
    }

    public void setF_Voice01_ALT(String f_Voice01_ALT) {
        F_Voice01_ALT = f_Voice01_ALT;
    }

    public String getF_Voice01_URL() {
        return F_Voice01_URL;
    }

    public void setF_Voice01_URL(String f_Voice01_URL) {
        F_Voice01_URL = f_Voice01_URL;
    }

    public String getF_Voice02_ALT() {
        return F_Voice02_ALT;
    }

    public void setF_Voice02_ALT(String f_Voice02_ALT) {
        F_Voice02_ALT = f_Voice02_ALT;
    }

    public String getF_Voice02_URL() {
        return F_Voice02_URL;
    }

    public void setF_Voice02_URL(String f_Voice02_URL) {
        F_Voice02_URL = f_Voice02_URL;
    }

    public String getF_Voice03_ALT() {
        return F_Voice03_ALT;
    }

    public void setF_Voice03_ALT(String f_Voice03_ALT) {
        F_Voice03_ALT = f_Voice03_ALT;
    }

    public String getF_Voice03_URL() {
        return F_Voice03_URL;
    }

    public void setF_Voice03_URL(String f_Voice03_URL) {
        F_Voice03_URL = f_Voice03_URL;
    }

    public String getF_Vedio_URL() {
        return F_Vedio_URL;
    }

    public void setF_Vedio_URL(String f_Vedio_URL) {
        F_Vedio_URL = f_Vedio_URL;
    }

    public String getF_Update() {
        return F_Update;
    }

    public void setF_Update(String f_Update) {
        F_Update = f_Update;
    }

    public String getF_CID() {
        return F_CID;
    }

    public void setF_CID(String f_CID) {
        F_CID = f_CID;
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

        return "PlantData{" +
                "F_Name_Ch='" + F_Name_Ch + '\'' +
                ", F_Summary='" + F_Summary + '\'' +
                ", F_Keywords='" + F_Keywords + '\'' +
                ", F_AlsoKnown='" + F_AlsoKnown + '\'' +
                ", F_Geo='" + F_Geo + '\'' +
                ", F_Location='" + F_Location + '\'' +
                ", F_Name_En='" + F_Name_En + '\'' +
                ", F_Name_Latin='" + F_Name_Latin + '\'' +
                ", F_Family='" + F_Family + '\'' +
                ", F_Genus='" + F_Genus + '\'' +
                ", F_Brief='" + F_Brief + '\'' +
                ", F_Feature='" + F_Feature + '\'' +
                ", F_FunctionAndApplication='" + F_FunctionAndApplication + '\'' +
                ", F_Code='" + F_Code + '\'' +
                ", F_Pic01_ALT='" + F_Pic01_ALT + '\'' +
                ", F_Pic01_URL='" + F_Pic01_URL + '\'' +
                ", F_Pic02_ALT='" + F_Pic02_ALT + '\'' +
                ", F_Pic02_URL='" + F_Pic02_URL + '\'' +
                ", F_Pic03_ALT='" + F_Pic03_ALT + '\'' +
                ", F_Pic03_URL='" + F_Pic03_URL + '\'' +
                ", F_Pic04_ALT='" + F_Pic04_ALT + '\'' +
                ", F_Pic04_URL='" + F_Pic04_URL + '\'' +
                ", F_pdf01_ALT='" + F_pdf01_ALT + '\'' +
                ", F_pdf01_URL='" + F_pdf01_URL + '\'' +
                ", F_pdf02_ALT='" + F_pdf02_ALT + '\'' +
                ", F_pdf02_URL='" + F_pdf02_URL + '\'' +
                ", F_Voice01_ALT='" + F_Voice01_ALT + '\'' +
                ", F_Voice01_URL='" + F_Voice01_URL + '\'' +
                ", F_Voice02_ALT='" + F_Voice02_ALT + '\'' +
                ", F_Voice02_URL='" + F_Voice02_URL + '\'' +
                ", F_Voice03_ALT='" + F_Voice03_ALT + '\'' +
                ", F_Voice03_URL='" + F_Voice03_URL + '\'' +
                ", F_Vedio_URL='" + F_Vedio_URL + '\'' +
                ", F_Update='" + F_Update + '\'' +
                ", F_CID='" + F_CID + '\'' +
                imageData +
                '}';
    }
}
