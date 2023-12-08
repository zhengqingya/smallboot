package com.zhengqing.common.flowable.util;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.common.engine.impl.util.io.StringStreamSource;

import java.util.Collection;

/**
 * <p> flowable工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/8 17:20
 */
public class FlowableUtil {

    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    /**
     * bpmnXml转bpmnModel对象
     *
     * @param bpmnXml xml
     * @return bpmnModel对象
     * @author zhengqingya
     */
    public static BpmnModel getBpmnModel(String bpmnXml) {
        return bpmnXMLConverter.convertToBpmnModel(new StringStreamSource(bpmnXml), false, false);
    }

    /**
     * 获取开始节点
     *
     * @param bpmnModel bpmnModel对象
     * @return 开始节点（未找到开始节点，返回null）
     * @author zhengqingya
     */
    public static StartEvent getStartEvent(BpmnModel bpmnModel) {
        Process process = bpmnModel.getMainProcess();
        FlowElement startElement = process.getInitialFlowElement();
        if (startElement instanceof StartEvent) {
            return (StartEvent) startElement;
        }
        
        Collection<FlowElement> flowElementList = process.getFlowElements();
        for (FlowElement flowElement : flowElementList) {
            if (flowElement instanceof StartEvent) {
                return (StartEvent) flowElement;
            }
        }
        return null;
    }


}
