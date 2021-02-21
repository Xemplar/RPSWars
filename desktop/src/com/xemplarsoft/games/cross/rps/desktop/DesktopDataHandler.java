package com.xemplarsoft.games.cross.rps.desktop;

import com.xemplarsoft.utils.savedb.RawDataHandler;
import com.xemplarsoft.utils.testdb.Base64;
import org.lwjgl.Sys;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class DesktopDataHandler implements RawDataHandler {
    protected boolean isGlobal = false;
    protected String company, app, file;

    protected File data;
    protected String hash;

    protected List<KeyValuePair> pairs;

    public DesktopDataHandler(String company, String app, String file){
        this.pairs = new ArrayList<>();
        this.company = company;
        this.app = app;
        this.file = file;

        if(app == null) isGlobal = true;

        loadPairs();
    }

    public String read(){
        try {
            return new String(Files.readAllBytes(getFile().toPath()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void write(String data){
        try {
            Files.write(getFile().toPath(), data.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getHash(File f){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            InputStream is = new FileInputStream(f);
            byte[] data = new byte[is.available()];
            int read = is.read(data, 0, data.length);
            is.close();

            return Base64.encodeToString(md.digest(data));
        } catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    private void loadPairs(){
        System.out.println("Start Reading Pairs");
        try {
            String file = read();

            System.out.println("");

            pairs.clear();
            for(String line : file.split("\n")){
                if(line.equals("")) continue;
                System.out.println(line);

                String[] dat = line.split(":");
                if(dat.length < 2) continue;
                String key = dat[0];
                byte[] data = Base64.decode(dat[1]);

                pairs.add(new KeyValuePair(key, data));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Finished Loading Pairs");
    }

    private void savePairs(){
        try {
            StringBuilder data = new StringBuilder();
            for(KeyValuePair pair : pairs){
                data.append(pair.key).append(":").append(Base64.encodeToString(pair.value).replace("\n", "").trim());
                data.append("\n");
            }
            write(data.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public File getFile() throws IOException{
        String sep = FileSystems.getDefault().getSeparator();
        String loc = System.getProperty("user.home") + sep + "." + company;
        File companyDir = new File(loc);
        if(!companyDir.exists()){
            if(!companyDir.mkdir()){
                System.out.println("Cannot make company dir.");
                System.exit(-1);
            }
        }
        if(!isGlobal){
            loc += sep + app;
            File appDir = new File(loc);
            if(!appDir.exists()){
                if(!appDir.mkdir()){
                    System.out.println("Cannot make app dir.");
                    System.exit(-1);
                }
            }
        }
        loc += sep + file;
        File data = new File(loc);
        if(!data.exists()){
            if(!data.createNewFile()){
                System.out.println("Cannot make data file.");
                System.exit(-1);
            }
        }
        return data;
    }

    public void putBytes(String key, byte[] value) {
        for(KeyValuePair pair : pairs){
            if(pair.key.equals(key)) {
                pair.value = value;
                return;
            }
        }
        pairs.add(new KeyValuePair(key, value));
    }

    public byte[] getBytes(String key, byte[] defaultValue) {
        for(KeyValuePair pair : pairs){
            if(pair.key.equals(key)) return pair.value;
        }
        pairs.add(new KeyValuePair(key, defaultValue));
        return defaultValue;
    }

    public void flush() {
        savePairs();
    }

    private static class KeyValuePair{
        public String key;
        public byte[] value;

        public KeyValuePair(String key, byte[] value){
            this.key = key;
            this.value = value;
        }
    }
}
