package com.wraith.auction.exceptions;

/**
 * Database exception controller
 */
public class DataBaseException extends Exception
{
    /**
     * Create exception
     * @param message Exception message
     */
    public DataBaseException(String message)
    {
        super(message);
    }
}
