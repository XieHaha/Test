package com.keydom.ih_patient.utils;

import com.keydom.ih_patient.bean.BodyCheckRecordInfo;
import com.keydom.ih_patient.bean.CityBean;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.ih_patient.bean.DocAndDepSearchBean;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.InspectionRecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 门诊时间工具
 */
public class DepartmentDataHelper {
    public static List<Object> getDataAfterHandle(List<HospitaldepartmentsInfo> resultList) {
        List<Object> dataList = new ArrayList<Object>();
        for (HospitaldepartmentsInfo hospitaldepartmentsInfo : resultList) {
            HeaderInfo headerInfo = new HeaderInfo();
            headerInfo.setFistDepartmentName(hospitaldepartmentsInfo.getName());
            headerInfo.setFontColor(hospitaldepartmentsInfo.getFontColor());
            headerInfo.setImage(hospitaldepartmentsInfo.getImage());
            List<HospitaldepartmentsInfo.HdListBean> hdList = hospitaldepartmentsInfo.getHdList();
            List<BodyInfo> bodyInfoList = new ArrayList<>();
            BodyInfoList bodyListinfo = new BodyInfoList();
            for (HospitaldepartmentsInfo.HdListBean hdListBean : hdList) {
                BodyInfo bodyInfo = new BodyInfo();
                bodyInfo.setSecondDepartmentName(hdListBean.getName());
                bodyInfo.setId(hdListBean.getId());
                bodyInfoList.add(bodyInfo);
            }
            bodyListinfo.setBodyInfoList(bodyInfoList);
            if (hdList.size() > 6) {
                bodyListinfo.setShowMore(true);
            } else {
                bodyListinfo.setShowMore(false);
            }
            //把所有数据按照头部、内容和尾部三个部分排序好

            dataList.add(headerInfo);
            dataList.add(bodyListinfo);
        }
        return dataList;
    }

    /**
     * 头部时间实体
     */
    public static class HeaderInfo {
        private String fistDepartmentName;
        private String fontColor;
        private String image;

        public String getFistDepartmentName() {
            return fistDepartmentName;
        }

