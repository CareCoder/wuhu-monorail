package net.cdsunrise.wm.schedule.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * @author gechaoqing
 * 通用统计工具
 */
public class AnalyzeCommonCountUtil extends HashMap<String,AnalyzeCommonCountVo> {
    public AnalyzeCommonCountUtil(){}
    public AnalyzeCommonCountUtil(String... names){
        this.init(names);
    }
    public AnalyzeCommonCountUtil(List<String> names){
        this.init(names);
    }
    public void init(List<String> names){
        if(names!=null&&!names.isEmpty()){
            for(String name:names){
                AnalyzeCommonCountVo analyzeCommonCountVo = new AnalyzeCommonCountVo();
                analyzeCommonCountVo.setName(name);
                analyzeCommonCountVo.setCount(new AtomicInteger(0));
                put(name,analyzeCommonCountVo);
            }
        }
    }
    public void init(String...names){
        if(names!=null&&names.length>0){
            this.init(Arrays.asList(names));
        }
    }
    public void increment(String name){
        get(name).getCount().getAndIncrement();
    }

    public List<AnalyzeCommonCountVo> countResult(){
        List<AnalyzeCommonCountVo> result = new ArrayList<>();
        forEach((name,count)->result.add(count));
        return result;
    }
}
