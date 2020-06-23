package com.example.mednowadmin;

class AdminInfo {
    private String phone, password, adminID;

    public AdminInfo(){};

    public AdminInfo(String phone, String password, String adminID) {
        this.phone = phone;
        this.password = password;
        this.adminID = adminID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
}
