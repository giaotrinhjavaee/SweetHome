/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author admin
 */
public class UploadFile {

    ArrayList<OutputStream> listOutputSream = new ArrayList<OutputStream>();

    public String listFilename = "";

    public String copyFile(String fileName, InputStream in) {
        try {
            String location = SessionUtils.getRequest().getServletContext().getRealPath("/resources/upload/");
            Date date = new Date();
            String nameEn = Encrypt.md5(date.getTime() + "");
            String[] token = fileName.split("\\.");
            String extension = token[token.length - 1];
            File newFiles = new File(location, nameEn + "." + extension);

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(newFiles);
            listFilename += nameEn + "." + extensionFile(extension) + ",";
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            listOutputSream.add(out);
            System.out.println("New file created!");
            return nameEn + "." + extensionFile(extension);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }



    public void flush() throws IOException {
        for (OutputStream item : listOutputSream) {
            item.flush();
            item.close();
        }

    }

    public void close() throws IOException {
        for (OutputStream item : listOutputSream) {
            item.close();
        }
    }

    String extensionFile(String exten) {
        if (exten.toLowerCase().equals("png")) {
            return "png";
        }
        if (exten.toLowerCase().equals("jpg")) {
            return "jpg";
        }
        if (exten.toLowerCase().equals("jpeg")) {
            return "jpeg";
        } else {
            return "";
        }
    }

}
