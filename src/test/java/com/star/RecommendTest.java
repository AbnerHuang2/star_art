package com.star;

import com.alibaba.fastjson.JSONObject;
import com.star.service.RecommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/5/4
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecommendTest {

    @Autowired
    RecommandService recommandService;

    @Test
    void testRecommandService(){
        //recommandService.getRecommend();
        List<JSONObject> res = recommandService.getRecommendCourseByUid(1l,2,2);
        System.out.println(res);
    }

}
