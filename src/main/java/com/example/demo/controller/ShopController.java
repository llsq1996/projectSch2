package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.entity.exEntity.ShopRec;
import com.example.demo.entity.exEntity.UploadFile;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
public class ShopController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RegionMapper regionMapper;

    @PostMapping("/shopAdd")
    @ResponseBody
    JSONObject shopAdd(@Param("shop") ShopRec shopRec) {
        System.out.println(shopRec);
//        Shop shop=new Shop();
//        BeanUtils.copyProperties(shopRec,shop);
//        shop.setAddress(getName(shopRec.getAddressList()));
//        System.out.println(shop);
//        if(1==shopMapper.shopAdd(shop)){
//            System.out.println(shop);
//            return ToJsonObject.getSuccessJSONObject(shop.getId());
//        }else{
//            return ToJsonObject.getFailJSONObject(null);
//        }
    return ToJsonObject.getSuccessJSONObject(null);
    }
    private String getName(List<String> list){
        if(list.size()==3){
            return regionMapper.getProName(list.get(0))+
                    regionMapper.getCityName(list.get(1))+
                    regionMapper.getAreaName(list.get(2));
        }else {
            return "";
        }
    }

    @RequestMapping("/pic")
    @ResponseBody
    JSONObject picture(@Param("file") MultipartFile file,@Param("name") UploadFile filename) {
//       List<Shop> list=shopMapper.getAllShop().stream().peek(x-> System.out.println(x)).collect(Collectors.toList());
       if(Objects.nonNull(file)){
           if(StringUtils.isEmpty(filename)){
               filename.setName("123.jpg");
           }
           try{
               byte bs[]=file.getBytes();
               File path = new File(ResourceUtils.getURL("classpath:").getPath());
               if(!path.exists()) {
                   path = new File("");
               }
               File upload = new File(path.getAbsolutePath(),"static/picture/"+filename.getName());
               if(!upload.exists()){
                   if(!upload.createNewFile()){
                       return ToJsonObject.getSuccessJSONObject(null);
                   }
               }
               FileOutputStream outputStream=new FileOutputStream(upload);
               outputStream.write(bs);
               outputStream.close();
           }catch (IOException io){
               System.out.println(io);
           }

       }
        return ToJsonObject.getSuccessJSONObject(null);
    }


}
