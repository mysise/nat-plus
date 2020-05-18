package org.mysise.natplus.server.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  获取ioc容器中的Spring bean
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/11 10:50
 */
@Component
public class SpringUtils implements ApplicationContextAware  {
    private static ApplicationContext applicationContext;
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    public static <T> Object getBean(Class<T> t){
        return getApplicationContext().getBean(t);
    }


    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue(10);
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>(11);
        map.put("11","11");
        map.size();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS,arrayBlockingQueue);
        threadPoolExecutor.submit(() -> {
            System.out.println("111");
            System.out.println("111");
        });
    }
}