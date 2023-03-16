package com.x14n.web.controller.common;


import com.google.code.kaptcha.Producer;
import com.x14n.common.config.X14nConfig;
import com.x14n.common.constant.Constants;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.redis.RedisCache;
import com.x14n.common.utils.sign.Base64;
import com.x14n.common.utils.uuid.IdUtils;
import com.x14n.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    @GetMapping("/captchaImage")
    public AjaxResult getCode() {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        ajax.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff) {
            return ajax;
        }

        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;
        String captchaType = X14nConfig.getCaptchaType();

        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey,code,Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid",uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
