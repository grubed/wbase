package com.mrwind.windbase.common.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyiming
 * Created by wuyiming on 2017/12/15.
 */
@Configuration
@ConfigurationProperties(prefix = "mrwind.mongo")
public class SpringMongoConfig {

    private String[] host;

    private String user;

    private String pwd;

    private String authDb;

    private String dbWindBase;

    private String dbAttence;

    private String dbFengChat;

    @Bean("mongoClient")
    public MongoFactoryBean mongoClient() {
        MongoFactoryBean factoryBean = new MongoFactoryBean();
        factoryBean.setServerStrings(host);
        factoryBean.setAuthDb(authDb);
        factoryBean.setUsername(user);
        factoryBean.setPassword(pwd);

        return factoryBean;
    }

    @Bean
    public MorphiaFactoryBean morphiaFactoryBean() {
        MorphiaFactoryBean morphiaFactoryBean = new MorphiaFactoryBean();
        String[] arrays = new String[1];
        arrays[0] = "com.mrwind.windbase.entity.mongo";

        morphiaFactoryBean.setMapPackages(arrays);
        morphiaFactoryBean.setTypeConverter(new BigDecimalConverter());

        return morphiaFactoryBean;
    }

    @Bean("windbase")
    public DatastoreFactoryBean windbaseBean(@Autowired MongoFactoryBean mongoFactoryBean,
                                             @Autowired MorphiaFactoryBean morphiaFactoryBean) {
        return getDatastoreFactoryBean(mongoFactoryBean, morphiaFactoryBean, dbWindBase, true);
    }

//    @Bean("attence")
//    public DatastoreFactoryBean attenceBean(@Autowired MongoFactoryBean mongoFactoryBean,
//                                            @Autowired MorphiaFactoryBean morphiaFactoryBean) {
//        return getDatastoreFactoryBean(mongoFactoryBean, morphiaFactoryBean, dbAttence, false);
//    }
//
//    @Bean("fengChat")
//    public DatastoreFactoryBean fengChatBean(@Autowired MongoFactoryBean mongoFactoryBean,
//                                            @Autowired MorphiaFactoryBean morphiaFactoryBean) {
//        return getDatastoreFactoryBean(mongoFactoryBean, morphiaFactoryBean, dbFengChat, false);
//    }

    private DatastoreFactoryBean getDatastoreFactoryBean(@Autowired MongoFactoryBean mongoFactoryBean,
                                                         @Autowired MorphiaFactoryBean morphiaFactoryBean,
                                                         String dbWindBase, Boolean index) {
        DatastoreFactoryBean factoryBean = new DatastoreFactoryBean();
        try {
            factoryBean.setMorphia(morphiaFactoryBean.getObject());
            factoryBean.setMongo(mongoFactoryBean.getObject());
            factoryBean.setToEnsureIndexes(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        factoryBean.setDbName(dbWindBase);
        factoryBean.setToEnsureCaps(false);

        return factoryBean;
    }

    public void setHost(String[] host) {
        this.host = host;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAuthDb(String authDb) {
        this.authDb = authDb;
    }

    public String getDbWindBase() {
        return dbWindBase;
    }

    public void setDbWindBase(String dbWindBase) {
        this.dbWindBase = dbWindBase;
    }

    public String getDbAttence() {
        return dbAttence;
    }

    public void setDbAttence(String dbAttence) {
        this.dbAttence = dbAttence;
    }

    public String getDbFengChat() {
        return dbFengChat;
    }

    public void setDbFengChat(String dbFengChat) {
        this.dbFengChat = dbFengChat;
    }

}
