package com.example.prog3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class parseToByteArray {
    public byte[] methodToConvert(File file)
            throws IOException
    {
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }
}
