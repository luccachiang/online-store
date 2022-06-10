package com.cy.store.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class OrderVO implements Serializable {
    private Integer oid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private String image;
    private String recvName;
    private Date orderTime;


    public Integer getCid() {
        return oid;
    }

    public void setCid(Integer cid) {
        this.oid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderVO orderVO = (OrderVO) o;
        return Objects.equals(oid, orderVO.oid) && Objects.equals(uid, orderVO.uid) && Objects.equals(pid, orderVO.pid) && Objects.equals(price, orderVO.price) && Objects.equals(num, orderVO.num) && Objects.equals(title, orderVO.title) && Objects.equals(image, orderVO.image) && Objects.equals(recvName, orderVO.recvName) && Objects.equals(orderTime, orderVO.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid, uid, pid, price, num, title, image, recvName, orderTime);
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", recvName='" + recvName + '\'' +
                ", orderTime=" + orderTime +
                '}';
    }
}
