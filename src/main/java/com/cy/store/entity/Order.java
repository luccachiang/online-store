package com.cy.store.entity;

import java.io.Serializable;
import java.util.Date;

/** 订单数据的实体类 */
public class Order extends BaseEntity implements Serializable {
    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }

    public String getRecvProvince() {
        return recvProvince;
    }

    public void setRecvProvince(String recvProvince) {
        this.recvProvince = recvProvince;
    }

    public String getRecvCity() {
        return recvCity;
    }

    public void setRecvCity(String recvCity) {
        this.recvCity = recvCity;
    }

    public String getRecvArea() {
        return recvArea;
    }

    public void setRecvArea(String recvArea) {
        this.recvArea = recvArea;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getOid() != null ? !getOid().equals(order.getOid()) : order.getOid() != null) return false;
        if (getUid() != null ? !getUid().equals(order.getUid()) : order.getUid() != null) return false;
        if (getRecvName() != null ? !getRecvName().equals(order.getRecvName()) : order.getRecvName() != null)
            return false;
        if (getRecvPhone() != null ? !getRecvPhone().equals(order.getRecvPhone()) : order.getRecvPhone() != null)
            return false;
        if (getRecvProvince() != null ? !getRecvProvince().equals(order.getRecvProvince()) : order.getRecvProvince() != null)
            return false;
        if (getRecvCity() != null ? !getRecvCity().equals(order.getRecvCity()) : order.getRecvCity() != null)
            return false;
        if (getRecvArea() != null ? !getRecvArea().equals(order.getRecvArea()) : order.getRecvArea() != null)
            return false;
        if (getRecvAddress() != null ? !getRecvAddress().equals(order.getRecvAddress()) : order.getRecvAddress() != null)
            return false;
        if (getTotalPrice() != null ? !getTotalPrice().equals(order.getTotalPrice()) : order.getTotalPrice() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(order.getStatus()) : order.getStatus() != null) return false;
        if (getOrderTime() != null ? !getOrderTime().equals(order.getOrderTime()) : order.getOrderTime() != null)
            return false;
        return getPayTime() != null ? getPayTime().equals(order.getPayTime()) : order.getPayTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getOid() != null ? getOid().hashCode() : 0;
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + (getRecvName() != null ? getRecvName().hashCode() : 0);
        result = 31 * result + (getRecvPhone() != null ? getRecvPhone().hashCode() : 0);
        result = 31 * result + (getRecvProvince() != null ? getRecvProvince().hashCode() : 0);
        result = 31 * result + (getRecvCity() != null ? getRecvCity().hashCode() : 0);
        result = 31 * result + (getRecvArea() != null ? getRecvArea().hashCode() : 0);
        result = 31 * result + (getRecvAddress() != null ? getRecvAddress().hashCode() : 0);
        result = 31 * result + (getTotalPrice() != null ? getTotalPrice().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getOrderTime() != null ? getOrderTime().hashCode() : 0);
        result = 31 * result + (getPayTime() != null ? getPayTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", recvName='" + recvName + '\'' +
                ", recvPhone='" + recvPhone + '\'' +
                ", recvProvince='" + recvProvince + '\'' +
                ", recvCity='" + recvCity + '\'' +
                ", recvArea='" + recvArea + '\'' +
                ", recvAddress='" + recvAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                "} " + super.toString();
    }
}
