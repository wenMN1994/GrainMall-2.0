package com.grain.mall.member.service.impl;

import com.grain.mall.member.dao.MemberLevelDao;
import com.grain.mall.member.entity.MemberLevelEntity;
import com.grain.mall.member.exception.MobileExistException;
import com.grain.mall.member.exception.UserNameExistException;
import com.grain.mall.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        memberEntity.setPassword("");

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

}