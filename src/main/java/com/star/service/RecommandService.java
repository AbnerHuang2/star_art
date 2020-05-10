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
@Service
public class RecommandService {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String pass;

    //设置用户邻居数量
    Integer neighborNum = 10;

    public List<JSONObject> getRecommendCourseByUid(Long uid,int size,int type){
        List<JSONObject> resList = null;
        if(type==1){
            resList = getRecommendByUser(uid,size,"user_course", "user_id", "course_id", "user_rate", "select_date");
        } else {
            resList = getRecommendByItem(uid,size,"user_course", "user_id", "course_id", "user_rate", "select_date");
        }

        return resList;
    }

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
    public List<JSONObject> getRecommendByUser(Long uid, int size,String table,String userIdColumn,String itemIdColumn, String valueColumn, String timeColumn) {
        List<JSONObject> resList = new ArrayList();
        try {
            String server = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
            String database = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
            //权重计算使用数据库中的值，也可以使用文件形式
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(server);
            dataSource.setUser(user);
            dataSource.setPassword(pass);
            dataSource.setDatabaseName(database);
            DataModel model = new MySQLJDBCDataModel(dataSource,table, userIdColumn,itemIdColumn,valueColumn,timeColumn);
            // 指定用户相似度计算方法，这里采用皮尔森相关度
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            // 指定用户邻居数量，这里为10
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighborNum, similarity, model);
            // 构建基于用户的推荐系统
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 得到指定用户的推荐结果，这里是得到用户1的两个推荐
            List<RecommendedItem> list=recommender.recommend(uid, size);

            for(RecommendedItem item : list){
                JSONObject json = new JSONObject();
                json.put("itemId",item.getItemID());
                json.put("value",item.getValue());
                resList.add(json);
            }
            return resList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return resList;
        }
    }

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
    public List<JSONObject> getRecommendByItem(Long uid, int size,String table,String userIdColumn,String itemIdColumn, String valueColumn, String timeColumn) {
        List<JSONObject> resList = new ArrayList();
        try {
            String server = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
            String database = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
            //权重计算使用数据库中的值，也可以使用文件形式
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(server);
            dataSource.setUser(user);
            dataSource.setPassword(pass);
            dataSource.setDatabaseName(database);
            DataModel model = new MySQLJDBCDataModel(dataSource,table, userIdColumn,itemIdColumn,valueColumn,timeColumn);
            // 指定用户相似度计算方法，这里采用皮尔森相关度
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            //
            ItemSimilarity item = new EuclideanDistanceSimilarity(model);
            // 构建基于用户的推荐系统
            Recommender recommender = new GenericItemBasedRecommender(model,item);

            // 得到指定用户的推荐结果，这里是得到用户1的两个推荐
            List<RecommendedItem> list=recommender.recommend(uid, size);

            for(RecommendedItem res_item : list){
                JSONObject json = new JSONObject();
                json.put("itemId",res_item.getItemID());
                json.put("value",res_item.getValue());
                resList.add(json);
            }

            return resList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return resList;
        }
    }

    public void getRecommend() {
        System.out.println(url);
        try {
            String server = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
            String database = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
            //权重计算使用数据库中的值，也可以使用文件形式
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(server);
            dataSource.setUser(user);
            dataSource.setPassword(pass);
            dataSource.setDatabaseName(database);
            DataModel model = new MySQLJDBCDataModel(dataSource, "user_course", "user_id", "course_id", "user_rate", "select_date");
            // 指定用户相似度计算方法，这里采用皮尔森相关度
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            // 指定用户邻居数量，这里为10
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
            // 构建基于用户的推荐系统
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            // 得到指定用户的推荐结果，这里是得到用户1的两个推荐
            LongPrimitiveIterator iterator=model.getUserIDs();
            while(iterator.hasNext()) {
                long uid=iterator.nextLong();
                System.err.println(uid);
                List<RecommendedItem> list=recommender.recommend(uid, 2);
                for(RecommendedItem item:list) {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
