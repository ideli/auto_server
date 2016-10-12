/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.flow;

import java.util.Map;

import javax.validation.constraints.NotNull;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import cn.mwee.auto.deploy.model.FlowStrategy;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:27:18
 */
@Data
public class FlowAddContract extends BaseContract {
	/**
     * 名称
     */
	@NotBlank(message="流程名称不能为空")
	private String name;
	/**
     * 模板
     */
	@NotNull(message="未指定模板")
	private Integer templateId;
    /**
     * 项目Id
     */
    @NotNull(message="未指定项目Id")
	private Integer projectId;

    private Byte type;

    /**
     * 父流程Id
     */
    private Integer pid;

	/**
	 * 流程步骤
	 */
	private Byte flowStep;

	/**
	 * 步骤
	 */
	private Byte step;

    /**
     * 当前步骤
     */
    private Integer stepState;

	/**
     *  区域
     */
	private String zones;

    /**
     * 版本分支
     */
    private String vcsBranch;

    /**
     * 是否需要构建
     */
    private Byte needBuild;

    /**
     * 是否立即执行（0，否；1，是）
     */
    private byte exeNow;

	/**
     *  执行参数
     */
	private Map<String, Object> params;

	/**
	 * 流程策略-区域组大小
	 */
	private Integer strategyZoneSize;

	/**
	 * 流程策略-延迟时间
	 */
	private Integer strategyInterval;
}
