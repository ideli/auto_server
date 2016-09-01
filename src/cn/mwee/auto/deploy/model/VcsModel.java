package cn.mwee.auto.deploy.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
@Data
public class VcsModel {
    /**
     * 类型
     */
    private String type;
    /**
     * 仓库地址
     */
    private String repUrl;
    /**
     *  项目名称
     */
    private String projectName;
    /**
     * 分支名称
     */
    private List<String> brachesNames;

}
