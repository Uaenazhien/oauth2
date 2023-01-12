package com.bjpowernode.enums;

/**
 * 0:未上架
 * 1:上架
 */
public enum ProductStatus {
    OFF(0,"未上架"),ON(1,"上架");

    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }


    ProductStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }
}
