package com.wechat.bills;


import com.wechat.bills.config.SystemConfig;
import com.wechat.bills.utils.HttpUtils;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class JunitTest {

    @Test
    public  void testBase64(){
        try {
            String asB64 = Base64.getEncoder().encodeToString("L/qUWbOpXtbcW125jyWBK1V+/uaewC4gnSnKgJrxpWmsZdnlvsO0AuvXleWN+hIHn76xuRJsjgT2IGM3MGET3OWRP3yD/YM9CZFeCaJ/Pr66jxR47W846oQt+ei9UmI4zsq72gEorKtHAd/cb/Vr+Q==".getBytes("utf-8"));
            System.out.println("base64加密后："+asB64);

            byte[] asBytes = Base64.getDecoder().decode(asB64);
            System.out.println("解密后："+new String(asBytes, "utf-8"));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Test
    public  void testBills(){
        try {
            String exportkeyPlace = "AxWki9RrHie1r1M9visFD5k%3D";
            String userroll_encryption ="lGueKRYYvv5hN8G4LgfUWatMjoj7in4BqpxCteKbrjkNO3oPfZxBoxEnl8FuQVof3hqVxn5hbRdI10/kEj7MvyO+FGtJmuIow/XGcvQF78NXuttlmh9LI6y6Y79n94QLg5erlgnW9HKFiJgeOas3tg==";
            String userroll_pass_ticket ="SB7XtGPA+7Ebn5acW87eXXzkPEwN9kpD+AGC/GXc9kc34EQ3Q8lP5fSrz80PyjR2";

            String url = "https://wx.tenpay.com/userroll/userrolllist?classify_type=0&count=50&exportkey="+exportkeyPlace+"&sort_type=1";
            String cookie_encryption= "userroll_encryption="+userroll_encryption+"; Domain=wx.tenpay.com; Path=/; Expires=Mon, 12-Mar-2019 11:49:52 GMT; Secure; HttpOnly";
            String cookie_pass_ticket="userroll_pass_ticket="+userroll_pass_ticket+"; Path=/; Expires=Mon, 12-Mar-2019 11:49:52 GMT; Secure; HttpOnly";

            String response = HttpUtils.executeGet(HttpUtils.createHttpClient(), url, null, cookie_encryption+";"+cookie_pass_ticket, "utf-8", true);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getApplicationContext(){

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SystemConfig.class);
            SystemConfig config = context.getBean(SystemConfig.class);
            config.outSource();



    }

    
    
    
     @Test
    public  void testTopBuzz(){
        try {
        	
        	
        String url = "https://game.deshangkeji.com/Api/Index/Info";
        String param = "{\n" + 
        		"    \"userId\": \"61e2218932abdcf2cc903c7a3653e95b\",\n" + 
        		"    \"gameId\": \"88\",\n" + 
        		"    \"merchantId\": \"1234\",\n" + 
        		"    \"sign\": \"AD24DAAC7EE3FC24170683934F538282\",\n" + 
        		"    \"timestamp\": \"1562553169405\"\n" + 
        		"}";
        //String response =  HttpUtils.executePost(url, param);
        
    
       
       String response = postParamJson(param,url,MediaType.APPLICATION_JSON);
           
          System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public String postParamJson(String param,String url,MediaType paramType){
    	try {
    		RestTemplate tempplate = new RestTemplate();
    		HttpHeaders headers = new HttpHeaders();
    		
    		headers.setContentType(paramType);
    		HttpEntity<String> httpEntity = new HttpEntity<String>(param,headers);
    		tempplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    		
    		
    		ResponseEntity<String> postEntity = tempplate.postForEntity(url, httpEntity, String.class);
    		
    		return postEntity.getBody();
    	}catch(Exception e){
    		 System.out.println(e);
    		 return "";
    	}
    }
}
