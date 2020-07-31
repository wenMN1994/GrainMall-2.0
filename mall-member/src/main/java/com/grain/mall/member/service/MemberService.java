package com.grain.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grain.common.utils.PageUtils;
import com.grain.mall.member.entity.MemberEntity;
import com.grain.mall.member.exception.MobileExistException;
import com.grain.mall.member.exception.UserNameExistException;
import com.grain.mall.member.vo.MemberRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:42:35
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 会员注册
     * @param vo
     */
    void register(MemberRegisterVo vo);

    /**
     * 检查用户名是否唯一
     * @param userName
     */
    void checkUserNameUnique(String userName) throws UserNameExistException;

    /**
     * 检查手机号是否唯一
     * @param mobile
     */
    void checkMobileUnique(String mobile) throws MobileExistException;
}

