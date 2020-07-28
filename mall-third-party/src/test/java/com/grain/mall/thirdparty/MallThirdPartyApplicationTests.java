package com.grain.mall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.grain.mall.thirdparty.component.SmsComponent;
import com.grain.mall.thirdparty.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallThirdPartyApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    OSSClient ossClient;

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testSendCode(){
        smsComponent.sendSmsCode("18475536452","413709");
    }

    @Test
    public void sendSms(){
        String host = "http://dingxintz.market.alicloudapi.com";
        String path = "/dx/notifySms";
        String method = "POST";
        String appcode = "你自己的AppCode";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "18475536452");
        querys.put("param", "code:413709");
        querys.put("tpl_id", "TP19121716");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpload() throws FileNotFoundException {

        // 上传文件流。
        InputStream inputStream = new FileInputStream("C:\\Users\\DragonWen\\Desktop\\小程序图标\\weichart.png");

        ossClient.putObject("grain-mall-dragon", "weichart.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传完成...");
    }

}
