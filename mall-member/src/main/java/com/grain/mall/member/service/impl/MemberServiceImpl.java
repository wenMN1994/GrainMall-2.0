package com.grain.mall.member.service.impl;

import com.grain.mall.member.dao.MemberLevelDao;
import com.grain.mall.member.entity.MemberLevelEntity;
import com.grain.mall.member.exception.MemberNotExistException;
import com.grain.mall.member.exception.MobileExistException;
import com.grain.mall.member.exception.PasswordErrorException;
import com.grain.mall.member.exception.UserNameExistException;
import com.grain.mall.member.vo.MemberLoginVo;
import com.grain.mall.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grain.common.utils.PageUtils;
import com.grain.common.utils.Query;

import com.grain.mall.member.dao.MemberDao;
import com.grain.mall.member.entity.MemberEntity;
import com.grain.mall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo vo) {
        MemberEntity memberEntity = new MemberEntity();

        // 设置默认等级
        MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(memberLevelEntity.getId());

        // 检查用户名和手机号是否唯一
        checkMobileUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserNum());

        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUserNum());

        // 密码要进行加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberEntity.setPassword(passwordEncoder.encode(vo.getPassword()));

        memberEntity.setCreateTime(new Date());

        this.baseMapper.insert(memberEntity);
    }

    @Override
    public void checkUserNameUnique(String userName) throws UserNameExistException {

        Integer count = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if(count > 0){
            throw new UserNameExistException();
        }
    }

    @Override
    public void checkMobileUnique(String mobile) throws MobileExistException {

        Integer count = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", mobile));
        if(count > 0){
            throw new MobileExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) throws MemberNotExistException {

        // 去数据库查询 SELECT * FROM `ums_member` WHERE username=? OR mobile=?
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", vo.getLoginAccount())
                .or()
                .eq("mobile", vo.getLoginAccount()));

        if(memberEntity == null){
            // 用户不存在，登录失败
            throw new MemberNotExistException();
        } else {
            return cryptographicCheck(vo, memberEntity);
        }
    }

    @Override
    public MemberEntity cryptographicCheck(MemberLoginVo vo, MemberEntity memberEntity) throws PasswordErrorException {
        // 获取到数据库的password
        String passwordDb = memberEntity.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 密码匹配
        boolean matches = passwordEncoder.matches(vo.getPassword(), passwordDb);
        if(matches){
            return memberEntity;
        }else {
            // 密码错误，登录失败
            throw new PasswordErrorException();
        }
    }

}