package com.example.pc.evergreen.beans;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by pc on 2018/3/23.
 */

public class UserData extends BmobObject implements Serializable {

    private String account;//账户
    private String phone;//j手机号
    private String number;//编号 (IC卡号)
    private String name;//姓名
    private String photo;//照片（通过上传服务器后返回的相片地址）
    private String gender;//性别（男、女）
    private String birth; //出生年月日（格式：yyyy-MM-dd）
    private String unit;//原单位
    private String duty;//原职务
    private String treatment;//离／退休
    private String treatmentData;//离退休时间
    private String grade;//级别（副科级、正科级、副县级、正县级、副地级、正地级、副省级、正省级、副国级、正国级、其它）
    private String address;//家庭住址
    private String contact;//联系人（配偶或子女姓名）
    private String relation;//与老干部关系
    private String contactPhone;//联系人手机号
    private Integer integral;//积分
    private String permissions;//权限


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTreatmentData() {
        return treatmentData;
    }

    public void setTreatmentData(String treatmentData) {
        this.treatmentData = treatmentData;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
