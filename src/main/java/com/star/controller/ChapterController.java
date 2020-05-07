package com.star.controller;

import com.star.model.api.CommonResult;
import com.star.model.entity.Chapter;
import com.star.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/19
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("/getChapters")
    public CommonResult<List<Chapter>> getChapters(Long courseId){
        List<Chapter> list =  chapterService.getChapterByCourseId(courseId);
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取相关章节成功");
        }
        return CommonResult.failed("获取相关章节失败");
    }
}
