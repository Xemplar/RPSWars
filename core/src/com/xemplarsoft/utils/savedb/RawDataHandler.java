package com.xemplarsoft.utils.savedb;

public interface RawDataHandler {
    public static final byte[] EMPTY = new byte[0];

    public abstract void putBytes(String key, byte[] value);
    public abstract byte[] getBytes(String key, byte[] defaultValue);
    public void flush();
}
