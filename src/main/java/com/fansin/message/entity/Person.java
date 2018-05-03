package com.fansin.message.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * ä¸ªäººä¿¡æ¯å››è¦ç´ 
 * </p>
 *
 * @author zhaofeng
 * @since 2018 -05-02
 */
@TableName("Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String idCard;
    private String bankCard;
    private String mobilePhone;
    private Integer age;
    private String home;
    private String office;
    private Date createDatetime;
    private String remark;


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets id card.
     *
     * @return the id card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Sets id card.
     *
     * @param idCard the id card
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * Gets bank card.
     *
     * @return the bank card
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * Sets bank card.
     *
     * @param bankCard the bank card
     */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    /**
     * Gets mobile phone.
     *
     * @return the mobile phone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets mobile phone.
     *
     * @param mobilePhone the mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets home.
     *
     * @return the home
     */
    public String getHome() {
        return home;
    }

    /**
     * Sets home.
     *
     * @param home the home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Gets office.
     *
     * @return the office
     */
    public String getOffice() {
        return office;
    }

    /**
     * Sets office.
     *
     * @param office the office
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     * Gets create datetime.
     *
     * @return the create datetime
     */
    public Date getCreateDatetime() {
        return createDatetime;
    }

    /**
     * Sets create datetime.
     *
     * @param createDatetime the create datetime
     */
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * Gets remark.
     *
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets remark.
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", id=" + id +
                ", name=" + name +
                ", idCard=" + idCard +
                ", bankCard=" + bankCard +
                ", mobilePhone=" + mobilePhone +
                ", age=" + age +
                ", home=" + home +
                ", office=" + office +
                ", createDatetime=" + createDatetime +
                ", remark=" + remark +
                "}";
    }
}
