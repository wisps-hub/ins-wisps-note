package com.wisps.cache;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache {
    void set(String key, String value);

    String getString(String key);

    long incr(String key);

    long decr(String key);

    long incrBy(String key, Long amount);

    double incrByFloat(String key, double amount);

    long decrBy(String key, Long amount);

    void append(String key, String appendStr);

    void setRange(String key, long offset, String appendStr);

    String getRange(String key, long startOffset, long endOffset);

    boolean setBit(String key, long offset, String value);

    boolean getBit(String key, long offset);

    long bitCount(String key);

    long bitCountRange(String key, long start, long end);

    long bitOp(BitOP op, String key, String... srcKey);

    long rpush(String key, String... values);

    long lpush(String key, String... values);

    String rpop(String key);

    List<String> rpopBatch(String key, int batch);

    String lpop(String key);

    List<String> lpopBatch(String key, int batch);

    String lindex(String key, long idx);

    List<String> lrange(String key, long start, long end);

    void ltrim(String key, long start, long end);

    List<String> blpop(String key, int timeout);

    List<String> brpop(String key, int timeout);

    String rpoplpush(String srcKey, String deskKey);

    String brpoplpush(String srcKey, String deskKey, int timeout);

    long sadd(String key, String... values);

    long srem(String key, String... values);

    boolean sismember(String key, String value);

    long scard(String key);

    Set<String> smembers(String key);

    String srandmember(String key);

    List<String> srandmemberBatch(String key, int batch);

    String spop(String key);

    Set<String> spopBatch(String key, long batch);

    long smove(String srcKey, String deskKey, String member);

    Set<String> sdiff(String... keys);

    long sdiffstore(String deskKey, String... keys);

    Set<String> sinter(String... keys);

    long sinterstore(String deskKey, String... keys);

    Set<String> sunion(String... keys);

    long sunionstore(String deskKey, String... keys);

    void hmset(String key, Map<String, String> hash);

    List<String> hmget(String key, String... fields);

    long hdel(String key, String... fields);

    long hlen(String key);

    boolean hexists(String key, String field);

    Set<String> hkeys(String key);

    List<String> hvals(String key);

    Map<String, String> hgetAll(String key);

    long hincrBy(String key, String field, long count);

    double hincrByFloat(String key, String field, double count);

    long zadd(String key, String member, double score);

    long zaddMap(String key, Map<String, Double> scoreMembers);

    long zrem(String key, String member);

    long zcard(String key);

    double zincrby(String key, double amount, String member);

    long zcount(String key, double min, double max);

    long zrank(String key, String member);

    double zscore(String key, String member);

    Set<String> zrange(String key, long start, long end);

    Set<Tuple> zrangeWithScores(String key, long start, long end);

    Set<String> zrangeByScore(String key, double min, double max);

    Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

    long zrevrank(String key, String member);

    Set<String> zrevrange(String key, long start, long stop);

    Set<String> zrevrangeByScore(String key, double max, double min);

    long zremrangeByRank(String key, long start, long stop);

    long zremrangeByScore(String key, double min, double max);

    long zinterstore(String destKey, String... srcKey);

    long zunionstore(String destKey, String... srcKey);

}