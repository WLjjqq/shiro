package com.shiro.bean;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisJava {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        //String中的存值
        jedis.set("li", "bai");
        //String中的取值
        System.out.println("String中li的值"+jedis.get("li"));

        //list   是一个栈。先进后出
        jedis.lpush("a-list","LiBai");
        jedis.lpush("a-list","WangBo");
        jedis.lpush("a-list","DuFu");
        List<String> list=jedis.lrange("a-list",0,2);
        for(int i=0;i<list.size();i++){
            System.out.println("Redis中list的值："+list.get(i));
        }

        //keys
        Set<String> kyes = jedis.keys("*");
        Iterator<String> iterator =kyes.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println("key的值："+key);
        }
    }
}
