package com.sdgtt.service.impl;

import com.sdgtt.model.auto.User;
import com.sdgtt.mapper.auto.UserMapper;
import com.sdgtt.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信审员 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
