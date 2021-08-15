/*
 * @author yjiewei
 * @date 2021/8/14 19:10
 */
package com.yjiewei.action;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Setter
public class UserAction {

    private String username;

    /**
     * 使用ajax来访问 用户名是否重复
     * @return
     */
    public String checkUsername()throws IOException {

        HttpServletResponse response = ServletActionContext.getResponse();
        // response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        if ("zhangsan".equals(username)) {
            response.getWriter().print("用户名 is ok");
        }else {
            response.getWriter().print("用户名 not good error");
        }
        return null; // 1.绝对返回null
        // return Action.NONE;

    }
}
