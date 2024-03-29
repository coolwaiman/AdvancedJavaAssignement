package com.advance.java.server.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rAYMOND on 4/22/2016.
 */
@Entity
public class Orderline implements Serializable, Comparable<Orderline> {
    private Cusorder cusOrder;
    private Storeproduct storeProduct;
    private double productPrice;

    @ManyToOne
    @JoinColumn(name = "OrderId", nullable = false)
    public Cusorder getCusOrder() {
        return cusOrder;
    }

    public void setCusOrder(Cusorder orderId) {
        this.cusOrder = orderId;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "ProductSn", referencedColumnName = "ProductSn", nullable = false)
    public Storeproduct getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(Storeproduct productSn) {
        this.storeProduct = productSn;
    }

    @Basic
    @Column(name = "ProductPrice", nullable = false, precision = 0)
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orderline orderline = (Orderline) o;

        if (cusOrder != orderline.cusOrder) return false;
        if (storeProduct != orderline.storeProduct) return false;
        if (Double.compare(orderline.productPrice, productPrice) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cusOrder.hashCode();
        result = 31 * result + storeProduct.hashCode();
        temp = Double.doubleToLongBits(productPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Orderline o) {
        if(this.getCusOrder().compareTo(o.getCusOrder()) == 0) {
            return this.getCusOrder().compareTo(o.getCusOrder());
        } else {
            return this.getCusOrder().compareTo(o.getCusOrder());
        }
    }
}
