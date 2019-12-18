package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.AreaDicDao;
import cn.ekgc.itrip.pojo.entity.AreaDic;
import cn.ekgc.itrip.service.AreaDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("areaDicService")
@Transactional
public class AreaDicServiceImpl implements AreaDicService {
    @Autowired
    private AreaDicDao areaDicDao;

    /**
     * <b>根据是否为国内还是国外，查询热门城市列表</b>
     * @param isChina
     * @return
     * @throws Exception
     */
    public List<AreaDic> getHotCityListByIsChina(Integer isChina) throws Exception {
        // 封装查询参数
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("isChina", isChina);
        queryMap.put("isHot", 1);           // 默认为热门城市
        // 对于所显示的数据中心，应该都是出于激活状态的
        queryMap.put("isActivated", 1);

        // 使用数据持久层进行查询，获得列表
        List<AreaDic> areaDicList = areaDicDao.findAreaDisListByQuery(queryMap);
        return areaDicList;
    }
}