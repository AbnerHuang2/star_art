package com.star.service.impl;

import com.star.mapper.ChapterDao;
import com.star.model.entity.Chapter;
import com.star.service.ChapterService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/21
 */
@Service
public class ChapterServiceImpl implements ChapterService {
    @Resource
    ChapterDao chapterDao;

    @Override
    @Cacheable(value = "chapterByCourseId")
    public List<Chapter> getChapterByCourseId(Long courseId){
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseId",courseId);
        return chapterDao.selectByExample(example);
    }
}
