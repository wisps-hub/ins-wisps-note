package com.wisps.cache;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisContext {
    /**
     * redis中STRING类型可以存储
     * 1 子节串 （byte String）
     * 2 整数
     * 3 浮点数
     * 用户将一个值存储到redis字符串中，如果这个值能被解释为十进制整数或者浮点数，redis会察觉这一点并允许用户执行incr和decr操作。
     * 如果健不存在或者保存了空串，redis会将此值当作0处理。
     * 如果用户尝试对一个无法被解释为整数或浮点数的字符串进行自增/减操作，redis将返回错误
     */

    public void set(Jedis jedis, String key, String value) {
        jedis.set(key, value);
    }

    public String getString(Jedis jedis, String key) {
        return jedis.get(key);
    }

    public long incr(Jedis jedis, String key) {
        //返回操作后的值
        return jedis.incr(key);
    }

    public long decr(Jedis jedis, String key) {
        //返回操作后的值
        return jedis.decr(key);
    }

    public long incrBy(Jedis jedis, String key, Long amount) {
        //返回操作后的值
        return jedis.incrBy(key, amount);
    }

    public double incrByFloat(Jedis jedis, String key, double amount) {
        //返回操作后的值
        return jedis.incrByFloat(key, amount);
    }

    public long decrBy(Jedis jedis, String key, Long amount) {
        //返回操作后的值
        return jedis.decrBy(key, amount);
    }

    public void append(Jedis jedis, String key, String appendStr){
        //返回操作后的长度
        jedis.append(key, appendStr);
    }

    public void setRange(Jedis jedis, String key, long offset, String appendStr){
        //返回操作后的长度
        //从offset下标位置开始向后覆盖。
        //位数不够会用null扩展至所需长度(一个null代表一位)，再进行覆盖。
        jedis.setrange(key, offset, appendStr);
    }

    public String getRange(Jedis jedis, String key, long startOffset, long endOffset){
        //返回 [startOffset，endOffset] 区间内字符
        return jedis.getrange(key, startOffset, endOffset);
    }

    public boolean setBit(Jedis jedis, String key, long offset, String value){
        return jedis.setbit(key, offset, value);
    }

    public boolean getBit(Jedis jedis, String key, long offset){
        return jedis.getbit(key, offset);
    }

    public long bitCount(Jedis jedis, String key){
        //统计二进制串中值为1的二进制位数量
        return jedis.bitcount(key);
    }

    public long bitCountRange(Jedis jedis, String key, long start, long end){
        //统计[start，end]范围内二机制位值为1的二进制位数量
        return jedis.bitcount(key, start, end);
    }

    public long bitOp(Jedis jedis, BitOP op, String key, String... srcKey){
        return jedis.bitop(op, key, srcKey);
    }

/******************************** 列表操作 *************************************/
    /**
     * 列表操作
     * 允许传入空串作为一个元素，但不允许传null
     */

    public long rpush(Jedis jedis, String key, String... values){
        //返回列表长度
        return jedis.rpush(key, values);
    }

    public long lpush(Jedis jedis, String key, String... values){
        //返回列表长度
        return jedis.lpush(key, values);
    }

    public String rpop(Jedis jedis, String key){
        //从列表右侧弹出一个元素
        //元素为空时key会被清除
        return jedis.rpop(key);
    }

    public List<String> rpopBatch(Jedis jedis, String key, int batch){
        //从列表右侧弹出count个元素
        //元素为空时key会被清除
        return jedis.rpop(key, batch);
    }

    public String lpop(Jedis jedis, String key){
        //从列表左侧弹出一个元素
        //元素为空时key会被清除
        return jedis.lpop(key);
    }

    public List<String> lpopBatch(Jedis jedis, String key, int batch){
        //从列表左侧弹出count个元素
        //元素为空时key会被清除
        return jedis.lpop(key, batch);
    }

    public String lindex(Jedis jedis, String key, long idx){
        //返回偏移量为idx的元素
        return jedis.lindex(key, idx);
    }

    public List<String> lrange(Jedis jedis, String key, long start, long end){
        //返回偏移量 [start, end] 的元素
        return jedis.lrange(key, start, end);
    }

    public void ltrim(Jedis jedis, String key, long start, long end){
        //修剪，只保留 [start, end] 的元素
        jedis.ltrim(key, start, end);
    }

    public List<String> blpop(Jedis jedis, String key, int timeout){
        // 从列表左端弹出一个元素，返回 [key, value]
        // 无元素时阻塞timeout秒，仍无元素返回null
        //元素为空时key会被清除
        return jedis.blpop(timeout, key);
    }

    public List<String> brpop(Jedis jedis, String key, int timeout){
        // 从列表左端弹出一个元素，返回 [key, value]
        // 无元素时阻塞timeout秒，仍无元素返回null
        //元素为空时key会被清除
        return jedis.brpop(timeout, key);
    }

    public String rpoplpush(Jedis jedis, String srcKey, String deskKey){
        //从srcKey右端弹出一个元素推入deskKey左端
        // 返回被操作元素，无元素返回null
        //元素为空时key会被清除
        return jedis.rpoplpush(srcKey, deskKey);
    }

    public String brpoplpush(Jedis jedis, String srcKey, String deskKey, int timeout){
        // 从srcKey右端弹出一个元素推入deskKey左端，返回被操作元素
        // 无元素时阻塞timeout秒，仍无元素返回null
        //元素为空时key会被清除
        return jedis.brpoplpush(srcKey, deskKey, timeout);
    }

/******************************** 集合操作 *************************************/

    /**
     * 集合操作， 无重复元素
     * 允许传入空串当作一个元素， 但不允许传null
     */

    public long sadd(Jedis jedis, String key, String... values){
        //返回成功插入元素个数
        return jedis.sadd(key, values);
    }

    public long srem(Jedis jedis, String key, String... values){
        //返回成功移除元素个数
        return jedis.srem(key, values);
    }

    public boolean sismember(Jedis jedis, String key, String value){
        return jedis.sismember(key, value);
    }

    public long scard(Jedis jedis, String key){
        return jedis.scard(key);
    }

    public Set<String> smembers(Jedis jedis, String key){
        //返回集合中所有元素
        return jedis.smembers(key);
    }

    public String srandmember(Jedis jedis, String key){
        //从集合中随机返回一个元素
        return jedis.srandmember(key);
    }

    public List<String> srandmemberBatch(Jedis jedis, String key, int batch){
        //从集合中随机返回一组元素， batch > 0 不重复， batch < 0 会重复
        return jedis.srandmember(key, batch);
    }

    public String spop(Jedis jedis, String key){
        //从集合中随机移除一个元素并返回元素
        // 元素为空时key会被清除
        return jedis.spop(key);
    }

    public Set<String> spopBatch(Jedis jedis, String key, long batch){
        //从集合中随机移除组元素并返回元素
        // 元素为空时key会被清除
        return jedis.spop(key, batch);
    }

    public long smove(Jedis jedis, String srcKey, String deskKey, String member){
        // 将member从srcKey移动到deakKey，成功返回1，否则返回0
        return jedis.smove(srcKey, deskKey, member);
    }

    public Set<String> sdiff(Jedis jedis, String... keys){
        //差集
        //返回存在于第一个key中，不存在于其他key中的元素
        return jedis.sdiff(keys);
    }

    public long sdiffstore(Jedis jedis, String deskKey, String... keys){
        //存储差集
        return jedis.sdiffstore(deskKey, keys);
    }

    public Set<String> sinter(Jedis jedis, String... keys){
        //交集
        //返回同时存在于所有key中的元素
        return jedis.sinter(keys);
    }

    public long sinterstore(Jedis jedis, String deskKey, String... keys){
        //存储交集
        return jedis.sinterstore(deskKey, keys);
    }

    public Set<String> sunion(Jedis jedis, String... keys){
        //并集
        return jedis.sunion(keys);
    }

    public long sunionstore(Jedis jedis, String deskKey, String... keys){
        //存储并集
        return jedis.sunionstore(deskKey, keys);
    }

/******************************** 散列操作 *************************************/

    public void hmset(Jedis jedis, String key, Map<String, String> hash){
        //hash中k-v都不允许为null，但可以是空串
        jedis.hmset(key, hash);
    }

    public List<String> hmget(Jedis jedis, String key, String... fields){
        //返回健对应的值集合,不存在的健会返回null值
        return jedis.hmget(key, fields);
    }

    public long hdel(Jedis jedis, String key, String... fields){
        //返回成功删除的field个数
        return jedis.hdel(key, fields);
    }

    public long hlen(Jedis jedis, String key){
        //返回散列包含的健值对数量
        return jedis.hlen(key);
    }

    public boolean hexists(Jedis jedis, String key, String field){
        return jedis.hexists(key, field);
    }

    public Set<String> hkeys(Jedis jedis, String key){
        //返回所有健，空返回空集合
        return jedis.hkeys(key);
    }

    public List<String> hvals(Jedis jedis, String key){
        //返回所有值，空返回空集合
        return jedis.hvals(key);
    }

    public Map<String, String> hgetAll(Jedis jedis, String key){
        //返回所有健值对，空返回空map
        return jedis.hgetAll(key);
    }

    public long hincrBy(Jedis jedis, String key, String field, long count){
        //返回修改后的值
        return jedis.hincrBy(key, field, count);
    }

    public double hincrByFloat(Jedis jedis, String key, String field, double count){
        //返回修改后的值
        return jedis.hincrByFloat(key, field, count);
    }

/******************************** 有序集合 *************************************/

    public long zadd(Jedis jedis, String key, String member, double score){
        //返回新增成功数量
        return jedis.zadd(key, score, member);
    }

    public long zaddMap(Jedis jedis, String key, Map<String, Double> scoreMembers){
        //返回新增成功数量
        return jedis.zadd(key, scoreMembers);
    }

    public long zrem(Jedis jedis, String key, String member){
        //返回删除成功数量
        return jedis.zrem(key, member);
    }

    public long zcard(Jedis jedis, String key){
        //返回成员数量
        return jedis.zcard(key);
    }

    public double zincrby(Jedis jedis, String key, double amount, String member){
        //返回操作后的成员
        return jedis.zincrby(key, amount, member);
    }

    public long zcount(Jedis jedis, String key, double min, double max){
        //返回分值介于[min，max]间成员数量
        return jedis.zcount(key, min, max);
    }

    public long zrank(Jedis jedis, String key, String member){
        //返回成员排名，不存在返回null
        return jedis.zrank(key, member);
    }

    public double zscore(Jedis jedis, String key, String member){
        //返回成员分数，不存在返回null
        return jedis.zscore(key, member);
    }

    public Set<String> zrange(Jedis jedis, String key, long start, long end){
        //返回排名介于[start，end]间的成员
        return jedis.zrange(key, start, end);
    }

    public Set<Tuple> zrangeWithScores(Jedis jedis, String key, long start, long end){
        //返回排名介于[start，end]间的成员和分数
        return jedis.zrangeWithScores(key, start, end);
    }

    public Set<String> zrangeByScore(Jedis jedis, String key, double min, double max){
        //返回分数介于[min，max]间的成员
        return jedis.zrangeByScore(key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(Jedis jedis, String key, double min, double max){
        //返回分数介于[start，max]间的成员和分数
        return jedis.zrangeByScoreWithScores(key, min, max);
    }

    public long zrevrank(Jedis jedis, String key, String member){
        //返回分值按大->小排列的排名
        return jedis.zrevrank(key, member);
    }

    public Set<String> zrevrange(Jedis jedis, String key, long start, long stop){
        //返回分值按大->小排列的[start,stop]排名范围内成员
        return jedis.zrevrange(key, start, stop);
    }

    public Set<String> zrevrangeByScore(Jedis jedis, String key, double max, double min){
        //返回分值按大->小排列的[max,min]分数范围内成员
        return jedis.zrevrangeByScore(key, max, min);
    }

    public long zremrangeByRank(Jedis jedis, String key, long start, long stop){
        //移除排名介于[start,stop]成员, 返回删除成功数量
        return jedis.zremrangeByRank(key, start, stop);
    }

    public long zremrangeByScore(Jedis jedis, String key, double min, double max){
        //移除分数介于[min,max]成员, 返回删除成功数量
        return jedis.zremrangeByScore(key, min, max);
    }

    public long zinterstore(Jedis jedis, String destKey, String... srcKey){
        //存储交集
        return jedis.zinterstore(destKey, srcKey);
    }

    public long zunionstore(Jedis jedis, String destKey, String... srcKey){
        //存储并集
        return jedis.zunionstore(destKey, srcKey);
    }

}