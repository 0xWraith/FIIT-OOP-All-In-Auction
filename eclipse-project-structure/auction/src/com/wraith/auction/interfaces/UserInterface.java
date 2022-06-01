package com.wraith.auction.interfaces;

/**
 * User interface
 */
public interface UserInterface
{
    /**
     * User default method
     * @return Greeting message
     */
    default String getUser() { return "Hello, User"; }
}
