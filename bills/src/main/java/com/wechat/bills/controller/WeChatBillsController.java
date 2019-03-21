package com.wechat.bills.controller;

import com.alibaba.fastjson.JSONObject;
import com.wechat.bills.entity.User;
import com.wechat.bills.service.ChangeDetailService;
import com.wechat.bills.service.UserService;
import com.wechat.bills.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;

/**
 * @Classname WeChatBillsController
 * @Description TODO
 * @Date 2019/3/21 17:29
 * @Created by elephone
 */
@Api(description="微信账单获取相关接口")
@RestController
public class WeChatBillsController {
    private Logger logger = LoggerFactory.getLogger(WeChatBillsController.class);


    @Autowired
    private ChangeDetailService changeDetailService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询微信号对应的key",notes = "在数据库里查询微信号最新的key")
    @PostMapping("/getLastKey")
    public R hasEffectiveKey(HttpServletRequest request, @RequestBody User user) {
        try {
            User u = userService.hasEffectiveKey(user);
            if(u != null){
                return R.okwithdata( JSONObject.toJSONString(u));
            }
            return R.error("查不到有效key");
        } catch (Exception e) {
            logger.error("获取微信key出错:{}", e);
            return R.error("获取微信key出错");
        }
    }

    @ApiOperation(value = "保存微信号所有的key",notes = "保存微信号所有的key到数据库")
    @PostMapping("/saveKeys")
    public R saveKeys(HttpServletRequest request, @RequestBody User user) {
        //验证数据
        Validator validator = new Validator();
        List<ConstraintViolation> valid = validator.validate(user);
        if(valid != null && valid.size() > 0){
            logger.info("请求参数异常:"+valid.get(0).getMessage());
            return R.error("请求参数异常:"+valid.get(0).getMessage());
        }
        try {
            int i = userService.saveUser(user);
            return R.okwithdata( JSONObject.toJSONString(user));
        } catch (Exception e) {
            logger.error("保存微信key出错:{}", e);
            return R.error("保存微信key出错");
        }
    }


    @ApiOperation(value = "保存账单到数据库并下载",notes = "保存账单到数据库并下载到本地")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name="wechatId",dataType = "string", required = true,value = "微信账号"),
            @ApiImplicitParam(paramType = "query", name="exportKey",dataType = "string", required = true,value = "导出key"),
            @ApiImplicitParam(paramType = "query", name="userrollEncryption",dataType = "string", required = true,value = "加密key"),
            @ApiImplicitParam(paramType = "query", name="userrollPassTicket",dataType = "string", required = true,value = "凭据key"),
            @ApiImplicitParam(paramType = "query", name="balanceuserrollEncryption",dataType = "string", required = true,value = "余额查询key")
    })
    @GetMapping("/saveBillsToExcel")
    public void saveAndDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam("wechatId") String wechatId,@RequestParam("exportKey") String exportKey,
                          @RequestParam(value = "userrollEncryption",required = false) String userrollEncryption,@RequestParam(value = "userrollPassTicket",required = false) String userrollPassTicket,
                          @RequestParam("balanceuserrollEncryption") String balanceuserrollEncryption) {
        try {
           User user = new User();
           user.setWechatId(wechatId);
           user.setExportKey(new String( Base64.getDecoder().decode(exportKey), "utf-8"));
           user.setUserrollEncryption(userrollEncryption);
           user.setUserrollPassTicket(userrollPassTicket);
           user.setBalanceuserrollEncryption(new String( Base64.getDecoder().decode(balanceuserrollEncryption), "utf-8"));
           Object repsonse = changeDetailService.saveBillsToDatabaseAndExcel(response,user);
           logger.info(repsonse.toString());
        } catch (Exception e) {
            logger.error("获取微信账单出错:{}", e);
        }
    }

//    @ApiOperation(value = "无效接口",notes = "这个接口没有使用，仅仅是为了验证ApiImplicitParam的类型为body时怎么传参")
//    @ApiImplicitParam(paramType = "body", name = "parameter",required = true,dataType = "body",
//            value = "参数列表,如:{\"uid\":\"564949\",\"startTime\":\"2015-01-01 00:00:00\",\"endTime\":\"2018-12-30 11:59:59\"," +
//                    "\"pageNo\":\"1\",\"pageSize\":\"10\"}")
//    @PostMapping(value = "/orderHistory")
//    public R orderHistory(HttpServletRequest request,@RequestBody Map<String,Object> parameter) {
//        try {
//            return R.ok("这个接口没有使用，仅仅是为了验证ApiImplicitParam的类型为body时怎么传参");
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//            return R.error("内部异常");
//        }
//
//    }

}
