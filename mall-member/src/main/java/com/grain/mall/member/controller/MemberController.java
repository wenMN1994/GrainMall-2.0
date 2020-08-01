package com.grain.mall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.grain.common.exception.BizCodeEnum;
import com.grain.common.to.login.SocialUserVo;
import com.grain.mall.member.exception.MemberNotExistException;
import com.grain.mall.member.exception.MobileExistException;
import com.grain.mall.member.exception.PasswordErrorException;
import com.grain.mall.member.exception.UserNameExistException;
import com.grain.mall.member.feign.CouponFeignService;
import com.grain.mall.member.vo.MemberLoginVo;
import com.grain.mall.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.grain.mall.member.entity.MemberEntity;
import com.grain.mall.member.service.MemberService;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.R;



/**
 * 会员
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:42:35
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @RequestMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R memberCoupon = couponFeignService.memberCoupon();
        return R.ok().put("member", memberEntity).put("coupons",memberCoupon.get("coupons"));
    }

    @PostMapping("/oauth/login")
    public R oauthLogin(@RequestBody SocialUserVo vo) throws Exception {
        MemberEntity entity = memberService.login(vo);
        if(entity != null){
            return R.ok().setData(entity);
        }else {
            return R.error(BizCodeEnum.OAUTH2_LOGIN_EXCEPTION.getCode(), BizCodeEnum.OAUTH2_LOGIN_EXCEPTION.getMsg());
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){

        MemberEntity memberEntity = null;
        try {
            memberEntity = memberService.login(vo);
        } catch (MemberNotExistException e) {
            return R.error(BizCodeEnum.USER_NOT_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_NOT_EXIST_EXCEPTION.getMsg());
        } catch (PasswordErrorException e) {
            return R.error(BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getCode(), BizCodeEnum.PASSWORD_ERROR_EXCEPTION.getMsg());
        }

        return R.ok().setData(memberEntity);
    }

    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo vo){

        try {
            memberService.register(vo);
        } catch (MobileExistException e) {
            return R.error(BizCodeEnum.MOBILE_EXIST_EXCEPTION.getCode(), BizCodeEnum.MOBILE_EXIST_EXCEPTION.getMsg());
        } catch (UserNameExistException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
