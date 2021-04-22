package com.allandroidprojects.sampleecomapp.miscellaneous;

public class ModelServiceMan {

    String name;
    String address_text;

    public ModelServiceMan() {
    }

    public ModelServiceMan(String name, String address_text) {
        this.name = name;
        this.address_text = address_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }
}
