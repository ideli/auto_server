package cn.mwee.auto.deploy.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by huming on 16/6/24.
 */
@Data
public class MwGroupData {

    private Byte preGroup;

    private List<Byte> concurrentGroups = Lists.newArrayList();

    private Byte postGroup;

    public void addConcurrentGroup(Byte group)
    {
        concurrentGroups.add(group);
    }

}
