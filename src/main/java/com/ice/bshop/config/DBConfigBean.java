package com.ice.bshop.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class DBConfigBean implements ImportBeanDefinitionRegistrar {
    static final String TIKV_PACKAGES = "com.ice.bshop.dao.tikv";
    static final String MYSQL_PACKAGES = "com.ice.bshop.dao.mysql";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.addIncludeFilter(new AnnotationTypeFilter(BingRepository.class));
        boolean isTikv = isTikv();
        String scanPackage = getScanPackage(isTikv);
        Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents(scanPackage);
        for (BeanDefinition beanDefinition : beanDefinitionSet){
            if (beanDefinition instanceof AnnotatedBeanDefinition){
                //获得注解上的参数信息
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                String beanClassAllName = beanDefinition.getBeanClassName();
                //将其注册进容器中
                registry.registerBeanDefinition("baseDao", beanDefinition);
            }
        }
    }

    private boolean isTikv(){
        String dbType = PropertiesUtils.getValue("db_type");
        return "tikv".equals(dbType);
    }

    private String getScanPackage(boolean isTikv){
        return isTikv ? TIKV_PACKAGES : MYSQL_PACKAGES;
    }

    /**
     * 允许Spring扫描接口上的注解
     * @return
     */
    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false);
    }
}
