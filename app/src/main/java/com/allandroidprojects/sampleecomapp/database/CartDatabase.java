package com.allandroidprojects.sampleecomapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "cartlist_table")
public class CartDatabase implements Serializable {

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "item_name")
  private String stringImageUri;
  private String itemName;
  private String itemDescription;
  private String itemPrice;
  private String itemDeliveryDetails;
  private String itemQuantity;
  private String itemSize;

    public CartDatabase(@NonNull String stringImageUri, String itemName, String itemDescription, String itemPrice, String itemDeliveryDetails, String itemQuantity, String itemSize) {
        this.stringImageUri = stringImageUri;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemDeliveryDetails = itemDeliveryDetails;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
    }

    public String getStringImageUri() {
        return stringImageUri;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemDeliveryDetails() {
        return itemDeliveryDetails;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemSize() {
        return itemSize;

    }

    public void setStringImageUri(String stringImageUri) {
        this.stringImageUri = stringImageUri;
    }

    public void setItemName(@NonNull String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDeliveryDetails(String itemDeliveryDetails) {
        this.itemDeliveryDetails = itemDeliveryDetails;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    @NonNull
    @Override
    public String toString() {
        return "CartDatabase{" +
                "string_image_uri=" + stringImageUri +
                ", item_name='" + itemName + '\'' +
                ", title_description='" + itemDescription + '\'' +
                ", item_price=" + itemPrice +
                ", item_delivery_detail=" + itemDeliveryDetails +
                ", item_quantity=" + itemQuantity +
                ", item_size=" + itemSize +
                '}';
    }
}
