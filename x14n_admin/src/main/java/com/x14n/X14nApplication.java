package com.x14n;


import com.x14n.common.core.domain.entity.SysRole;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class X14nApplication {
    public static void main(String[] args) throws NoSuchFieldException {
        ConfigurableApplicationContext context = SpringApplication.run(X14nApplication.class, args);
        System.out.println(context);
        Object sysRoleBean = context.getBean(SysRole.class);
        System.out.println(sysRoleBean);

        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
    }

}
