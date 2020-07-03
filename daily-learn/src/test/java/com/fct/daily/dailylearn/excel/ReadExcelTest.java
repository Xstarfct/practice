package com.fct.daily.dailylearn.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Joiner;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
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
    
    @Setter
    @Getter
    @Accessors(chain = true)
    public static class ExpressContentSalesMng implements Serializable {
        
        private static final long serialVersionUID = -1138768953988435344L;
        /**
         * 数据生成时间 2020-05-19 20:14:54
         */
        @JSONField(name = "created_at")
        private String createdAt;
        /**
         * 轨迹详情
         */
        private String context;
    }
    
    
    @Test
    public void testData() {
        String data = "{\"express_company\":\"ztoky\",\"express_company_name\":\"中通快运\",\"express_context\":[{\"created_at\":\"2020-06-28 19:13:53\",\"context\":\"拍照签收签收\"},{\"created_at\":\"2020-06-28 19:13:07\",\"context\":\"【丽水市】云和派件员：蓝晨阳 电话：18767871330 当前正在为您派件\"},{\"created_at\":\"2020-06-28 09:43:49\",\"context\":\"【丽水市】快件已到达 云和\"},{\"created_at\":\"2020-06-28 02:15:33\",\"context\":\"【金华>市】快件已从金华分拨中心发出，正在发往云和\"},{\"created_at\":\"2020-06-27 23:36:02\",\"context\":\"【金华市】快件已到达 金华分拨中心\"},{\"created_at\":\"2020-06-27 19:55:46\",\"context\":\"【金华市】快件已从后宅洪华发出，正在发往金华分拨中心\"},{\"created_at\":\"2020-06-27 19:55:29\",\"context\":\"【金华市】您的包裹已由 >物流公司揽收\"}],\"express_no\":\"300086852790\",\"out_trade_no\":\"20200456111\",\"status\":\"3\",\"timestamp\":\"1593432996215\"}";
        JSONObject jsonObject = JSON.parseObject(data);
        String outTradeNo = jsonObject.getString("out_trade_no");
        String expressNo = jsonObject.getString("express_no");
        List<ExpressContent> list = JSON.parseArray("[{\"time\":\"2020-06-28 19:13:53\",\"context\":\"拍照签收签收\"},{\"time\":\"2020-06-28 19:13:07\",\"context\":\"【丽水市】云和派件员：蓝晨阳 电话：18767871330 当前正在为您派件\"},{\"time\":\"2020-06-28 09:43:49\",\"context\":\"【丽水市】快件已到达 云和\"},{\"time\":\"2020-06-28 02:15:33\",\"context\":\"【金华>市】快件已从金华分拨中心发出，正在发往云和\"},{\"time\":\"2020-06-27 23:36:02\",\"context\":\"【金华市】快件已到达 金华分拨中心\"},{\"time\":\"2020-06-27 19:55:46\",\"context\":\"【金华市】快件已从后宅洪华发出，正在发往金华分拨中心\"},{\"time\":\"2020-06-27 19:55:29\",\"context\":\"【金华市】您的包裹已由 >物流公司揽收\"}]", ExpressContent.class);
        // 倒序
        list.sort(Comparator.comparing(ExpressContent::getTime).reversed());
    
        list.get(0).setStatus("签收");
        list.get(list.size() - 1).setStatus("在途");
    
        System.out.println("================================");
        System.out.println(outTradeNo + "  ---   " + expressNo);
        System.out.println(JSON.toJSONString(list));
        System.out.println("================================");
        String join = Joiner.on("-------").join(Arrays.asList("1", "2", "3"));
        System.out.println(join);
        System.out.println(Joiner.on("-------\n").join(list));
    }
}
