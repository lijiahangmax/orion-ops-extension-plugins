package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.lang.utils.collect.Lists;
import com.orion.lang.utils.collect.Maps;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsageBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 硬盘时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 10:51
 */
@Slf4j
@Component
public class DiskHourReduceResolver implements IMetricsHourReduceResolver<DiskIoUsageBO> {

    /**
     * 当前采集信息粒度时
     */
    private final Map<String, String> currentHours;

    /**
     * 当前采集信息
     */
    private final Map<String, List<DiskIoUsageBO>> currentMetrics;

    public DiskHourReduceResolver() {
        this.currentHours = Maps.newMap();
        this.currentMetrics = Maps.newMap();
    }

    @Override
    public void reduce(DiskIoUsageBO data) {
        String seq = data.getSeq();
        String currentHour = Utils.getRangeStartHour(data);
        String prevHour = currentHours.computeIfAbsent(seq, k -> currentHour);
        List<DiskIoUsageBO> list = currentMetrics.computeIfAbsent(seq, k -> Lists.newList());
        // 同一时间
        if (currentHour.equals(prevHour)) {
            list.add(data);
            return;
        }
        // 不同时间则规约
        currentHours.put(seq, currentHour);
        // 计算数据
        DiskIoUsageBO reduceData = new DiskIoUsageBO();
        reduceData.setRc(list.stream().mapToLong(DiskIoUsageBO::getRc).sum());
        reduceData.setRs(list.stream().mapToLong(DiskIoUsageBO::getRs).map(s -> s / Const.BUFFER_KB_1).sum());
        reduceData.setWc(list.stream().mapToLong(DiskIoUsageBO::getWc).sum());
        reduceData.setWs(list.stream().mapToLong(DiskIoUsageBO::getWs).map(s -> s / Const.BUFFER_KB_1).sum());
        reduceData.setUt(list.stream().mapToLong(DiskIoUsageBO::getUt).sum());
        Utils.setReduceHourRange(reduceData, prevHour, currentHour);
        log.debug("硬盘时级数据指标-seq: {} {}", seq, JSON.toJSONString(reduceData));
        list.clear();
        list.add(data);
        // 拼接到月级数据
        String path = PathBuilders.getDiskMonthDataPath(Utils.getRangeStartMonth(prevHour), seq);
        Utils.appendMetricsData(path, reduceData);
    }

}
