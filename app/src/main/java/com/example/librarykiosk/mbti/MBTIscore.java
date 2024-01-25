package com.example.librarykiosk.mbti;

import java.io.Serializable;

public class MBTIscore implements Serializable {
    private int M;
    private int B;
    private int T;
    private int I;

    public MBTIscore() {
        // 초기화 등 필요한 작업 수행
        M = 0;
        B = 0;
        T = 0;
        I = 0;
    }

    // 각 변수에 대한 접근자 메서드
    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }

}
