package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.SmsDTO;
import com.mrwind.windbase.dto.SmsMultiLanguageDTO;
import com.mrwind.windbase.dto.VoiceDTO;
import com.mrwind.windbase.service.SmsService;
import com.mrwind.windbase.service.VoiceService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 短信服务
 *
 * @author hanjie
 */

@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Resource
    private SmsService smsService;

    @Resource
    private VoiceService voiceService;

    /**
     * 发送短信
     */
    @PostMapping(value = "/send")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result sendSms(@RequestBody @Valid SmsDTO body, BindingResult bindingResult) {
        smsService.sendSms(body.getTel(), body.getMessage());
        return Result.getSuccess();
    }

    /**
     * 发送短信
     */
    @PostMapping(value = "/sendbytoken")
    public Result sendSmsByToken(@RequestBody @Valid SmsDTO body, BindingResult bindingResult) {
        smsService.sendSms(body.getTel(), body.getMessage());
        return Result.getSuccess();
    }

    /**
     * 发送短信 (支持多语言)
     */
    @PostMapping(value = "/sendmultilanguage")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result sendSms(@RequestBody @Valid SmsMultiLanguageDTO body, BindingResult bindingResult) {
        smsService.sendSms(body.getTel(), body.getMessageMap());
        return Result.getSuccess();
    }

    @SkipTokenAndRootTeamAuth
    @PostMapping(value = "/voice")
    public Result test(@RequestBody @Valid VoiceDTO voiceDTO) {
        voiceService.sendVoice(voiceDTO.getMobileList(),
                voiceDTO.getContent());

        return Result.getSuccess();
    }


}
