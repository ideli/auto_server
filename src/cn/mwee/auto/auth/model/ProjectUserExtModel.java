package cn.mwee.auto.auth.model;

import lombok.Data;

/**
 * Created by Administrator on 2016/8/15.
 */
@Data
public class ProjectUserExtModel extends AuthUser {
    private Integer id;
    private Integer userId;
    private String userType;
}
