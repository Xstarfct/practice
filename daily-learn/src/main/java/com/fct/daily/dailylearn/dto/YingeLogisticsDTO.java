package com.fct.daily.dailylearn.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 印鸽回调我们的参数列表
 *
 * @author xstarfct
 * @version 2020-05-19 9:44 上午
 */
@Setter
@Getter
@Accessors(chain = true)
public class YingeLogisticsDTO implements Serializable {
    
    private static final long serialVersionUID = 8932906831437181622L;
    /**
     * 唯一订单号	kkl2019221223124124 对应我们的发货单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 状态	1下单成功2已发货3已签收
     */
    private Integer status;
    /**
     * 物流单号
     */
    @JSONField(name = "express_no")
    private String expressNo;
    /**
     * 物流公司代号
     */
    @JSONField(name = "express_company")
    private String expressCompany;
    /**
     * 物流信息
     */
    @JSONField(name = "express_context")
    private List<ExpressContent> expressContent;
    
    @Setter
    @Getter
    @Accessors(chain = true)
    public static class ExpressContent implements Serializable {
        
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
    
    private static void buildTraceUpdateRecord(YingeLogisticsDTO record) {
        List<ExpressContent> content = record.getExpressContent();
        if (CollectionUtils.isNotEmpty(content)) {
            // 按照创建时间倒序排列
            content.sort(Comparator.comparing(ExpressContent::getCreatedAt).reversed());
            List<Map<String, String>> tempContentList = new ArrayList<>();
            // 需要转换成快递100的详情，在详情页面的时候就不用转换了 这里同构 其他的地方就不用转换了
            IntStream.range(0, content.size()).forEach(index -> {
                ExpressContent info = content.get(index);
                Map<String, String> temp = new HashMap<>(3);
                temp.put("time", info.getCreatedAt());
                temp.put("context", info.getContext());
                //[这里兼容成快递100的逻辑]列表是时间倒序的，所以这里的逻辑 第一个状态都是YingeLogisticsDTO.status对应的状态 ，其他的都是在途中，
                temp.put("status", "在途");
                tempContentList.add(temp);
            });
//            System.out.println("update de_delivery_logistics set logistics_status = 2, logistics_trace = '" + JSON.toJSONString(tempContentList) + "' where logistics_no = '" + record
//                    .getExpressNo() + "' and logistics_type = '2';");
//            System.out.println("update od_order_package set logistics_status = 2 where delivery_no = '"+ record.getOutTradeNo()+"' and logistics_no = '" + record.getExpressNo() + "';\n");
    
            System.out.println("");
        }
    }
    