        public void setFistDepartmentName(String fistDepartmentName) {
            this.fistDepartmentName = fistDepartmentName;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    /**
     * 内容实体列表
     */
    public static class BodyInfoList {
        private List<BodyInfo> BodyInfoList;

        private boolean isShowMore;

        public boolean isShowMore() {
            return isShowMore;
        }

        public void setShowMore(boolean showMore) {
            isShowMore = showMore;
        }

        public List<BodyInfo> getBodyInfoList() {
            return BodyInfoList;
        }

        public void setBodyInfoList(List<BodyInfo> bodyInfoList) {
            BodyInfoList = bodyInfoList;
        }
    }

    /**
     * 内容实体
     */
    public static class BodyInfo {
        private String secondDepartmentName;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSecondDepartmentName() {
            return secondDepartmentName;
        }

        public void setSecondDepartmentName(String secondDepartmentName) {
            this.secondDepartmentName = secondDepartmentName;
        }
    }

    /**
     * 添加搜索列表
     *
     * @param docAndDepSearchBean
     * @return
     */
    public static List<Object> getSearchDocDepAfterHandle(DocAndDepSearchBean docAndDepSearchBean) {
        List<Object> dataList = new ArrayList<Object>();
        if (docAndDepSearchBean.getHospitalDept() != null)
            dataList.addAll(docAndDepSearchBean.getHospitalDept());
        if (docAndDepSearchBean.getEsHospitalUser() != null)
            dataList.addAll(docAndDepSearchBean.getEsHospitalUser());
        return dataList;
    }

    /**
     * 批量添加列表
     *
     * @param doctorInfoList
     * @return
     */
    public static List<Object> getSearchDocAfterHandle(List<DepartmentSchedulingBean> doctorInfoList) {
        List<Object> dataList = new ArrayList<Object>();
        for (DepartmentSchedulingBean departmentSchedulingBean : doctorInfoList) {
            if (departmentSchedulingBean.getHUser() != null) {
                dataList.add(departmentSchedulingBean.getHUser());
            }
        }
        // dataList.addAll(doctorInfoList);
        return dataList;
    }

    /**
     * 批量处理数据
     *
     * @param inspectionRecordInfoList
     * @return
     */
    public static List<Object> getInspectionInfoAfterHandle(List<InspectionRecordInfo> inspectionRecordInfoList) {
        List<Object> dataList = new ArrayList<>();
        for (InspectionRecordInfo inspectionRecordInfo : inspectionRecordInfoList) {
            InspectionReportGroup inspectionReportGroup = new InspectionReportGroup();
            inspectionReportGroup.setData(inspectionRecordInfo.getDate());
            inspectionReportGroup.setExpand(false);
            inspectionReportGroup.setChildSize(inspectionRecordInfo.getCheckoutRecordVO().size());
            dataList.add(inspectionReportGroup);
            dataList.addAll(inspectionRecordInfo.getCheckoutRecordVO());
        }
        return dataList;
    }

    /**
     * 批量添加数据
     *
     * @param bodyCheckRecordInfoList
     * @return
     */
    public static List<Object> getBodyCheckInfoAfterHandle(List<BodyCheckRecordInfo> bodyCheckRecordInfoList) {
        List<Object> dataList = new ArrayList<>();
        for (BodyCheckRecordInfo bodyCheckRecordInfo : bodyCheckRecordInfoList) {
            InspectionReportGroup inspectionReportGroup = new InspectionReportGroup();
            inspectionReportGroup.setData(bodyCheckRecordInfo.getDate());
            inspectionReportGroup.setExpand(false);
            inspectionReportGroup.setChildSize(bodyCheckRecordInfo.getInspectRecordVOList().size());
            dataList.add(inspectionReportGroup);
            dataList.addAll(bodyCheckRecordInfo.getInspectRecordVOList());
        }
        return dataList;
    }

    /**
     * 报告实体
     */
    public static class InspectionReportGroup {
        String data;
        int childSize;
        boolean isExpand;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getChildSize() {
            return childSize;
        }

        public void setChildSize(int childSize) {
            this.childSize = childSize;
        }

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
    }

    /**
     * 添加通知
     *
     * @param notificationsBeanList
     * @return
     */
    public static List<Object> getNotificationsBeanAfterHandle(List<IndexData.NotificationsBean> notificationsBeanList) {
        List<Object> dataList = new ArrayList<Object>();
        dataList.addAll(notificationsBeanList);
        return dataList;
    }

    /**
     * 获取城市实体
     *
     * @param cityBean
     * @return
     */
    public static List<Object> getCityBeanAfterHandle(CityBean cityBean) {
        List<Object> dataList = new ArrayList<Object>();
        if (cityBean.getA() != null && cityBean.getA().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("A");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getA());
        }
        if (cityBean.getB() != null && cityBean.getB().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("B");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getB());
        }
        if (cityBean.getC() != null && cityBean.getC().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("C");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getC());
        }
        if (cityBean.getD() != null && cityBean.getD().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("D");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getD());
        }
        if (cityBean.getE() != null && cityBean.getE().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("E");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getE());
        }
        if (cityBean.getF() != null && cityBean.getF().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("F");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getF());
        }
        if (cityBean.getG() != null && cityBean.getG().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("G");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getG());
        }
        if (cityBean.getH() != null && cityBean.getH().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("H");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getH());
        }
        if (cityBean.getJ() != null && cityBean.getJ().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("J");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getJ());
        }
        if (cityBean.getK() != null && cityBean.getK().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("K");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getK());
        }
        if (cityBean.getL() != null && cityBean.getL().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("L");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getL());
        }
        if (cityBean.getM() != null && cityBean.getM().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("M");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getM());
        }
        if (cityBean.getN() != null && cityBean.getN().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("N");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getN());
        }
        if (cityBean.getP() != null && cityBean.getP().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("P");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getP());
        }
        if (cityBean.getQ() != null && cityBean.getQ().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("Q");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getQ());
        }
        if (cityBean.getR() != null && cityBean.getR().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("R");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getR());
        }
        if (cityBean.getS() != null && cityBean.getS().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("S");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getS());
        }
        if (cityBean.getT() != null && cityBean.getT().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("T");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getT());
        }
        if (cityBean.getW() != null && cityBean.getW().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("W");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getW());
        }
        if (cityBean.getX() != null && cityBean.getX().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("X");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getX());
        }
        if (cityBean.getY() != null && cityBean.getY().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("Y");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getY());
        }
        if (cityBean.getZ() != null && cityBean.getZ().size() != 0) {
            CityHeader cityHeader = new CityHeader();
            cityHeader.setHeaderName("Z");
            dataList.add(cityHeader);
            dataList.addAll(cityBean.getZ());
        }
        return dataList;
    }

    /**
     * 城市头部实体
     */
    public static class CityHeader {
        private String headerName;

        public String getHeaderName() {
            return headerName;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }
    }
}
