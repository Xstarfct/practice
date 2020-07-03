package com.fct.daily.dailylearn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * RegularTest 正则相关
 *
 * @author xstarfct
 * @version 2020-06-19 2:30 下午
 */
@Slf4j
public class RegularTest {
    
    /**
     * 1、纯数字或仅包含连续重复字符的密码即为弱口令，如123456，aaaaaa等 2、数字和字母的组合 3、其他
     */
    private static final String regular = "[{\"level\":1,\"regular\":\"^([A-Za-z])\\\\1+$|(^[0-9]+$)\"},{\"level\":2,\"regular\":\"^(?!(^[0-9]+$)|^([A-Za-z])\\\\1+$)[a-zA-Z\\\\d]+$\"}]";
    
    /**
     * 根据明文密码来确定密码强度
     * <pre> 默认的json串 ^(.)\1+$ ^([A-Za-z])\1+$
     *     [{"level":1,"regular":"(^[0-9]+$)|^([A-Za-z])\\1+$"},{"level":2,"regular":"^(?!(^[0-9]+$)|^([A-Za-z])\\1+$)[a-zA-Z\\d]+$"}]
     * </pre>
     *
     * @param password 明文密码
     * @return 密码轻度
     */
    public static Integer computePasswordLevel(String password) {
        JSONArray regularArray = JSON.parseArray(regular);
        if (regularArray.size() < 1) {
            return 1;
        }
        for (int i = 0; i < regularArray.size(); i++) {
            JSONObject regular = regularArray.getJSONObject(i);
            String regex = regular.getString("regular");
            if (Pattern.matches(regex, password)) {
                return regular.getInteger("level");
            }
        }
        return 3;
    }
    
    @Test
    public void testRegular() {
        log.info("regular = {}", regular);
        log.info("11111111 = {}", computePasswordLevel("11111111"));
        log.info("aaaaaaa = {}", computePasswordLevel("aaaaaaa"));
        log.info("hkdahkjhd = {}", computePasswordLevel("hkdahkjhd"));
        log.info("123456 = {}", computePasswordLevel("123456"));
        log.info("123456hjdkahksjd = {}", computePasswordLevel("123456hjdkahksjd"));
        log.info("123456hjdkahks123434jsdd8874da3 = {}", computePasswordLevel("123456hjdkahks123434jsdd8874da3"));
        log.info("jhdkahsjd*&%sgjda! = {}", computePasswordLevel("jhdkahsjd*&%sgjda!"));
    }
    
    static Optional<String> getOutputOpt(String input) {
        return input == null ? Optional.empty() : Optional.of("output for " + input);
    }
    
    static String getOutput(String input) {
        return input == null ? null : "output for " + input;
    }
    
    @Test
    public void name() {
        
        Optional<String> test = Optional.of("String java is a beautiful language");
        log.info("{}", test.map(RegularTest::getOutput).filter(v -> v.contains("is")));
        log.info("{}", test.flatMap(RegularTest::getOutputOpt));
        
    }
    
    @Test
    public void name2() {
        List<PwdRegular> list = Lists.newArrayList();
        
        list.add(new PwdRegular().setLevel(1).setRegular("(^[0-9]+$)|(^[A-Za-z]+$)"));
        list.add(new PwdRegular().setLevel(2).setRegular("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$"));
        
        log.info("{}", JSON.toJSONString(list));
    }
    
    
    @Data
    @Accessors(chain = true)
    static class PwdRegular {
        
        private Integer level;
        private String regular;
        private String remark;
    }
    
    /**
     * ^[A-Za-z]\1+$ [{"level":1,"regular":"(^[0-9]+$)|^([A-Za-z])\\1+$"},{"level":2,"regular":"^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+$"}]
     * <p>
     * ([A-Zs-z])\1+[^\1]|^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$
     * <p>
     * ([A-Za-z])\\1+[^A]
     */
    @Test
    public void test() {
//        String regular2 = "^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+$";
//
//        log.info("adakda2sada  = {}",Pattern.matches(regular2, "adakda2"));
        
        String reg = "^(?!^[0-9]+$|^[A-Za-z]\\\\1+$)[a-zA-Z\\\\d]+$";
        
        log.info("adakda  = {}", Pattern.matches(reg, "adakda"));
        log.info("AAAAA  = {}", Pattern.matches(reg, "AAAAA"));
    }
    
    public static Integer compute2PasswordLevel(String password) throws Exception{
        String text = "[{\"level\":1,\"regular\":\"^([A-Za-z])\\\\1+$|(^[0-9]+$)\"},{\"level\":2,\"regular\":\"^(?!(^[0-9]+$)|^([A-Za-z])\\\\1+$)[a-zA-Z0-9]+$\"}]";
        org.json.JSONArray array = new org.json.JSONArray(text);
        if (array.length() < 1) {
            return 1;
        }
        for (int i = 0; i < array.length(); i++) {
            org.json.JSONObject object = array.getJSONObject(i);
            String regex = object.getString("regular");
            if (Pattern.matches(regex, password)) {
                return object.getInt("level");
            }
        }
        return 3;
    }
    
    @Test
    public void jackson() throws Exception {
        log.info("11111111 = {}", compute2PasswordLevel("11111111"));
        log.info("aaaaaaa = {}", compute2PasswordLevel("aaaaaaa"));
        log.info("hkdahkjhd = {}", compute2PasswordLevel("hkdahkjhd"));
        log.info("123456 = {}", compute2PasswordLevel("123456"));
        log.info("123456hjdkahksjd = {}", compute2PasswordLevel("123456hjdkahksjd"));
        log.info("123456hjdkahks123434jsdd8874da3 = {}", compute2PasswordLevel("123456hjdkahks123434jsdd8874da3"));
        log.info("jhdkahsjd*&%sgjda! = {}", compute2PasswordLevel("jhdkahsjd*&%sgjda!"));
    }
    
    
    
    
}
