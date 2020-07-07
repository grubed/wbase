package com.mrwind.windbase.common.config.mongo;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.TypeConverter;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * @author wuyiming
 * Created by admin on 16/3/15.
 */
public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {

    /**
     * 要扫描并映射的包
     */
    private String[] mapPackages;

    /**
     * 要映射的类
     */
    private String[] mapClasses;


    private TypeConverter typeConverter;

    /**
     * 扫描包时，是否忽略不映射的类
     * 这里按照Morphia的原始定义，默认设为false
     */
    private boolean ignoreInvalidClasses;

    @Override
    protected Morphia createInstance() throws Exception {
        Morphia m = new Morphia();
        if (mapPackages != null) {
            for (String packageName : mapPackages) {
                m.mapPackage(packageName, ignoreInvalidClasses);
            }
        }
        if (mapClasses != null) {
            for (String entityClass : mapClasses) {
                m.map(Class.forName(entityClass));
            }
        }

        m.getMapper().getConverters().addConverter(typeConverter);
        return m;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    public String[] getMapPackages() {
        return mapPackages;
    }

    public String[] getMapClasses() {
        return mapClasses;
    }

    public boolean isIgnoreInvalidClasses() {
        return ignoreInvalidClasses;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }

    public void setIgnoreInvalidClasses(boolean ignoreInvalidClasses) {
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }

    public TypeConverter getTypeConverter() {
        return typeConverter;
    }

    public void setTypeConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
}
