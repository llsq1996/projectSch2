package com.example.demo.controller;

import com.example.demo.entity.exEntity.ShopCategory;
import com.example.demo.entity.exEntity.ShopListRec;
import com.example.demo.enums.CategoryEnum;
import com.example.demo.enums.DeliveryEnum;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.util.Help;
import com.example.demo.util.ToJsonObject;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
public class CountController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RegionMapper regionMapper;

    @GetMapping("/categoryCount")
    @ResponseBody
    JSONObject CategoryCount() {
        List<ShopCategory> list = Lists.newArrayList();
        CategoryEnum[] arraty = CategoryEnum.values();
        for (int i = 0; i < arraty.length; i++) {
            ShopCategory category = new ShopCategory();
            category.setCategoryName(arraty[i].getName());
            category.setShopList(Lists.newArrayList());
            category.setPinNum(0);
            list.add(category);
        }
        shopMapper.getAllShop().forEach(x -> {
            list.forEach(each -> {
                if (CategoryEnum.CategoryName(x.getCategory()).equals(each.getCategoryName())) {
                    ShopListRec shopRec = new ShopListRec();
                    BeanUtils.copyProperties(x, shopRec);
                    if (x.getIsTradeMark() == 1) {
                        shopRec.setIsTradeMark("是");
                        each.setPinNum(each.getPinNum()+1);
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
