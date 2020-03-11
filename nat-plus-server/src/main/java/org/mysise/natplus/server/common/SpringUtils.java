package org.mysise.natplus.server.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
}