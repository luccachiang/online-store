package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** 处理用户相关请求的控制器类 */
@RestController // @controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 第一种注入方式
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据
     *   spring boot会将前端的url地址中的参数名和pojo类的属性名进行比较
     *   如果这两个名称相同，则将值注入到pojo类中对应的属性
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user)
    {
        // 调用业务对象执行注册
        userService.reg(user);
        // 返回
        return new JsonResult<Void>(OK);
    }

    /**
     * 第二种注入方式
     * 1.接收数据方式：请求处理方法的参数列表设置为非pojo类型（String，Integer基本类型 etc.）
     *   springboot会直接将请求的参数名和方法的参数名直接进行比较
     *   如果名称相同则自动完成值的依赖注入
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session)
    {
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);

        // 在登陆方法中将数据封装到session对象中(session对象是全局的，项目其他地方也可以访问)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        // 获取session中绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        // 将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,
                                           HttpSession session) {
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        // 从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行获取数据
        User data = userService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);
        // 响应成功
        return new JsonResult<Void>(OK);
    }

    /** 设置上传文件的最大值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 设置允许上传的头像类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();
    /** 初始化允许上传头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }


    /**
     * MultipartFile接口是springMVC提供的一个接口，这个接口为我们包装了获取文件类型的数据
     * （任何类型的file都可以接收），spring boot又整合了SpringMVC，
     * 只需要在处理请求的方法参数列表上声明一个参数类型为 MultipartFile 的参数
     * 然后spring boot自动将传递给服务的文件数据赋值给这个参数
     *
     * @RequestMapping 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上
     *                  如果名称不一致则可以使用@RequestMapping注解进行标记和映射
     *                  类似于@Param 和 pathVariable
     *
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                            @RequestParam("file") MultipartFile file){
        // 判断上传的文件是否为空
        // 是：抛出异常
        if (file.isEmpty()){
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        // 是：抛出异常
        if (file.getSize() > AVATAR_MAX_SIZE){//getsize返回文件的大小，以字节为单位
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) +
                    "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        // 是：抛出异常
        String contentType = file.getContentType();
        //public boolean list.contains(Object o):
        // 当前列表若包含某元素则返回true；若不包含该元素，返回结果为false
        if (!AVATAR_TYPES.contains(contentType)){
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：" +
                    "\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径 /upload/someFile.png
        String parent = session.getServletContext().getRealPath("upload");
        // 保存头像文件的文件夹，判断这个文件是否存在
        File dir = new File(parent);
        if (!dir.exists()){
            dir.mkdirs();
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename(); // e.g. avatar01.png
        int beginTndex = originalFilename.lastIndexOf(".");
        if (beginTndex > 0){
            suffix = originalFilename.substring(beginTndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;

        // 创建文件对象，表示保存的头像文件（空文件）
        File dest = new File(dir, filename);
        // 执行保存头像文件，写入空文件
        // 如果产生异常则抛出
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadException("上传文件发生异常，请稍后重新尝试");
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        }

        // 头像路径，相对路径
        String avatar = "/upload/" + filename;
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 将头像写入到数据库中
        userService.changeAvatar(uid, avatar, username);

        // 返回成功和头像路径
        return new JsonResult<String>(OK, avatar);
    }
}


///** 处理用户相关请求的控制器类 */
//@RestController // @controller + @ResponseBody
//@RequestMapping("users")
//public class UserController {
//    @Autowired
//    private IUserService userService;
//
//    @RequestMapping("reg")
//    public JsonResult<Void> reg(User user)
//    {
//        // 创建返回值
//        JsonResult<Void> result = new JsonResult<Void>();
//        try {
//            // 调用业务对象执行注册
//            userService.reg(user);
//            // 响应成功
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        } catch (UsernameDuplicateException e) {
//            // 用户名被占用
//            result.setState(4000);
//            result.setMessage("用户名已经被占用");
//        } catch (InsertException e) {
//            // 插入数据异常
//            result.setState(5000);
//            result.setMessage("注册失败，请联系系统管理员");
//        }
//        return result;
//    }
//
//}
