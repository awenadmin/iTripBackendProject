package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.LabelDicDao;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.service.LabelDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <b>通用字典/特色信息业务层接口实现类</b>
 * @author  awen
 * @version 3.1.1 2019-12-11
 * @since 2019-12-17
 */
@Service("labelDicService")
@Transactional
public class LabelDicServiceImpl implements LabelDicService {
    @Autowired
    private LabelDicDao labelDicDao;

    /**
     * <b>获得通用字典信息列表</b>
     * @return
     * @throws Exception
     */
    public List<LabelDic> getLabelDicList() throws Exception {
        // 使用数据持久层进行直接查询
        List<LabelDic> labelDicList = labelDicDao.findLabelDicListByQuery(null);
        return labelDicList;
    }
}