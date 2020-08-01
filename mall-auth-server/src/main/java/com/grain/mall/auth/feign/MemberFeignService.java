package com.grain.mall.auth.feign;

import com.grain.common.vo.SocialUserVo;
import com.grain.common.utils.R;
import com.grain.mall.auth.vo.UserLoginVo;
import com.grain.mall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/7/31 23:02
 * @description：
 * @modified By：
 * @version: $
 */
@FeignClient("mall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/register")
    public R register(@RequestBody UserRegisterVo vo);

    @PostMapping("/member/member/login")
    public R login(@RequestBody UserLoginVo vo);

    @PostMapping("member/member/oauth/login")
    public R oauthLogin(@RequestBody SocialUserVo vo);
}
