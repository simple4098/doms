package com.tomasky.doms.dto.qunar;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/6
 * @version: v1.0.0
 */
public class Operator {

    private String operatorGuid;// 操作人 ID String 否 pms 操作人 ID
    private String operatorName;

    public String getOperatorGuid() {
        return operatorGuid;
    }

    public void setOperatorGuid(String operatorGuid) {
        this.operatorGuid = operatorGuid;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
