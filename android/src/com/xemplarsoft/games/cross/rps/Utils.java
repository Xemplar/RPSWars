package com.xemplarsoft.games.cross.rps;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public final class Utils {
    private Utils(){}

    /**
     * Checks if string supplied is a username or email
     * @param username checked string.
     * @return returns true if string is an email.
     */
    public static boolean checkIfEmail(String username){
        System.out.print("Checking: " + username + ". This is ");
        boolean ret = true;
        try {
            int at = username.indexOf("@");
            int dot = username.lastIndexOf(".");
            int len_before_at = username.substring(0, at).length();
            int len_before_dot = username.substring(at + 1, dot).length();
            int len_after_dot = username.substring(dot + 1).length();

            ret &= at > 0;
            ret &= dot > at;
            ret &= len_before_at >= 2;
            ret &= len_before_dot >= 2;
            ret &= len_after_dot >= 2;

            if(ret){
                String allowedNormal = "abcdefghijklmnopqrstuvwxyqABCDEFGHIJKLMNOPQURSTUVXYZ0123456789!#$%&'*+-/=?^_`{|}~";
                String allowedAll = "abcdefghijklmnopqrstuvwxyqABCDEFGHIJKLMNOPQURSTUVXYZ0123456789!#$%&'*+-/=?^_`{|}~. \"(),:;<>@[\\]";
                String difference = ". \"(),:;<>@[\\]";

                char[] local = username.substring(0, at).toCharArray();
                String local_str = username.substring(0, at);

                boolean hasBad = false;
                for(char c : local){
                    hasBad |= !allowedAll.contains(c + "");
                }

                if(hasBad) return false;

                boolean hasQuotes = local[0] == '"' && local[local.length - 1] == '"';
                if(!hasQuotes && local_str.contains("..")) return false;

                for(int i = 0; i < local.length; i++){
                    boolean hasSpecial = !allowedNormal.contains(local[i] + "");
                    if(hasSpecial){
                        switch (local[i]){
                            case '.': {
                                if(i == 0 || (i == (local.length - 1))) return false;
                                break;
                            }
                            case '"':{
                                if(!hasQuotes && (i == 0 || i == (local.length - 1))) return false;
                                if(i == 1) return false;
                                if(i != 0 && i != local.length - 1 && local[i - 1] != '\\') return false;
                                break;
                            }
                            case '\\':{
                                if(i == 0) return false;
                                if(local[i - 1] != '\\') return false;
                            }
                            case ',':
                            case ':':
                            case ';':
                            case '<':
                            case '>':
                            case '@':
                            case '[':
                            case ']':{
                                if(i == 0) return false;
                                break;
                            }

                            default:
                                return false;
                        }
                    }
                }
            }
        } catch (Exception e){
            ret = false;
        }

        return ret;
    }


    /**
     * Convert byte array to hex string
     * @param bytes toConvert
     * @return hexValue
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for(int idx=0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     * @param str which to be converted
     * @return  array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try { return str.getBytes("UTF-8"); } catch (Exception ex) { return null; }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * @param filename which to be converted to string
     * @return String value of File
     * @throws java.io.IOException if error occurs
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN=1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8=false;
            int read,count=0;
            while((read=is.read(bytes)) != -1) {
                if (count==0 && bytes[0]==(byte)0xEF && bytes[1]==(byte)0xBB && bytes[2]==(byte)0xBF ) {
                    isUTF8=true;
                    baos.write(bytes, 3, read-3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count+=read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try{ is.close(); } catch(Exception ignored){}
        }
    }
}
