package com.zedis.utils;

import java.util.zip.CRC32;

/**
 * Created by Administrator on 2018/4/15.
 */
public class Crc32Util {
    public static long crc32Str(String str){
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
        return crc32.getValue();
    }
}
