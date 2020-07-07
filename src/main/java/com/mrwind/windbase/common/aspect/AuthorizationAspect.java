package com.mrwind.windbase.common.aspect;

import com.mrwind.windbase.common.annotation.SkipRootTeamAuth;
import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.constant.WindAuthKeyEnum;
import com.mrwind.windbase.common.util.JwtUtil;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dao.mysql.TeamRepository;
import com.mrwind.windbase.dao.mysql.UserRepository;
import com.mrwind.windbase.dao.mysql.UserTokenRepository;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.entity.mysql.UserToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 校验切面
 *
 * @author hanjie
 */
@Component
@Aspect
@Order(1)
public class AuthorizationAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserTokenRepository userTokenRepository;

    @Resource
    private TeamRepository teamRepository;

    @Pointcut("execution(public * com.mrwind.windbase.controller..*.*(..))")
    public void requestPointCut() {

    }

    @Around("requestPointCut()")
    public Object doAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean skipTokenAndRootTeamAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SkipTokenAndRootTeamAuth.class) != null;
        boolean skipRootTeamAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SkipRootTeamAuth.class) != null;
        boolean isFromWindAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(WindAuthorization.class) != null;
        HttpServletRequest request = ServletUtil.getCurrentRequest();
        // 默认接口的调用方为当前项目 WIND_BASE
        request.setAttribute(CommonConstants.AUTH_FROM, WindAuthKeyEnum.WIND_BASE);
        if (!skipTokenAndRootTeamAuth) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            try {
                String[] userIdAndTel = JwtUtil.parseJWT(token).getSubject().split("_");
                String userId = userIdAndTel[0];
                String tel = userIdAndTel[1];
                // 首先校验当前 token
                UserToken currentUserToken = userTokenRepository.findByUserId(userId);
                if (currentUserToken == null || !TextUtils.equals(currentUserToken.getCurrentToken(), token)) {
                    throw new RuntimeException();
                }
                User user = userRepository.findByUserIdAndTel(userId, tel);
                if (user != null) {
                    if (!skipRootTeamAuth) {
                        if (!TextUtils.isEmpty(user.getCurrentTeamId())) {
                            Team root = teamRepository.findByTeamId(user.getCurrentTeamId());
                            if (root != null) {
                                request.setAttribute(CommonConstants.USER_ROOT_TEAM, root);
                            }
//                            else {
//                                return Result.getFailI18N("error.no.current.team");
//                            }
                        }
//                        else {
//                            return Result.getFailI18N("error.no.current.team");
//                        }
                    }
                    // 把 user 对象放在 request 中
                    request.setAttribute(CommonConstants.USER_KEY, user);
                } else {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                // token 无效
                HttpServletResponse response = ServletUtil.getCurrentResponse();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return Result.getFailI18N("error.token.auth");
            }
        }
        if (isFromWindAuth) {
            String key = request.getHeader("key");
            WindAuthKeyEnum from = WindAuthKeyEnum.get(key);
            if (from == null) {
                // 第三方携带的 key 无效
                return Result.getFailI18N("error.permission.denied");
            } else {
                // 把来源值放在 request 中
                request.setAttribute(CommonConstants.AUTH_FROM, from);
            }
        }
        return joinPoint.proceed();
    }

}
