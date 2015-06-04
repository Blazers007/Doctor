package com.blazers.app.doctor.BmobModel;

import cn.bmob.v3.BmobObject;

/**
 * Created by Blazers on 15/6/4.
 */
public class Doctor extends BmobObject {

    private String doctorId;
    private String doctorName;
    private String workHospital;
    private String department;
    private String description;
    private String workYears;

    /* 最好用约定好的数字来标记专业 便于搜索  */
    private String major;
    private String majorDescription;

    /* 关联病人表表示治疗关系 */
}
