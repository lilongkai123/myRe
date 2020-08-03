package com.fh.category.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fh.category.mapper.CategoryMapper;
import com.fh.category.service.CategoryService;
import com.fh.common.ServerResponse;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    public ServerResponse categoryList() {
        Boolean exists = RedisUtil.exists("categoryList");
        if (exists){
            String categoryList = RedisUtil.get("categoryList");
            List<Map> maps = JSONArray.parseArray(categoryList, Map.class);
            return ServerResponse.success(maps);
        }
        //获取所有的子节点
        List<Map<String, Object>> allList = categoryMapper.categoryList();
        //获取父节点
        List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
        //循环子节点
        for (Map map : allList) {
            //判断子节点不为空时
            if (map.get("pid").equals(0)) {
                //将子节点赋值给父节点
                parentList.add(map);
            }
        }
        selectChildren(allList,parentList);

        return ServerResponse.success(parentList);
    }


    public void selectChildren(List<Map<String, Object>> allList,List<Map<String, Object>> parentList){
        //遍历父节点
        for (Map<String, Object> pmm : parentList) {
            //查询所有的子节点的内容
            List<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>();
            //遍历子节点
            for (Map<String, Object> amap : allList) {
                //判断父节点的id是否等于子节点的pid
                if (pmm.get("id").equals(amap.get("pid"))){
                    //将所有的子节点赋值给子节点的内容
                    childrenList.add(amap);
                }
            }
            //判断如果儿子的集合不为空且长度大于0说明集合里面有值
            if (childrenList !=null && childrenList.size()>0){
                //然后把父节点里面的值赋值给儿子的集合
                pmm.put("children",childrenList);
                //这里是递归生成自己调用自己 来查询
                selectChildren(allList,childrenList);
            }
        }
    }
}
