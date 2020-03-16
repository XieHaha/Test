package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 城市实体
 */
public class CityBean {

    @JSONField(name = "A")
    private List<itemBean> A;
    @JSONField(name = "B")
    private List<itemBean> B;
    @JSONField(name = "C")
    private List<itemBean> C;
    @JSONField(name = "D")
    private List<itemBean> D;
    @JSONField(name = "E")
    private List<itemBean> E;
    @JSONField(name = "F")
    private List<itemBean> F;
    @JSONField(name = "G")
    private List<itemBean> G;
    @JSONField(name = "H")
    private List<itemBean> H;
    @JSONField(name = "J")
    private List<itemBean> J;
    @JSONField(name = "K")
    private List<itemBean> K;
    @JSONField(name = "L")
    private List<itemBean> L;
    @JSONField(name = "M")
    private List<itemBean> M;
    @JSONField(name = "N")
    private List<itemBean> N;
    @JSONField(name = "P")
    private List<itemBean> P;
    @JSONField(name = "Q")
    private List<itemBean> Q;
    @JSONField(name = "R")
    private List<itemBean> R;
    @JSONField(name = "S")
    private List<itemBean> S;
    @JSONField(name = "T")
    private List<itemBean> T;
    @JSONField(name = "W")
    private List<itemBean> W;
    @JSONField(name = "X")
    private List<itemBean> X;
    @JSONField(name = "Y")
    private List<itemBean> Y;
    @JSONField(name = "Z")
    private List<itemBean> Z;

    public List<itemBean> getA() {
        return A;
    }

    public void setA(List<itemBean> A) {
        this.A = A;
    }

    public List<itemBean> getB() {
        return B;
    }

    public void setB(List<itemBean> B) {
        this.B = B;
    }

    public List<itemBean> getC() {
        return C;
    }

    public void setC(List<itemBean> C) {
        this.C = C;
    }

    public List<itemBean> getD() {
        return D;
    }

    public void setD(List<itemBean> D) {
        this.D = D;
    }

    public List<itemBean> getE() {
        return E;
    }

    public void setE(List<itemBean> E) {
        this.E = E;
    }

    public List<itemBean> getF() {
        return F;
    }

    public void setF(List<itemBean> F) {
        this.F = F;
    }

    public List<itemBean> getG() {
        return G;
    }

    public void setG(List<itemBean> G) {
        this.G = G;
    }

    public List<itemBean> getH() {
        return H;
    }

    public void setH(List<itemBean> H) {
        this.H = H;
    }

    public List<itemBean> getJ() {
        return J;
    }

    public void setJ(List<itemBean> J) {
        this.J = J;
    }

    public List<itemBean> getK() {
        return K;
    }

    public void setK(List<itemBean> K) {
        this.K = K;
    }

    public List<itemBean> getL() {
        return L;
    }

    public void setL(List<itemBean> L) {
        this.L = L;
    }

    public List<itemBean> getM() {
        return M;
    }

    public void setM(List<itemBean> M) {
        this.M = M;
    }

    public List<itemBean> getN() {
        return N;
    }

    public void setN(List<itemBean> N) {
        this.N = N;
    }

    public List<itemBean> getP() {
        return P;
    }

    public void setP(List<itemBean> P) {
        this.P = P;
    }

    public List<itemBean> getQ() {
        return Q;
    }

    public void setQ(List<itemBean> Q) {
        this.Q = Q;
    }

    public List<itemBean> getR() {
        return R;
    }

    public void setR(List<itemBean> R) {
        this.R = R;
    }

    public List<itemBean> getS() {
        return S;
    }

    public void setS(List<itemBean> S) {
        this.S = S;
    }

    public List<itemBean> getT() {
        return T;
    }

    public void setT(List<itemBean> T) {
        this.T = T;
    }

    public List<itemBean> getW() {
        return W;
    }

    public void setW(List<itemBean> W) {
        this.W = W;
    }

    public List<itemBean> getX() {
        return X;
    }

    public void setX(List<itemBean> X) {
        this.X = X;
    }

    public List<itemBean> getY() {
        return Y;
    }

    public void setY(List<itemBean> Y) {
        this.Y = Y;
    }

    public List<itemBean> getZ() {
        return Z;
    }

    public void setZ(List<itemBean> Z) {
        this.Z = Z;
    }

    public static class itemBean {
        /**
         * id : 258
         * priority : null
         * code : 513200
         * name : 阿坝藏族羌族自治州
         * pinyin : abazangzuqiangzu
         * abbreviation :
         * remark : null
         * provinceId : 23
         * area : null
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "priority")
        private Object priority;
        @JSONField(name = "code")
        private String code;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "pinyin")
        private String pinyin;
        @JSONField(name = "abbreviation")
        private String abbreviation;
        @JSONField(name = "remark")
        private Object remark;
        @JSONField(name = "provinceId")
        private int provinceId;
        @JSONField(name = "area")
        private Object area;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPriority() {
            return priority;
        }

        public void setPriority(Object priority) {
            this.priority = priority;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }
    }


}
