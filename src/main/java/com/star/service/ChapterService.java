package com.star.service;

import com.star.mapper.ChapterDao;
import com.star.model.entity.Chapter;
import com.star.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */
@Service
public class ChapterService {

    @Autowired
    ChapterDao chapterDao;

    public List<Chapter> getChapterByCourseId(Long courseId){
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseId",courseId);
        return chapterDao.selectByExample(example);
    }

}
