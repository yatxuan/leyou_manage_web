package com.calabashbrothers.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

public class SolrUtil {
    SolrServer httpSolrServer = new HttpSolrServer("http://localhost:6060/solr/");

    /**
     *
     * 添加数据到索引库
     * @param bean
     */
    public void commitDocument(Object bean){
        SolrInputDocument solrInputDocument = this.add(bean);
        try {
            httpSolrServer.add(solrInputDocument);
            httpSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回SolrInputDocument对象
     * @param bean
     * @return
     */
    private SolrInputDocument add(Object bean){
        SolrInputDocument doc = null;
        Class<?> entity = bean.getClass();
        Field[] fields = entity.getDeclaredFields();
        doc = new SolrInputDocument();
        for (Field f : fields) {
            f.setAccessible(true);
            if(!isFinal(f.getModifiers())){
                try {
                    this.setField(doc, f.getName(), f.get(bean), f.getType());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(" SolrServiceImpl.reflect2Doc 反射获取bean方法返回值失败! " + e);
                }
            }
        }
        doc.setField("classes_s", entity.toString()); //增加类型信息
        return doc;
    }

    /**
     * 判断该字段是不是常量
     * @param modifiers
     * @return
     */
    private boolean isFinal(int modifiers){
        return (modifiers & Modifier.FINAL) != 0;
    }

    /**
     * 设置字段名(域名)
     * @param doc
     * @param name
     * @param val
     * @param type
     */
    private void setField(SolrInputDocument doc,String name,Object val,Class<?> type){

        if(!"id".equals(name) && !"title_ik".equals(name)){

            name += getFieldNameSuffix(type);
        }
        doc.setField(name, val);
    }

    /**
     * 获取字段后缀
     * @param type
     * @return
     */
    private String getFieldNameSuffix(Class<?> type){
        String suffix = "";
        if (type == Byte.class || type == byte.class) {
            suffix = "_i";
        } else if (type == Short.class || type == short.class) {
            suffix = "_i";
        } else if (type == Integer.class || type == int.class) {
            suffix = "_i";
        } else if (type == Long.class || type == long.class) {
            suffix = "_l";
        } else if (type == Float.class || type == float.class) {
            suffix = "_f";
        } else if (type == Double.class || type == double.class) {
            suffix = "_d";
        } else if (type == Boolean.class || type == boolean.class) {
            suffix = "_b";
        } else if (type == Character.class || type == char.class) {
            suffix = "_s";
        } else if (type == String.class) {
            suffix = "_s";
        } else if (type == Date.class) {
            suffix = "_dt";
        }
        return suffix;
    }

    /**
     * 删除索引库中指定数据
     * @param entity
     */
    public void deleteDocument(Object entity){
        Class<?> clazz = entity.getClass();
        for(Field f : clazz.getDeclaredFields()){
            f.setAccessible(true);
            String fileName = f.getName();
            fileName += getFieldNameSuffix(f.getType());

            Object val = null;
            try {
                val = f.get(entity);
            } catch (IllegalArgumentException | IllegalAccessException e1) {
                throw new RuntimeException("删除失败! \r  " + e1);
            }

            if(val == null) continue;

            try {
                httpSolrServer.deleteByQuery(fileName + ":" + val,1000);
            } catch (Exception e) {
                throw new RuntimeException("删除失败! \r  " + e);
            }
        }
    }

    /**
     * 多条件查询
     * @param param
     * @param fieldName
     * @param q
     * @param entitys
     * @return
     */
    public List<?> search(Map<String,String> param, String fieldName, String q, Class<?>[] entitys){

        if(param == null)param = new HashMap<>();

        String[] fqVal = existence(param.get("fq"));//过滤条件

        String[] sortVal = existence( param.get("sort"));//排序

        String[] pageVal = existence(param.get("page"));//分页

        String[] flVal = existence(param.get("fl"));//指定查询域

        SolrQuery solrQuery = new SolrQuery();
        //设置 条件
        //关键词
        //判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if(StringUtils.isBlank(fieldName)&& StringUtils.isBlank(q)){
            solrQuery.setQuery("*:*");//查所有
        }else{
            solrQuery.setQuery(fieldName + ":*" + q + "*");
        }

        //过滤条件
        if(fqVal != null)
            solrQuery.set("fq",fqVal[0] + ":"+ fqVal[1]);

        //排序
        if(sortVal != null){
            SolrQuery.ORDER order = "ASC".equals(sortVal[1]) ? SolrQuery.ORDER.asc : "DESC".equals(sortVal[1]) ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc;
            solrQuery.addSort(sortVal[0] , order);
        }

        //分页
        if(pageVal != null){
            solrQuery.setStart(Integer.parseInt(pageVal[0]));
            solrQuery.setRows(Integer.parseInt(pageVal[1]));
        }else{
            solrQuery.setStart(0);
            solrQuery.setRows(10);
        }

        //指定查询域
        if(flVal != null){
            StringBuilder sbl = new StringBuilder();

            for(String str : flVal){
                sbl.append(str + ",");
            }
            sbl.delete(sbl.length()-1,sbl.length());
            solrQuery.set("fl",sbl.toString());
        }

        //执行查询
        QueryResponse response;
        try {
            response = httpSolrServer.query(solrQuery);
        } catch (SolrServerException e) {
            throw new RuntimeException("查询异常  \r " + e);
        }
        //文档结果集
        SolrDocumentList docs = response.getResults();

        //获取高亮结果集
        Map<String,Map<String,List<String>>> map = null;
        //map k id v map
        //map k 域名 v list
        //list 结果

        //总行数
        long count = docs.getNumFound();
        System.out.println("查询总条数 : " + count);

        int index = 0;
        List<Object> result = new ArrayList<>();

        for (SolrDocument solrDocument : docs) {
            //判断类型
            Class<?> entity = null;
            Object classes = solrDocument.get("classes_s");
            for (Class<?> clazz : entitys) {
                if(classes.equals(clazz.toString())){
                    entity = clazz;
                    break;
                }
            }

            Object bean;
            try {
                bean = entity.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(" 反射获取实例失败  请检查 entity参数是否为空!  \r " + e);
            }

            for (Field f : entity.getDeclaredFields()) {
                f.setAccessible(true);
                if(!isFinal(f.getModifiers())){ //对于 final 修饰字段不予处理
                    String k = f.getName();

                    if(!"id".equals(k) && !"title_ik".equals(k)) //对于id 字段不予获取后缀
                        k += getFieldNameSuffix(f.getType()); //获取域名后缀

                    Object val = solrDocument.get(k);

                    this.setVal(bean, val+"", f.getType(), f);
                }
            }

            result.add(bean);
            index++;
        }

        return toRepeat(result);
    }

    /**
     * 分割参数
     * @param str
     * @return
     */
    private String[] existence(String str){
        if(StringUtils.isNotBlank(str)){
            return str.split("&");
        }
        return null;
    }


    /**
     * 设置属性
     * @param bean
     * @param val
     * @param type
     * @param f
     */
    private void setVal(Object bean, String val, Class<?> type, Field f) {
        try {
            if(StringUtils.isBlank(val) || "null".equals(val)) {
                return;
            }
            if (type == Byte.class || type == byte.class) {
                f.set(bean, val.getBytes());
            } else if (type == Short.class || type == short.class) {
                f.set(bean, Short.parseShort(val));
            } else if (type == Integer.class || type == int.class) {
                f.set(bean, Integer.parseInt(val));
            } else if (type == Long.class || type == long.class) {
                f.set(bean, Long.parseLong(val));
            } else if (type == Float.class || type == float.class) {
                f.set(bean, Float.parseFloat(val));
            } else if (type == Double.class || type == double.class) {
                f.set(bean, Double.parseDouble(val));
            } else if (type == Boolean.class || type == boolean.class) {
                f.set(bean, Boolean.parseBoolean(val));
            } else if (type == Character.class || type == char.class) {
                f.set(bean, val);
            } else if (type == String.class) {
                f.set(bean, val);
            } else if (type == Date.class) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                f.set(bean, sdf.parse(val));
            }
        } catch (Exception e) {
            throw new RuntimeException("LuceneSearch setVal 出错! " + e);
        }
    }

    /**
     * 去重复
     * @param list
     * @return
     */
    private List<?> toRepeat(List<Object> list){
        Set<?> set = new HashSet<>(list);
        Object[] obj = set.toArray();
        list = new ArrayList<>();
        for (Object o : obj) {
            list.add(o);
        }
        return list;
    }
}
