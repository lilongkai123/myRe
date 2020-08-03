    package com.fh.util;

    import redis.clients.jedis.Jedis;
    import redis.clients.jedis.JedisPool;
    import redis.clients.jedis.JedisPoolConfig;

    public class RedisPool {

        private static JedisPool jedisPool =null;
        private static void initPool(){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(200);
            config.setMaxIdle(50);
            config.setMinIdle(8);//设置最小空闲数
            config.setMaxWaitMillis(10000);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            config.setTestWhileIdle(true);
            config.setTimeBetweenEvictionRunsMillis(30000);
            config.setNumTestsPerEvictionRun(10);
            config.setMinEvictableIdleTimeMillis(60000);
            //使用jedis 连接redis（连接池配置，地址，端口号）
            jedisPool = new JedisPool(config, "192.168.160.128", 6379, 10000, "123456", 0);
        }

        //静态块 在加载类时只会执行一次
        static{
            initPool();
        }
        //公共方法 供其他类调用
        public static Jedis getJedis(){
            return jedisPool.getResource();
        }
    }
