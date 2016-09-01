package cn.mwee.auto.auth.model;

import lombok.Data;

/**
 * Created by Administrator on 2016/8/8.
 */
@Data
public class ZtreePerm {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean open;
    private boolean checked;
}
