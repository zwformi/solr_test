package com.zw.test;

import com.zw.entity.User;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/9.
 */
public class Solr_mysql {
    private static HttpSolrServer server = null;

    private static final String DEFAULT_URL = "http://localhost:8080/solr/db";

    public static void init() {
         server = new HttpSolrServer(DEFAULT_URL);
    }

    public static void indexUser(User user){
        try {
            //添加user bean到索引库
            try {
                UpdateResponse response = server.addBean(user);
                server.commit();
                System.out.println(response.getStatus());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    //测试添加一个新的bean实例到索引
    public static void testIndexUser(){
        User user = new User();
        user.setId(8);
        user.setPassword("123456");
        user.setUser_name("周文你谁谁呀呀！！");
        user.setAge(23);
        user.setRoleid(3);

        indexUser(user);
    }

    public static void testQueryAll() {
        SolrQuery params = new SolrQuery();

        // 查询关键词，*:*代表所有属性、所有值，即所有index
        params.set("q", "user_name:小明");

        // 分页，start=0就是从0开始，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
        params.set("start", 0);
        params.set("rows", Integer.MAX_VALUE);

        // 排序，如果按照id排序，那么将score desc 改成 id desc(or asc)
        // params.set("sort", "score desc");
        params.set("sort", "id asc");

        // 返回信息*为全部，这里是全部加上score，如果不加下面就不能使用score
        params.set("fl", "*,score");

        QueryResponse response = null;
        try {
            try {
                response = server.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        if(response!=null){
            System.out.println("Search Results: ");
            SolrDocumentList list = response.getResults();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }

    public static void main(String[] args) {
        init();
        //testIndexUser();
        testQueryAll();
    }
}
