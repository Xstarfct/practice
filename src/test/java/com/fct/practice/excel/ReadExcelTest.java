package com.fct.practice.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

/**
 * 修复物流数据
 *
 * @author xstarfct
 * @version 2020-06-01 3:09 下午
 */
public class ReadExcelTest {
    
    @Test
    public void test1() {
        String fileName = "/Users/xstarfct/Desktop/document/test.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }
    
    public static class DemoDataListener extends AnalysisEventListener<DemoData> {
    
        @Override
        public void invoke(DemoData demoData, AnalysisContext analysisContext) {
            List<ExpressContent> list = JSON.parseArray(demoData.getLogisticsTrace(), ExpressContent.class);
            // 倒序
            list.sort(Comparator.comparing(ExpressContent::getTime).reversed());
            
            list.get(0).setStatus("签收");
            list.get(list.size() - 1).setStatus("在途");
            System.out.println("update de_delivery_logistics set logistics_trace = '" + JSON.toJSONString(list) + "' where id = " + demoData.getId() + ";");
        }
    
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        
        }
    }
    
    @Setter
    @Getter
    @ToString
    private static class ExpressContent implements Serializable {
        private static final long serialVersionUID = -2519642277398414824L;
        private String context;
        private String status;
        private String time;
    }
    
    
}
