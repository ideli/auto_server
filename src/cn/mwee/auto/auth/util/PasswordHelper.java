/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author mengfanyuan
 * 2016年6月30日上午11:20:12
 */
public class PasswordHelper {
    /**
     * 盐值生成器
     */
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    /**
     * 加密方式
     */
    private String algorithmName = "md5";
    /**
     * 加密次数
     */
    private int hashIterations = 2;

    /**
     * 用户密码加密
     * @param password 密码
     */
    public Map<String, String> encryptPassword(String password) {
        Map<String, String> returnMap = new HashMap<>();
        String salt = randomNumberGenerator.nextBytes().toHex();
        returnMap.put("salt", salt);
        String newPassword = encryptPassword(salt,password);
        returnMap.put("password", newPassword);
        return returnMap;
    }

    /**
     * 检查密码
     * @param salt 密码盐值
     * @param password 明文密码
     * @param encryptPassword 密码密文
     * @return
     */
    public boolean checkPassword(String salt, String password, String encryptPassword) {
        if (StringUtils.isBlank(encryptPassword)) return false;
        return encryptPassword.equals(encryptPassword(salt, password));

    }

    /**
     * 密码加密
     * @param salt 盐值
     * @param password 密码明文
     * @return
     */
    public String encryptPassword(String salt, String password) {
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }


    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public static void main(String[] args) {
        System.out.println(new PasswordHelper().encryptPassword("58e560e74d44896f4f995c605b0fc72b","paidui"));
//        new PasswordHelper().encryptPassword("0eb53519f55763f8391a84db0489e11f","123456");
    }
}
