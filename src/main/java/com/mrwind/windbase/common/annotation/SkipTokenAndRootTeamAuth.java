package com.mrwind.windbase.common.annotation;

import java.lang.annotation.*;

/**
 * 跳过 token 以及 rootTeam 校验
 *
 * @author hanjie
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipTokenAndRootTeamAuth {
}
