package com.hss.realm;

import javax.annotation.Resource;

import com.hss.entity.Blogger;
import com.hss.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 自定义realm
 * 为当前登录的用户授予角色和权限
 * @author hu
 *
 */

public class MyRealm extends AuthorizingRealm{

	@Resource
	private BloggerService bloggerService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		String userName=(String)token.getPrincipal();
		Map map=new HashMap();
		map.put("userName",userName);
		//通过用户名获取实体
		Blogger blogger=bloggerService.find(map);
		if(blogger!=null)
		{
			//把当前用户传入session,身份认证没有通过相当于设置的session无效.
			SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger);
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),"XXX");
			return authenticationInfo;
		}
		else{
			return null;
		}
	}
}
