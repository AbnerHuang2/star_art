package com.star.service;

import com.star.mapper.ChapterDao;
import com.star.model.entity.Chapter;
import com.star.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */
public interface ChapterService {

    public List<Chapter> getChapterByCourseId(Long courseId);

}
