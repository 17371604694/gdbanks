package com.jeeplus.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*
*
* 数据库链接加密处理工具类
* */
public class PassWordEncryptor {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor =new StandardPBEStringEncryptor();
        /*配置文件中配置如下的算法*/
        standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        /*配置文件中配置的password*/
        standardPBEStringEncryptor.setPassword("EWRREWRERWECCCXC");
        /*要加密的文本*/
        String name = standardPBEStringEncryptor.encrypt("root");
//        String password =standardPBEStringEncryptor.encrypt("WH@interWeb123");
        String password =standardPBEStringEncryptor.encrypt("MSQL@inter123");
        /*将加密的文本写到配置文件中*/
        System.out.println("name="+name);
        System.out.println("password="+password);
    }
}
