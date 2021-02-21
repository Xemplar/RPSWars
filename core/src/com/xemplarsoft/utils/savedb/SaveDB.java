package com.xemplarsoft.utils.savedb;

import java.util.Arrays;

import static com.xemplarsoft.utils.savedb.RawDataHandler.EMPTY;

public class SaveDB implements DataHandler.DataModifier {
    public static final byte FUNCTION_EMPTY   = 0b00000000;
    public static final byte FUNCTION_B64     = 0b00000001;
    public static final byte FUNCTION_ENCRYPT = 0b00000010;

    protected byte functions = FUNCTION_EMPTY;

    protected DataHandler handler;
    public SaveDB(boolean useModifier, RawDataHandler handler){
        this.handler = useModifier ? new DataHandler(handler, this) : new DataHandler(handler);
    }

    public void flush(){
        handler.flush();
    }

    public void useFunctionB64(boolean enabled){
        if(enabled) functions |= FUNCTION_B64;
        else functions &= ~FUNCTION_B64;
    }

    public byte[] unModify(byte[] data) {
        if(Arrays.equals(data, EMPTY)) return data;
        if(data.length < 2) return data;

        byte[] ret = new byte[data.length - 1];
        System.arraycopy(data, 1, ret, 0, ret.length);

        if(useFunction(data[0], FUNCTION_B64)){
            ret = Base64.decode(new String(ret));
        }
        return ret;
    }
    
    public byte[] modify(byte[] data) {
        if(Arrays.equals(data, EMPTY)) return data;
        if(data.length < 2) return data;

        byte[] ret = new byte[data.length - 1];
        System.arraycopy(data, 1, ret, 0, ret.length);

        if(useFunction(FUNCTION_B64)){
            ret = Base64.encode(ret);
        }
        return ret;
    }

    private boolean useFunction(byte function){
        return (this.functions & function) == function;
    }

    private boolean useFunction(byte modify, byte function){
        return (modify & function) == function;
    }
    
    // Data Methods

    /**
     * Stores a String using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putString(String key, String value){ this.handler.putString(key, value); }
    /**
     * Stores a Double using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putDouble(String key, double value){ this.handler.putDouble(key, value); }
    /**
     * Stores a Float using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putFloat(String key, float value){ this.handler.putFloat(key, value); }
    /**
     * Stores a Long using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putLong(String key, long value){ this.handler.putLong(key, value); }
    /**
     * Stores an Integer using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putInt(String key, int value){ this.handler.putInt(key, value); }
    /**
     * Stores a Short using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putShort(String key, short value){ this.handler.putShort(key, value); }
    /**
     * Stores a Character using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putChar(String key, char value){ this.handler.putChar(key, value); }
    /**
     * Stores a Byte using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putByte(String key, byte value){ this.handler.putByte(key, value); }
    /**
     * Stores a Boolean using the your RawDataHandler
     * @param key name to use to retrieve value later.
     * @param value value to store to be read later
     */
    public final void putBoolean(String key, boolean value){ this.handler.putBoolean(key, value); }
    /**
     * Retrieves a String using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public String getString(String key, String defaultValue){ return this.handler.getString(key, defaultValue); }
    /**
     * Retrieves a Double using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public double getDouble(String key, double defaultValue){ return this.handler.getDouble(key, defaultValue); }
    /**
     * Retrieves a Float using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public float getFloat(String key, float defaultValue){ return this.handler.getFloat(key, defaultValue); }
    /**
     * Retrieves a Long using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public long getLong(String key, long defaultValue){ return this.handler.getLong(key, defaultValue); }
    /**
     * Retrieves an Integer using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public int getInt(String key, int defaultValue){ return this.handler.getInt(key, defaultValue); }
    /**
     * Retrieves a Short using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public short getShort(String key, short defaultValue){ return this.handler.getShort(key, defaultValue); }
    /**
     * Retrieves a Character using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public char getChar(String key, char defaultValue){ return this.handler.getChar(key, defaultValue); }
    /**
     * Retrieves a Byte using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public byte getByte(String key, byte defaultValue){ return this.handler.getByte(key, defaultValue); }
    /**
     * Retrieves a Boolean using the your RawDataHandler
     * @param key name to use to retrieve the associated value.
     * @param defaultValue value to return if no value is found for the specified key
     */
    public boolean getBoolean(String key, boolean defaultValue){ return this.handler.getBoolean(key, defaultValue); }
}
