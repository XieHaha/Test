package com.keydom.ih_patient.callback;

import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.PackageData;

import java.util.List;

/**
 * 公用回调类
 */
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

    public interface SelectRegionListener{
        void getSelectedRegion(List<PackageData.ProvinceBean> data,int position1,int position2,int position3);
    }
    public interface SelectNationListener{
        void getSelectedNation(PackageData.NationBean nationBean);
    }

    public interface SelectCardListener{
        void getSelectedCard(MedicalCardInfo medicalCardInfo);
    }

    public interface SelectPatientListener{
        void getSelectedPatient(ManagerUserBean managerUserBean);
    }
    public interface SelectHospitalListener{
        void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo);
    }
    public interface SelectPayMentListener{
        void getSelectPayMent(String type);
    }
}
