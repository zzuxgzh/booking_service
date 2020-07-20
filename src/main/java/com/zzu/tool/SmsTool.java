package com.zzu.tool;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;
public class SmsTool {

    /**
     * @侯国焱
     *
     * 生成一个随机数字以便发送验证码
     *
     * @return boolean
     */
    public static String getCode(){

        String code = "";
        //生成6位随机数
        for (int i=0;i<6;i++){
            code = code + String.valueOf(new Random().nextInt(10));
        }
        return code;
    }

    /**
     * @侯国焱
     *
     * 向一个手机号发送验证码，实验模板
     *
     * @return boolean
     */
    public static boolean sendSms(String mobile,String code){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIVfYpUWqSpUj3", "zj5VqjMmDPZyanW2PhlpjIlgXG3Acx");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "个人内容");
        request.putQueryParameter("TemplateCode", "SMS_151991439");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
