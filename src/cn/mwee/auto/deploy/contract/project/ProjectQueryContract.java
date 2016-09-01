package cn.mwee.auto.deploy.contract.project;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectQueryContract extends BaseContract {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创建日期-开始
     */
    private Date createTimeS;
    /**
     * 创建日期-结束
     */
    private Date createTimeE;
}
