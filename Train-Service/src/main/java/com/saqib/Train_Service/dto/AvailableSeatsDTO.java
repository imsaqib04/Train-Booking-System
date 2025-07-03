package com.saqib.Train_Service.dto;

public class AvailableSeatsDTO {

    private int total;        // कुल खाली
    private int class2S;
    private int classSl;
    private int class3Ac;
    private int class2Ac;
    private int class1Ac;

    /* getters & setters */

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getClass2S() {
        return class2S;
    }

    public void setClass2S(int class2S) {
        this.class2S = class2S;
    }

    public int getClassSl() {
        return classSl;
    }

    public void setClassSl(int classSl) {
        this.classSl = classSl;
    }

    public int getClass3Ac() {
        return class3Ac;
    }

    public void setClass3Ac(int class3Ac) {
        this.class3Ac = class3Ac;
    }

    public int getClass2Ac() {
        return class2Ac;
    }

    public void setClass2Ac(int class2Ac) {
        this.class2Ac = class2Ac;
    }

    public int getClass1Ac() {
        return class1Ac;
    }

    public void setClass1Ac(int class1Ac) {
        this.class1Ac = class1Ac;
    }
}
