package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/** 处理用户数据的业务层实现类 */
@Service//将当前类的对象交给spring来管理，自动创建对象和维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if(result != null)
        {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }

        // 创建当前时间对象
        Date now = new Date();

        // 补全数据：加密后的密码
        // 密码加密处理的实现：md5算法的形式：1roifjujasdfi-afdjhlj-ajflds-qrttg
        // (串 + password + 串) ---- md5算法进行加密，连续加载3次
        // 盐值 + password + ---- 盐值就是一个随机的字符串
        String oldpassword = user.getPassword();
        // 获取盐值，随机生成一个
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 将密码和盐值作为一个整体进行加密处理，忽略了原有密码的强度
        String md5Password = getMd5Password(oldpassword, salt);
        // 将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        // 补全数据：盐值
        user.setSalt(salt);

        // 补全数据：isDelete(0)
        user.setIsDelete(0);

        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);


        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1
        if(rows != 1)
        {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);

        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        if (result == null)
        {
            throw new UserNotFoundException("用户名不存在");
        }

        // 判断查询结果中的isDelete是否为1，表示已经删除用户
        // 是：抛出UserNotFoundException异常
        if (result.getIsDelete() == 1)
        {
            throw new UserNotFoundException("用户名不存在");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        // 是：抛出PasswordNotMatchException异常
        if (!result.getPassword().equals(md5Password))
        {
            throw new PasswordNotMatchException("用户密码错误");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        // 返回新的user对象
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldpassword, String newpassword) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 检查查询结果是否为null以及isDelete是否为1
        if (result == null || result.getIsDelete() == 1)
        {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 将参数oldPassword结合盐值加密，得到oldMd5Password
        String oldMd5Password = getMd5Password(oldpassword, salt);
        // 判断查询结果中的password与oldMd5Password是否不一致
        if(!oldMd5Password.contentEquals(result.getPassword()))
        {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("原始密码输入错误");
        }

        // 将参数newPassword结合盐值加密，得到newMd5Password
        String newMd5Password = getMd5Password(newpassword, salt);
        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updatePasswordByUid()更新密码，并获取返回值
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, now);
        // 判断以上返回的受影响行数是否不为1
        if(rows != 1)
        {
            // 是：抛了UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);

        // 判断查询结果是否为null或者isDelete是否为1
        if (result == null || result.getIsDelete() == 1)
        {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        // 返回新的User对象，直接返回result数据传输量太大（前端只展示部分数据）
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);

        // 判断查询结果是否为null或者isDelete是否为1
        if (result == null || result.getIsDelete() == 1)
        {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }
        // 向参数user中补全数据：uid
        user.setUid(result.getUid());
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());

        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1)
        {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知的错误！");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null或者isDelete是否为1
        if (result == null || result.getIsDelete().equals(1))
        {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar,
                                        username, new Date());
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1)
        {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户头像时出现未知的错误！");
        }
    }

    /** 定义一个md5算法的加密处理*/
    private String getMd5Password(String password, String salt)
    {
        /**
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for(int i = 0; i < 3; ++i)
        {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
