package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @date 20/7/23 14:28
 * @des
 */
public class UpdateDoctorBean implements Serializable {
    private static final long serialVersionUID = 875061586558005557L;

    private String updateDoctors;

    public String getUpdateDoctors() {
        return updateDoctors;
    }

    public void setUpdateDoctors(String updateDoctors) {
        this.updateDoctors = updateDoctors;
    }
}
