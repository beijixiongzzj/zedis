package com.zedis.exception;

/**
 * Created by Administrator on 2018/4/15.
 */
public class ZedisShardInfoException extends Exception{
    private String message;

    public ZedisShardInfoException(String message) {
        super(message);
        this.message = message;
    }
}
