package com.star.service;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/5/4
 */

/**
 * 智能推荐服务
 */
public interface RecommandService {

    public List<JSONObject> getRecommendCourseByUid(Long uid, int size, int type);
    /**
     * 基于用户的协同过滤算法
     * 根据用户id和推荐数量返回推荐列表，如果数据量太小不足以推荐，则list的size为 0；
     * @param uid
     * @param size
     * @param table
     * @param userIdColumn
     * @param itemIdColumn
     * @param valueColumn
     * @param timeColumn
     * @return
     */
    public List<JSONObject> getRecommendByUser(Long uid, int size,String table,String userIdColumn,
                                               String itemIdColumn, String valueColumn, String timeColumn);
    /**
     * 基于物品的协同过滤
     * @param uid
     * @param size
     * @param table
     * @param userIdColumn
     * @param itemIdColumn
     * @param valueColumn
     * @param timeColumn
     * @return
     */
    public List<JSONObject> getRecommendByItem(Long uid, int size,String table,String userIdColumn,
                                               String itemIdColumn, String valueColumn, String timeColumn);
    public void getRecommend();

}
