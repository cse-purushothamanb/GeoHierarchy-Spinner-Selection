package com.example.spinners;

public class District {
    String Code;
    String LGD_DistrictCode;
    String Name;
    String MSG;

    String Error;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getLGD_DistrictCode() {
        return LGD_DistrictCode;
    }

    public void setLGD_DistrictCode(String LGD_DistrictCode) {
        this.LGD_DistrictCode = LGD_DistrictCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
