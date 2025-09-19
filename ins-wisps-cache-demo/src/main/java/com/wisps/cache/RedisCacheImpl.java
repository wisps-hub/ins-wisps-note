package com.wisps.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class RedisCacheImpl implements ICache {

    private static ConcurrentMap<Method, Method> methodMap = new ConcurrentHashMap<Method, Method>();
    private final RedisContext redisContext;
    private final Jedis jedis;
    private final ICache redisCacheProxy;

    public RedisCacheImpl(String host, int port) {
        redisContext = new RedisContext();
        jedis = new Jedis(host, port);
        redisCacheProxy = (ICache) Proxy.newProxyInstance(RedisContext.class.getClassLoader(),
                new Class[]{ICache.class}, new RedisCacheInterceptor());
    }

    class RedisCacheInterceptor implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            long createTime = System.currentTimeMillis();
            try {
                args = Optional.ofNullable(args).orElseGet(() -> new Object[0]);
                Object[] _args = new Object[args.length + 1];
                _args[0] = jedis;
                System.arraycopy(args, 0, _args, 1, args.length);
                result = oppositeMethod(method).invoke(redisContext, _args);
            } catch (Throwable t) {
                log.info("redis invoke fail cost: {}ms", System.currentTimeMillis() - createTime);
            }
            return result;
        }

        private Method oppositeMethod(final Method method) {
            Method oppMethod = methodMap.get(method);
            if (oppMethod == null) {
                String name = method.getName();
                Class<?>[] pTypes = method.getParameterTypes();
                Class<?>[] _pTypes = new Class<?>[pTypes.length + 1];
                _pTypes[0] = Jedis.class;
                System.arraycopy(pTypes, 0, _pTypes, 1, pTypes.length);
                try {
                    oppMethod = RedisContext.class.getDeclaredMethod(name, _pTypes);
                } catch (Exception e) {
                    throw new RuntimeException("RedisContext " + name + " method absent", e);
                }
                methodMap.put(method, oppMethod);
            }
            return oppMethod;
        }
    }

    @Override
    public void set(String key, String value) {
        redisCacheProxy.set(key, value);
    }

    @Override
    public String getString(String key) {
        return redisCacheProxy.getString(key);
    }

    @Override
    public long incr(String key) {
        return redisCacheProxy.incr(key);
    }

    @Override
    public long decr(String key) {
        return redisCacheProxy.decr(key);
    }

    @Override
    public long incrBy(String key, Long amount) {
        return redisCacheProxy.incrBy(key, amount);
    }

    @Override
    public double incrByFloat(String key, double amount) {
        return redisCacheProxy.incrByFloat(key, amount);
    }

    @Override
    public long decrBy(String key, Long amount) {
        return redisCacheProxy.decrBy(key, amount);
    }

    @Override
    public void append(String key, String appendStr){
        redisCacheProxy.append(key, appendStr);
    }

    @Override
    public void setRange(String key, long offset, String appendStr){
        redisCacheProxy.setRange(key, offset, appendStr);
    }

    @Override
    public String getRange(String key, long startOffset, long endOffset){
        return redisCacheProxy.getRange(key, startOffset, endOffset);
    }

    @Override
    public boolean setBit(String key, long offset, String value){
        return redisCacheProxy.setBit(key, offset, value);
    }

    @Override
    public boolean getBit(String key, long offset){
        return redisCacheProxy.getBit(key, offset);
    }

    @Override
    public long bitCount(String key){
        return redisCacheProxy.bitCount(key);
    }

    @Override
    public long bitCountRange(String key, long start, long end){
        return redisCacheProxy.bitCountRange(key, start, end);
    }

    @Override
    public long bitOp(BitOP op, String key, String... srcKey){
        return redisCacheProxy.bitOp(op, key, srcKey);
    }

    @Override
    public long rpush(String key, String... values){
        return redisCacheProxy.rpush(key, values);
    }

    @Override
    public long lpush(String key, String... values){
        return redisCacheProxy.lpush(key, values);
    }

    @Override
    public String rpop(String key){
        return redisCacheProxy.rpop(key);
    }

    @Override
    public List<String> rpopBatch(String key, int batch){
        return redisCacheProxy.rpopBatch(key, batch);
    }

    @Override
    public String lpop(String key){
        return redisCacheProxy.lpop(key);
    }

    @Override
    public List<String> lpopBatch(String key, int batch){
        return redisCacheProxy.lpopBatch(key, batch);
    }

    @Override
    public String lindex(String key, long idx){
        return redisCacheProxy.lindex(key, idx);
    }

    @Override
    public List<String> lrange(String key, long start, long end){
        return redisCacheProxy.lrange(key, start, end);
    }

    @Override
    public void ltrim(String key, long start, long end){
        redisCacheProxy.ltrim(key, start, end);
    }

    @Override
    public List<String> blpop(String key, int timeout){
        return redisCacheProxy.blpop(key, timeout);
    }

    @Override
    public List<String> brpop(String key, int timeout){
        return redisCacheProxy.brpop(key, timeout);
    }

    @Override
    public String rpoplpush(String srcKey, String deskKey){
        return redisCacheProxy.rpoplpush(srcKey, deskKey);
    }

    @Override
    public String brpoplpush(String srcKey, String deskKey, int timeout){
        return redisCacheProxy.brpoplpush(srcKey, deskKey, timeout);
    }

    @Override
    public long sadd(String key, String... values){
        return redisCacheProxy.sadd(key, values);
    }

    @Override
    public long srem(String key, String... values){
        return redisCacheProxy.srem(key, values);
    }

    @Override
    public boolean sismember(String key, String value){
        return redisCacheProxy.sismember(key, value);
    }

    @Override
    public long scard(String key){
        return redisCacheProxy.scard(key);
    }

    @Override
    public Set<String> smembers(String key){
        return redisCacheProxy.smembers(key);
    }

    @Override
    public String srandmember(String key){
        return redisCacheProxy.srandmember(key);
    }

    @Override
    public List<String> srandmemberBatch(String key, int batch){
        return redisCacheProxy.srandmemberBatch(key, batch);
    }

    @Override
    public String spop(String key){
        return redisCacheProxy.spop(key);
    }

    @Override
    public Set<String> spopBatch(String key, long batch){
        return redisCacheProxy.spopBatch(key, batch);
    }

    @Override
    public long smove(String srcKey, String deskKey, String member){
        return redisCacheProxy.smove(srcKey, deskKey, member);
    }

    @Override
    public Set<String> sdiff(String... keys){
        return redisCacheProxy.sdiff(keys);
    }

    @Override
    public long sdiffstore(String deskKey, String... keys){
        return redisCacheProxy.sdiffstore(deskKey, keys);
    }

    @Override
    public Set<String> sinter(String... keys){
        return redisCacheProxy.sinter(keys);
    }

    @Override
    public long sinterstore(String deskKey, String... keys){
        return redisCacheProxy.sinterstore(deskKey, keys);
    }

    @Override
    public Set<String> sunion(String... keys){
        return redisCacheProxy.sunion(keys);
    }

    @Override
    public long sunionstore(String deskKey, String... keys){
        return redisCacheProxy.sunionstore(deskKey, keys);
    }

    @Override
    public void hmset(String key, Map<String, String> hash){
        redisCacheProxy.hmset(key, hash);
    }

    @Override
    public List<String> hmget(String key, String... fields){
        return redisCacheProxy.hmget(key, fields);
    }

    @Override
    public long hdel(String key, String... fields){
        return redisCacheProxy.hdel(key, fields);
    }

    @Override
    public long hlen(String key){
        return redisCacheProxy.hlen(key);
    }

    @Override
    public boolean hexists(String key, String field){
        return redisCacheProxy.hexists(key, field);
    }

    @Override
    public Set<String> hkeys(String key){
        return redisCacheProxy.hkeys(key);
    }

    @Override
    public List<String> hvals(String key){
        return redisCacheProxy.hvals(key);
    }

    @Override
    public Map<String, String> hgetAll(String key){
        return redisCacheProxy.hgetAll(key);
    }

    @Override
    public long hincrBy(String key, String field, long count){
        return redisCacheProxy.hincrBy(key, field, count);
    }

    @Override
    public double hincrByFloat(String key, String field, double count){
        return redisCacheProxy.hincrByFloat(key, field, count);
    }

    @Override
    public long zadd(String key, String member, double score){
        return redisCacheProxy.zadd(key, member, score);
    }

    @Override
    public long zaddMap(String key, Map<String, Double> scoreMembers){
        return redisCacheProxy.zaddMap(key, scoreMembers);
    }

    @Override
    public long zrem(String key, String member){
        return redisCacheProxy.zrem(key, member);
    }

    @Override
    public long zcard(String key){
        return redisCacheProxy.zcard(key);
    }

    @Override
    public double zincrby(String key, double amount, String member){
        return redisCacheProxy.zincrby(key, amount, member);
    }

    @Override
    public long zcount(String key, double min, double max){
        return redisCacheProxy.zcount(key, min, max);
    }

    @Override
    public long zrank(String key, String member){
        return redisCacheProxy.zrank(key, member);
    }

    @Override
    public double zscore(String key, String member){
        return redisCacheProxy.zscore(key, member);
    }

    @Override
    public Set<String> zrange(String key, long start, long end){
        return redisCacheProxy.zrange(key, start, end);
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end){
        return redisCacheProxy.zrangeWithScores(key, start, end);
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max){
        return redisCacheProxy.zrangeByScore(key, min, max);
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max){
        return redisCacheProxy.zrangeByScoreWithScores(key, min, max);
    }

    @Override
    public long zrevrank(String key, String member){
        return redisCacheProxy.zrevrank(key, member);
    }

    @Override
    public Set<String> zrevrange(String key, long start, long stop){
        return redisCacheProxy.zrevrange(key, start, stop);
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min){
        return redisCacheProxy.zrevrangeByScore(key, max, min);
    }

    @Override
    public long zremrangeByRank(String key, long start, long stop){
        return redisCacheProxy.zremrangeByRank(key, start, stop);
    }

    @Override
    public long zremrangeByScore(String key, double min, double max){
        return redisCacheProxy.zremrangeByScore(key, min, max);
    }

    @Override
    public long zinterstore(String destKey, String... srcKey){
        return redisCacheProxy.zinterstore(destKey, srcKey);
    }

    @Override
    public long zunionstore(String destKey, String... srcKey){
        return redisCacheProxy.zunionstore(destKey, srcKey);
    }

}
