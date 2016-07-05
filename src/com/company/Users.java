package com.company;

/**
 * Created by Виктор on 04.07.2016.
 */
public class Users {
    private String login;
    private String email;
    private int badge1;
    private int badge2;
    private int badge3;
    private int badge4;
    private int badge5;

    public Users(String login, String email, int badge1, int badge2, int badge3, int badge4, int badge5) {
        this.login = login;
        this.email = email;
        this.badge1 = badge1;
        this.badge2 = badge2;
        this.badge3 = badge3;
        this.badge4 = badge4;
        this.badge5 = badge5;
    }

    public void incBadge1() {
        this.badge1++;
    }

    public void incBadge2() {
        this.badge2++;
    }

    public void incBadge3() {
        this.badge3++;
    }

    public void incBadge4() {
        this.badge4++;
    }

    public void incBadge5() {
        this.badge5++;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public int getBadge1() {
        return badge1;
    }

    public int getBadge2() {
        return badge2;
    }

    public int getBadge3() {
        return badge3;
    }

    public int getBadge4() {
        return badge4;
    }

    public int getBadge5() {
        return badge5;
    }
}
