package com.wisps.mq.consts;

public class RedisCacheKey {
    private static final String LOCK_PREFIX = "lock:";

    public static String getLockKey(String lockName) {
        return LOCK_PREFIX + lockName;
    }
}
