package com.grain.mall.member.dao;

import com.grain.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Dragon Wen
 * @email 18475536452@163.com
 * @date 2020-06-02 17:42:35
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