    /**
     * select * from od_order_delivery where delivery_no in ('XHXFH158953874045175310809','XHXFH159092074643994804186','XHXFH158954903961624129796','XHXFH158980936380727828107','XHXFH159021248006527257861','XHXFH158964140008198698162','XHXFH158955011122606509150','XHXFH158954976243937572756','XHXFH158961547034155987998','XHXFH158960667662875751539','XHXFH158963736453916807374','XHXFH158954290150897503009','XHXFH159023903362601595572','XHXFH158962502197874307303','XHXFH158953603091610973498','XHXFH158954776022159917525','XHXFH159089806979660918842','XHXFH158970300638541461958','XHXFH158980826863488409210','XHXFH158946003980239721359','XHXFH159006511680064031115','XHXFH159084666471251963357','XHXFH158954868531208079896','XHXFH158961879016956779196','XHXFH158953127784190640761','XHXFH158953864723704256857','XHXFH158968213079227519753','XHXFH159057226747737248280','XHXFH158954542105054561232','XHXFH158953969420539814112','XHXFH158954798033410775003','XHXFH158962370594459559746','XHXFH159048568439244105175','XHXFH158954843788919223266','XHXFH158953578546484555138','XHXFH159023494536383579913','XHXFH159082814064800333506','XHXFH158958794820651591779','XHXFH158954025042159535879','XHXFH158953866711306256857','XHXFH158937512763834598886','XHXFH158953753674234292119','XHXFH159089264332313689348','XHXFH158963669316930797996','XHXFH158955389398190923568','XHXFH159014041988880457705','XHXFH158998127246792311970','XHXFH158989691450167883018','XHXFH159014443366614599131','XHXFH159015385532313377836','XHXFH159083513417190856786','XHXFH158971197629083579031','XHXFH158960196916423937430','XHXFH158962643453063157061','XHXFH159014002464030556776','XHXFH159015253509774573096','XHXFH158963303443641567073','XHXFH158967315103209732757','XHXFH159021243989477257861','XHXFH158954818480767219065','XHXFH159015298424743097170','XHXFH158954908324691630303','XHXFH158962457238297638050','XHXFH158960210982076937430','XHXFH159049253637496508220','XHXFH159006712985822484295','XHXFH158936224889121555891','XHXFH158967857255802469952','XHXFH158937266252843250758','XHXFH158962965742759378912','XHXFH158953886261002809620','XHXFH158954797293788502213','XHXFH158968791441706568555','XHXFH159014783849855006191','XHXFH158953766153758238548','XHXFH158962175541389859851','XHXFH158959719245634713900','XHXFH159083842138276740279','XHXFH158954791793212557666','XHXFH159084385164791041814','XHXFH159023375473291391661','XHXFH158962760140763655414','XHXFH159014503569434749570','XHXFH159015288892449518680','XHXFH158953888387867859081','XHXFH158954338822842668283','XHXFH158961856540966568481','XHXFH159014432365358766209','XHXFH159081538861966825399','XHXFH158963413680306997809','XHXFH158953834162881302933','XHXFH159014370721451870987','XHXFH159084428634755666265','XHXFH158963292978114498993','XHXFH158961818882804001816','XHXFH158953930010157753317','XHXFH158961785429382982649','XHXFH158962223176665735926','XHXFH158963416711646850579','XHXFH158954029450434577396','XHXFH158971500040669542725','XHXFH158954801273209655565','XHXFH159022899449601572860','XHXFH159015479221814750618','XHXFH158980558192969382187','XHXFH159033004712050859265','XHXFH158968824920732522451','XHXFH158967781899319356906','XHXFH158975614181839549390','XHXFH158963413604851076075','XHXFH158966401863419927125','XHXFH158962216771812807787','XHXFH158962263598598520711','XHXFH159022741033161043466','XHXFH158946056557071781489','XHXFH159023056235245484822','XHXFH158945896089916587686','XHXFH158936685495525260099','XHXFH159088037504831966382','XHXFH158961852714057568481','XHXFH158962476418134440289','XHXFH158961269474742041621','XHXFH158937553764015166602','XHXFH158969657464853862215','XHXFH158954976397953057885','XHXFH158963434143885978750','XHXFH158962300751961761118','XHXFH159022931349474853105','XHXFH158961422498586120402','XHXFH159029384598732857259','XHXFH158971815418945637111','XHXFH159093201274098462225','XHXFH159088570806254757990','XHXFH158961820930302001816','XHXFH158961261690863882527','XHXFH159084516672175702122','XHXFH158937271833750250758','XHXFH158963478804541361680','XHXFH158960462989209977998','XHXFH159023721956652259603','XHXFH159023494536383579913','XHXFH158954966428527560130','XHXFH159091737102141508393','XHXFH159074904245126795953','XHXFH158962182361029572670') and status = 6
     *
     * select * from od_order_package where delivery_no in ('XHXFH158953753674234292119','XHXFH158961820930302001816','XHXFH158966401863419927125','XHXFH159023494536383579913','XHXFH159049253637496508220')
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("/Users/xstarfct/Downloads/yinge_call_error.20200602.log");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));//构造一个BufferedReader类来读取文件
            
            StringBuilder builder = new StringBuilder("(");
            String s;
            //使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                YingeLogisticsDTO dto = JSON.parseObject(s, YingeLogisticsDTO.class);
//                buildTraceUpdateRecord(dto);
                builder.append("'").append(dto.getOutTradeNo()).append("',");
            }
            br.close();
    
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
