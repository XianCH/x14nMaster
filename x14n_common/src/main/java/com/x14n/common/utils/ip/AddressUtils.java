package com.x14n.common.utils.ip;

import com.alibaba.fastjson.JSONObject;
import com.x14n.common.config.X14nConfig;
import com.x14n.common.constant.Constants;
import com.x14n.common.utils.StringUtils;
import com.x14n.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIp(String ip) {
        if (IpUtils.internalIp(ip)) {
            return "内网ip";
        }
        if (X14nConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtils.sendGet(IP_URL, "IP=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);

            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
