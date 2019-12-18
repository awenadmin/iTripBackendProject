package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.pojo.entity.AreaDic;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.Dto;
import cn.ekgc.itrip.transport.AreaDicTransport;
import cn.ekgc.itrip.transport.LabelDicTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>主业务模块控制器</b>
 * @author awen
 * @version 3.1.1 2019-12-17
 * @since 2019-12-17
 */
@RestController("bizController")
@RequestMapping("/biz")
public class BizController extends BaseController {
    @Autowired
    private AreaDicTransport areaDicTransport;
    @Autowired
    private LabelDicTransport labelDicTransport;
    /**
     * <b>根据是否为国内外，查找热门城市</b>
     * @param isChina
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/hotel/queryhotcity/{isChina}")
    public Dto<Object> queryCityListByIsChina(@PathVariable("isChina") Integer isChina) throws Exception{
        Dto<Object> resultDto = new Dto<Object>();
        //使用传输层调用Provider的server进行具体的查询
        List<AreaDic> areaDicList = areaDicTransport.getHotCityListByIsChina(isChina);
        resultDto.setData(areaDicList);
        resultDto.setSuccess("true");
        return resultDto;
    }
    @RequestMapping(value = "/api/hotel/queryhotelfeature", method = RequestMethod.GET)
    public Dto<Object> queryHotelFeatureList() throws Exception {
        Dto<Object> result = new Dto<Object>();
        // 查询结果列表
        List<LabelDic> labelDicList = labelDicTransport.getHotelFeatureList();

        result.setSuccess("true");
        result.setData(labelDicList);
        return result;
    }
}
