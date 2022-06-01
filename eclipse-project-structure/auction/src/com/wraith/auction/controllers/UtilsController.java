package com.wraith.auction.controllers;

import java.io.*;

/**
 * Class for different utils
 */
public class UtilsController
{
    /**
     * @param object Object to serialize
     * @param name Name of file
     * @throws IOException Error of serialization
     */
    public static void serializeObject(Object object, String name) throws IOException
    {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(String.format("%s.class", name)));
        objectOutputStream.writeObject(object);
    }

    /**
     * @param name Name of file
     * @return Deserialized object
     * @throws IOException Error of deserialization
     * @throws ClassNotFoundException Error of reading class
     */
    public static Object deserializeObject(String name) throws IOException, ClassNotFoundException
    {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("%s.class", name)));
        return objectInputStream.readObject();
    }
}
