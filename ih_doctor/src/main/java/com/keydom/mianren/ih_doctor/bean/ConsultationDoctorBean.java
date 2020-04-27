package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/4/27 11:37
 * @des 会诊医生
 */
public class ConsultationDoctorBean implements Serializable {
    private static final long serialVersionUID = 3753829925774047461L;
    private String id;
    private String name;
    private String doctorImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }
}
