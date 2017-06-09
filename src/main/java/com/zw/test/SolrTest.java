package com.zw.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2017/6/7.
 */
public class SolrTest {
    private static final String URL = "http://localhost:8080/solr/db";

    private HttpSolrClient server = null;

    @Before
    public void init() {
        // 创建 server
        server = new HttpSolrClient(URL);
    }

    @Test
    public void addDoc() {

        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", "this is id");
        doc.addField("title", "this is document");
        doc.addField("content", "this is content");
        try {

            UpdateResponse response = server.add(doc);
            // 提交
            server.commit();

            System.out.println("########## Query Time :" + response.getQTime());
            System.out.println("########## Elapsed Time :" + response.getElapsedTime());
            System.out.println("########## Status :" + response.getStatus());

        } catch (SolrServerException | IOException e) {
            System.err.print(e);
        }
    }

//    利用SolrJ添加多个Document，即添加文档集合
    @Test
    public void addDocs() {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 2);
        doc.addField("title", "SolrInputDocuments 1");
        doc.addField("content", "this is SolrInputDocuments 1 content");

        docs.add(doc);

        doc = new SolrInputDocument();
        doc.addField("id", 3);
        doc.addField("title", "SolrInputDocuments 2");
        doc.addField("content", "this is SolrInputDocuments 3 content");

        docs.add(doc);

        try {
            //add docs
            UpdateResponse response = server.add(docs);
            //commit后才保存到索引库
            fail(server.commit());
            fail(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        query("title:SolrInputDocuments 2");
    }

    /**
     * 查询
     */
    @Test
    public void testQuery() {
        String queryStr = "*:*";
        SolrQuery params = new SolrQuery();
        params.setQuery(queryStr);
        params.set("rows", 10);
        try {
            QueryResponse response = null;
            try {
                response = server.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList list = response.getResults();
            System.out.println("########### 总共 ： " + list.getNumFound() + "条记录");
            for (SolrDocument doc : list) {
                System.out.println("######### id : " + doc.get("id") + "  title : " + doc.get("title")+"  content : " + doc.get("content"));
            }
        } catch (SolrServerException e) {
            System.err.print(e);
        }
    }

    public final void fail(Object o) {

        System.out.println(o.toString());

    }
    /**

     * <b>function:</b> 根据query参数查询索引
     * @param query
     */

    public void query(String query) {

        SolrParams params = new SolrQuery(query);
        try {

            QueryResponse response = null;
            try {
                response = server.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }

            SolrDocumentList list = response.getResults();

            for (SolrDocument doc : list) {
                System.out.println("######### id : " + doc.get("id") + "  title : " + doc.get("title")+"  content : " + doc.get("content"));
            }

        } catch (SolrServerException e) {

            e.printStackTrace();

        }

    }




}
