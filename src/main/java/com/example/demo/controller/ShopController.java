package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.entity.TradeMark;
import com.example.demo.entity.exEntity.ShopListRec;
import com.example.demo.entity.exEntity.ShopRec;
import com.example.demo.enums.CategoryEnum;
import com.example.demo.enums.DeliveryEnum;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.mapper.TradeMarkMapper;
import com.example.demo.util.Help;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ShopController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private TradeMarkMapper tradeMarkMapper;

    /**
     * 基本资料录入
     * @param shopRec
     * @return
     */
    @PostMapping("/shopAdd")
    @ResponseBody
    JSONObject shopAdd( ShopRec shopRec) {
        System.out.println(shopRec);
        //校验数据标志，数据通过可写入则为true
        Boolean isRight=true;
        //检验数据未通过原因
        String message="";

        Shop shop=new Shop();
        BeanUtils.copyProperties(shopRec,shop);
        shop.setAddress(shopRec.getAddressList());
        try{
            shop.setDeliPrice(Double.parseDouble(shopRec.getDeliPrice()));
            shop.setDispatch(Double.parseDouble(shopRec.getDispatch()));
        }catch (Exception ex){
            isRight=false;
        }
        System.out.println(shop);
        if(StringUtils.isEmpty(shopRec.getAddressList())){
            isRight=false;
        }
        //写入
        if(isRight){
            if(shop.getIsTradeMark()==1){
                TradeMark tradeMark=new TradeMark();
                tradeMark.setTradeMarkName(shop.getSpName());
                tradeMarkMapper.tradeMarkAdd(tradeMark);
                System.out.println(tradeMark);
                shop.setIdTradeMark(tradeMark.getId());
            }
            if(1==shopMapper.shopAdd(shop)){
                System.out.println(shop);
                return ToJsonObject.getSuccessJSONObject(shop.getId());
            }else{
               message="写入数据失败";
            }
        }
        return ToJsonObject.getFailJSONObject2(null,message);
    }

    /**
     * 获取品牌商家列表
     * @return
     */
    @GetMapping("/tradeShop")
    @ResponseBody
    JSONObject tradeShop(){
        List<TradeMark> list=tradeMarkMapper.getAll();
        return ToJsonObject.getSuccessJSONObject(list);
    }

    /**
     * 获取商家列表
     * @return
     */
    @GetMapping("/shopList")
    @ResponseBody
    JSONObject shopList(){
        List<Shop> list=shopMapper.getAllShop();
        if(Objects.nonNull(list)){
            List<ShopListRec> shopList=list.stream().map(x->{
                ShopListRec shopRec=new ShopListRec();
                BeanUtils.copyProperties(x,shopRec);
                if(x.getIsTradeMark()==1){
                    shopRec.setIsTradeMark("是");
                }else{
                    shopRec.setIsTradeMark("否");
                }
                shopRec.setCTime(Help.timeFormat(x.getCTime()));
                shopRec.setETime(Help.timeFormat(x.getETime()));
                shopRec.setAddress(getName(x.getAddress().split(",")));
                if(Objects.nonNull(x.getDeliPrice())){
                    shopRec.setDeliPrice(x.getDeliPrice().toString());
                }
                if(Objects.nonNull(x.getDispatch())){
                    shopRec.setDispatch(x.getDispatch().toString());
                }
                shopRec.setCategory(CategoryEnum.CategoryName(x.getCategory()));
                shopRec.setDelivery(DeliveryEnum.DeliveryName(x.getDelivery()));
                return shopRec;
            }).collect(Collectors.toList());
            return ToJsonObject.getSuccessJSONObject(shopList);
        }
        return ToJsonObject.getFailJSONObject(null);
    }

    /**
     * 获取商家详情信息
     * @param id
     * @return
     */
    @GetMapping("/shopDetail")
    @ResponseBody
    JSONObject shopDetail(@Param("id") Integer id){
        //to do
        Shop shop=shopMapper.getShopById(id);
        if(Objects.nonNull(shop)){
            ShopRec shopRec=new ShopRec();
            BeanUtils.copyProperties(shop,shopRec);
            shopRec.setAddressList(shop.getAddress());
            if(Objects.nonNull(shop.getDeliPrice())){
                shopRec.setDeliPrice(shop.getDeliPrice().toString());
            }
            if(Objects.nonNull(shop.getDispatch())){
                shopRec.setDispatch(shop.getDispatch().toString());
            }
            return ToJsonObject.getSuccessJSONObject(shopRec);
        }
        return ToJsonObject.getFailJSONObject(null);
    }

    /**
     * 修改商家详情信息
     * @param shopRec
     * @return
     */
    @PostMapping("/shopUpdate")
    @ResponseBody
    JSONObject shopUpdate(ShopRec shopRec){
        if(Objects.nonNull(shopRec)){
            System.out.println(shopRec);
            //校验数据标志，数据通过可写入则为true
            Boolean isRight=true;
            //检验数据未通过原因
            String message="";
            Shop shop=new Shop();
            BeanUtils.copyProperties(shopRec,shop);
            shop.setAddress(shopRec.getAddressList());
            try{
                shop.setDeliPrice(Double.parseDouble(shopRec.getDeliPrice()));
                shop.setDispatch(Double.parseDouble(shopRec.getDispatch()));
            }catch (Exception ex){
                isRight=false;
            }
            System.out.println(shop);
            if(StringUtils.isEmpty(shopRec.getAddressList())){
                isRight=false;
            }
            //写入
            if(isRight){
                if(1==shopMapper.shopUpdate(shop)){
                    System.out.println(shop);
                    return ToJsonObject.getSuccessJSONObject(null);
                }else{
                    message="修改数据失败";
                }
            }
        }
        return ToJsonObject.getFailJSONObject(null);
    }
    /**
     * 删除商家详情信息
     * @param id
     * @return
     */
    @GetMapping("/shopDelete")
    @ResponseBody
    JSONObject shopDelete(@Param("id") Integer id){
        if(Objects.nonNull(id)){
            if(1==shopMapper.delAll(id)){
                return ToJsonObject.getSuccessJSONObject(null);
            }
       }
           return ToJsonObject.getFailJSONObject(null);
    }
    @GetMapping("/tradeMarkGet")
    @ResponseBody
    JSONObject tradeMarkGet(){
        return ToJsonObject.getSuccessJSONObject(tradeMarkMapper.getAll());
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
