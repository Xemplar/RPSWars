package com.xemplarsoft.utils.savedb;

import static com.xemplarsoft.utils.savedb.RawDataHandler.EMPTY;

public final class DataHandler implements RawDataHandler{
    private RawDataHandler handler;
    private DataModifier modifier;

    DataHandler(RawDataHandler handler){
        this.handler = handler;
        this.modifier = null;
    }

    DataHandler(RawDataHandler handler, DataModifier modifier){
        this.handler = handler;
        this.modifier = modifier;
    }

    public void flush(){
        this.handler.flush();
    }

    public void putBytes(String key, byte[] bytes){
        this.handler.putBytes(key, bytes);
    }

    public byte[] getBytes(String key, byte[] defaultValue){
        return this.handler.getBytes(key, defaultValue);
    }

    public final void putString(String key, String value){
        putBytes(key, value.getBytes());
    }
    
    public final void putDouble(String key, double pre){
        long value = Double.doubleToLongBits(pre);
        int size = Long.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putFloat(String key, float pre){
        int value = Float.floatToIntBits(pre);
        int size = Long.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putLong(String key, long value){
        int size = Long.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putInt(String key, int value){
        int size = Integer.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putShort(String key, short value){
        int size = Short.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putChar(String key, char value){
        int size = Character.SIZE / 8;

        byte[] val = new byte[size];
        for(int i = 0; i < size; i++){
            val[i] = (byte)(((0xFF << (i * 8L)) & value) >> (i * 8L));
        }

        putBytes(key, val);
    }
    
    public final void putByte(String key, byte value){
        byte[] val = new byte[1];
        val[0] = value;

        putBytes(key, val);
    }
    
    public final void putBoolean(String key, boolean value){
        byte[] val = new byte[1];
        val[0] = (byte)(value ? 1 : 0);

        putBytes(key, val);
    }
    
    public String getString(String key, String value){
        byte[] val = getBytes(key, EMPTY);
        return val == EMPTY ? null : new String(val);
    }
    
    public double getDouble(String key, double defaultValue){
        byte[] val = getBytes(key, EMPTY);
        try{
            return Double.longBitsToDouble(getLong(key, Double.doubleToLongBits(defaultValue)));
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public float getFloat(String key, float defaultValue){
        byte[] val = getBytes(key, EMPTY);
        try{
            return Float.intBitsToFloat(getInt(key, Float.floatToIntBits(defaultValue)));
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public long getLong(String key, long defaultValue){
        int size = Long.SIZE / 8;
        byte[] val = getBytes(key, EMPTY);
        
        try{
            long ret = 0L;
            
            for(int i = 0; i < size; i++){
                ret |= (long)(val[i] << (i * 8L));
            }
            
            return ret;
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public int getInt(String key, int defaultValue){
        int size = Integer.SIZE / 8;
        byte[] val = getBytes(key, EMPTY);

        try{
            int ret = 0;

            for(int i = 0; i < size; i++){
                ret |= (int)(val[i] << (i * 8));
            }
            
            return ret;
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public short getShort(String key, short defaultValue){
        int size = Short.SIZE / 8;
        byte[] val = getBytes(key, EMPTY);

        try{
            short ret = 0;

            for(int i = 0; i < size; i++){
                ret |= (short)(val[i] << (i * 8));
            }

            return ret;
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public char getChar(String key, char defaultValue){
        int size = Character.SIZE / 8;
        byte[] val = getBytes(key, EMPTY);

        try{
            char ret = 0;

            for(int i = 0; i < size; i++){
                ret |= (char)(val[i] << (i * 8));
            }

            return ret;
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    public byte getByte(String key, byte defaultValue){
        byte[] val = getBytes(key, new byte[]{defaultValue});

        return val[0];
    }
    
    public boolean getBoolean(String key, boolean defaultValue){
        byte[] val = getBytes(key, new byte[]{((byte)(defaultValue ? 1 : 0))});

        return val[0] == 1;
    }

    interface DataModifier {
        public byte[] unModify(byte[] data);
        public byte[] modify(byte[] data);
    }
}
