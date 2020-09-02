package com.grain.mall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.grain.mall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2016101600703624";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDr5UduCRwiSJhR52M0tJJYe52bvSiru+q00VCxN+Uq5LFmhGrEiIgxjvzop2HwUhZ7+Lz9ImySYPG0ESndRmhG7EcnHW50Tkwpjjna4m0ZflICcOemFCt9+FWXwKdND854RpcJSBlPAHggEvsmWn4F4Dif+CRWIVnI+cNKI9ioNCpJ3Lrew7pYIHFz9fWYvmuJSr7Ev8OHkJZzf2/SH+e+APtuAdwYCjaFvR1XrnkbtrZ/kI2hrv9hDKFsosO+xVQuVAKvcBdXg/lItfduuYPuIJtrdSTb+i0JlRixEQtFucz/HFY8lMebT3yJY+OheRNkarPeU8BgshjiZdBSAg1AgMBAAECggEALAtuNKKCjaFQfnkVmSRqcWDwAWzbJg6c2uYPJGX04iUA0lbV0gIeqBeltbeOOPnM2UgDANWpuJgeG0FNtTTVfa1lb8xlE+GG3165aa3uQQhaeF3LsCAB3C4skFb26N2wyAsiZe+FBlcryYsf2eL2LqSt1xQuaG7TL8wyW5RVYQct4uaNi54RbRAnQtkKsx7V/7FHsHsS86/zqMrZ8QeQCMn9GrdOFFz29qfxSS9xSXkqrrB02OdmK2BaRt+2M2FZnD1LUOWNZNW64xtpr26YvSauLimqhMv5pSSiaVH0KpY1ABSXT0PBCUOpvjZJfwHkpt4oy3XRdkCBGFU3sD6zgQKBgQC9H3r6JlVFOaf+jOLToN02GmDxXGy3Phq2S7MpC53i5nFi7YzvRY7DWYUkIaabwNsq+JsUtxHjKXaVSgHRps3/jykk2gfH+A/WiavoKUYKhXytgLS/Xc1wIgmTCVzAxEr80hrHdKQ0vdtHsLAMAAe2e3+pohYpcdIbSRX/yl6IUwKBgQCyQI8hG1eHCKX2exI7ARcJG1fCYbNSGrl21nAB+GnyHMDcXhurRxeGKSGar0XSus/+pOav0Vh0c457g4PpZOlKGv01HXQ3D1AYjageSygmJbWIcV2zIBHbx/LPY0HL87NBMw3dC/o25BDOPte2ggbrMhWPFF0z/tmDVH3eaHL8VwKBgBKPpGIooPXKUtKIwTpD73H/5MPLbmANmFGL3GmK2lQKidZLuilBimFhx89j/hYLN3dgLtg5n2fMCfdc7BDHbjBGvKMBQwI9NlC38kc2xckCkNn2Z8bgTelTbd0gbhn9Kd7mP/qTWApVF9BRZvXGivs946miXspygUxsP9x64AxBAoGAJwLYEE/SlRwAbnQ65izz5KtWRpVpUHPG2UpPEy9RyIogb1otRgv2CwefciIf7uGxubJPRnVTnZDYwxFiYkcSg4RqnCBJGe/BAM6n15FtRQQFucnNzvEa13XoHxlDAibguUk6JIGFjlPVlb3NKgVFDF5Wt4ymZUXGRubL8Uv8tlsCgYBw1ux6st1puI40rDNpfqHqG2D0i83X2T9ib+1CzH1HYZXHbU7UPU8khfrCW+aU8WJJ7e9L+3Nof+GglnaGSL4vpZ7DFnAQz/Pn07B5nyB45wx6YFKiaf6t/X4OY3VExNxDY4WJhgOj7uKqpRsoUAVGXR3lt2xKk6QMVMLEMMeBTw==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnpaveZZYl1bhjq/ocuSVaBzcyaVk8ErhKN5f9BNrSKmehM7DGDuUpCgkoq3L0hbttgEzBjNVbDKl+iirJZLGYPb9wk2UCfRJgogPDT5g3iZXCoFB6VPcKuf3tyqfJH5axbFODQRPWeoJ6WSiEdn4ASP5ZHFVdBFq/pUZP86RgIzQcgLI3jE4AoVWZsG1y7XjTa7awLTKmKUe+iMJDtZev6dZVvKn5Vpi8T5DAkj8H+k/wT+G5ra+e9nsacvBSvqd+Mr88R0I4VUtOXK1gLbCvVdjB2wO1KCRDqEulvdmWrpeZwlbNFsV0Wh72uuR89wyhjfRRB6HZ+t2XoI1P1IuSwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://atpkn8lavm.52http.tech/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.grainmall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
