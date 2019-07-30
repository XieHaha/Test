package com.keydom.ih_doctor.m_interface;
import com.keydom.ih_doctor.bean.HospitalAreaInfo;

public class GeneralCallback {

    public interface hospitalAreaAdapterCallBack{
        void commitInfo(Object object);
    }

    public interface dateAdapterCallBack{
        void getSelectedDate(int position);
    }

    public interface ExaReportActivityListener{
        void refreshSelectedCard(String cardNum);
    }
    public interface SelectHospitalListener{
        void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo);
    }

}
