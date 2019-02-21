package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.entity.exEntity.Count;
import com.example.demo.entity.exEntity.ShopListRec;
import com.example.demo.enums.CategoryEnum;
import com.example.demo.enums.DeliveryEnum;
import com.example.demo.mapper.AuditMapper;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.Help;
import com.example.demo.util.ToJsonObject;
import com.google.common.collect.Lists;
import freemarker.ext.beans.HashAdapter;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Controller
public class CountController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuditMapper auditMapper;

    /**
     * 类别统计
     * @return
     */
    @GetMapping("/categoryCount")
    @ResponseBody
    JSONObject CategoryCount() {
        List<Count> list = Lists.newArrayList();
        CategoryEnum[] arraty = CategoryEnum.values();
        for (int i = 0; i < arraty.length; i++) {
            Count count = new Count();
            count.setName(arraty[i].getName());
            count.setShopList(Lists.newArrayList());
            count.setNum(0);
            list.add(count);
        }
        shopMapper.getAllShop().forEach(x -> {
            list.forEach(each -> {
                if (CategoryEnum.CategoryName(x.getCategory()).equals(each.getName())) {
                    ShopListRec shopRec = new ShopListRec();
                    BeanUtils.copyProperties(x, shopRec);
                    if (x.getIsTradeMark() == 1) {
                        shopRec.setIsTradeMark("是");
                        each.setNum(each.getNum()+1);
                    } else {
                        shopRec.setIsTradeMark("否");
                    }
                    shopRec.setCTime(Help.timeFormat(x.getCTime()));
                    shopRec.setETime(Help.timeFormat(x.getETime()));
                    shopRec.setAddress(getName(x.getAddress().split(",")));
                    if (Objects.nonNull(x.getDeliPrice())) {
                        shopRec.setDeliPrice(x.getDeliPrice().toString());
                    }
                    if (Objects.nonNull(x.getDispatch())) {
                        shopRec.setDispatch(x.getDispatch().toString());
                    }
                    shopRec.setCategory(CategoryEnum.CategoryName(x.getCategory()));
                    shopRec.setDelivery(DeliveryEnum.DeliveryName(x.getDelivery()));
                    each.getShopList().add(shopRec);
                }
            });
        });
        return ToJsonObject.getSuccessJSONObject(list);
    }

    /**
     * 入驻统计
     * @return
     */
    @GetMapping("/shopAddCount")
    @ResponseBody
    JSONObject ShopAddCount() {
        List<Count> list = Lists.newArrayList();
        Shop shopLast=shopMapper.getLast();
        Integer day=Help.timeToNow(shopLast.getCTime());
        day=day/7;
        for (int i = 0; i <= day; i++) {
            Count count = new Count();
            count.setName((i+1)+"周");
            count.setNum(0);
            count.setShopList(Lists.newArrayList());
            list.add(count);
        }
        shopMapper.getAllShop().forEach(x -> {
            Integer week=Help.timeToNow(x.getCTime());
            week/=7;
            Count obj=list.get(week);
                    ShopListRec shopRec = new ShopListRec();
                    BeanUtils.copyProperties(x, shopRec);
                    if (x.getIsTradeMark() == 1) {
                        shopRec.setIsTradeMark("是");
                        obj.setNum(obj.getNum()+1);
                    } else {
                        shopRec.setIsTradeMark("否");
                    }
                    shopRec.setCTime(Help.timeFormat(x.getCTime()));
                    shopRec.setETime(Help.timeFormat(x.getETime()));
                    shopRec.setAddress(getName(x.getAddress().split(",")));
                    if (Objects.nonNull(x.getDeliPrice())) {
                        shopRec.setDeliPrice(x.getDeliPrice().toString());
                    }
                    if (Objects.nonNull(x.getDispatch())) {
                        shopRec.setDispatch(x.getDispatch().toString());
                    }
                    shopRec.setCategory(CategoryEnum.CategoryName(x.getCategory()));
                    shopRec.setDelivery(DeliveryEnum.DeliveryName(x.getDelivery()));
                    obj.getShopList().add(shopRec);
            });
        return ToJsonObject.getSuccessJSONObject(list);
    }

    /**
     * 人员统计
     * @return
     */
    @GetMapping("/userCount")
    @ResponseBody
    JSONObject UserCount() {
        List<Count> list = null;
        Map<String,Count> map=new HashMap<>();
        //统计录入量
        shopMapper.getAllShop().forEach(x -> {
            if(map.containsKey(x.getWorker())){
                Count ob=map.get(x.getWorker());
                ShopListRec shopRec = new ShopListRec();
                BeanUtils.copyProperties(x, shopRec);
                if (x.getIsTradeMark() == 1) {
                    shopRec.setIsTradeMark("是");
                } else {
                    shopRec.setIsTradeMark("否");
                }
                shopRec.setCTime(Help.timeFormat(x.getCTime()));
                shopRec.setETime(Help.timeFormat(x.getETime()));
                shopRec.setAddress(getName(x.getAddress().split(",")));
                if (Objects.nonNull(x.getDeliPrice())) {
                    shopRec.setDeliPrice(x.getDeliPrice().toString());
                }
                if (Objects.nonNull(x.getDispatch())) {
                    shopRec.setDispatch(x.getDispatch().toString());
                }
                shopRec.setCategory(CategoryEnum.CategoryName(x.getCategory()));
                shopRec.setDelivery(DeliveryEnum.DeliveryName(x.getDelivery()));
                ob.getShopList().add(shopRec);
            }else{
                Count ob=new Count();
                ob.setNum(0);
                ob.setName(x.getWorker());
                ob.setShopList(Lists.newArrayList());
                map.put(x.getWorker(),ob);
            }
        });
        //统计审核量
        auditMapper.getAllAuditShop().forEach(x->{
            if(map.containsKey(x.getAuditor())){
                Count ob=map.get(x.getAuditor());
                ob.setNum(ob.getNum()+1);
            }else{
                Count ob=new Count();
                ob.setNum(1);
                ob.setName(x.getAuditor());
                ob.setShopList(Lists.newArrayList());
                map.put(x.getAuditor(),ob);
            }
        });
        list=map.values().stream().sorted((x,y)->y.getShopList().size()-x.getShopList().size())
                .limit(10).collect(Collectors.toList());
        return ToJsonObject.getSuccessJSONObject(list);
    }
    /**
     * 根据地址id，获取地址名字
     * @param list
     * @return
     */
    private String getName(String[] list){
        if(list.length==3){
            return regionMapper.getProName(list[0])+
                    regionMapper.getCityName(list[1])+
                    regionMapper.getAreaName(list[2]);
        }else {
            return "";
        }
    }
}
