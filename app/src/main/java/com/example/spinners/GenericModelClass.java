package com.example.spinners;

public class GenericModelClass {
    public String applicantId;
    public String parameter;
    public String values;

    /*public GenericModelClass(String applicantID, String parameter, String values) {
        this.applicantID = applicantID;
        this.parameter = parameter;
        this.values = values;
    }*/

    public String getApplicantID() {
        return applicantId;
    }

    public void setApplicantID(String applicantID) {
        this.applicantId = applicantID;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
