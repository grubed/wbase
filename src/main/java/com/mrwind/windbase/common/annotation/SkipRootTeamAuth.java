package com.mrwind.windbase.common.annotation;

import java.lang.annotation.*;

/**
 * 仅跳过 rootTeam 校验，但不跳过 token 校验
 *
 * @author hanjie
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipRootTeamAuth {
}
