package com.wraith.auction.classes.User;

/**
 * Admin class
 */
public class Admin extends User
{
    /**
     * @return Text of greeting
     */
    public String getUser() { return String.format("Administrator, %s", getFirstName()); }
}
