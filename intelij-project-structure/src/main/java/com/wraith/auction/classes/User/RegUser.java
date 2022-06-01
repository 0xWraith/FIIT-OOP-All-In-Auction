package com.wraith.auction.classes.User;

/**
 * RegUser class
 */
public class RegUser extends User
{
    /**
     * Type of account
     * 0 - Seller
     * 1 - Bidder
     * 2 - Admin
     */
    private int accountType;

    /**
     * @return Get account type
     */
    public int getAccountType() { return accountType; }

    /**
     * @param accountType Account type
     */
    public void setAccountType(int accountType) { this.accountType = accountType; }


    /**
     * @return Null - not used for this type of account
     */
    public String getUser() { return null; }
}