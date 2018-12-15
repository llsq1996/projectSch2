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
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Controller
public class ShopController {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private RegionMapper regionMapper;

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
        String address=getName(shopRec.getAddressList().split(","));
        if(StringUtils.isEmpty(address)){
            isRight=false;
            message="省市县地址信息不全";
        }
        shop.setAddress(address);
        System.out.println(shop);
        //写入
        if(isRight){
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
     * 获取地址名字
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

    /**
     * 图片上传
     * @param file
     * @param fileDate
     * @return
     */
    @RequestMapping("/pic")
    @ResponseBody
    JSONObject picture(@Param("file") MultipartFile file, UploadFile fileDate) {
        System.out.println(fileDate);
       if(Objects.nonNull(file)){
           if(StringUtils.isEmpty(fileDate.getName())){
               fileDate.setName("0_example.jpg");
           }
           try{
               byte bs[]=file.getBytes();
               File path = new File(ResourceUtils.getURL("classpath:").getPath());
               if(!path.exists()) {
                   path = new File("");
               }
               File upload = new File(path.getAbsolutePath(),"static/picture/"+fileDate.getId()+"_"+fileDate.getName());
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

    /**
     * excel文件上传
     * @param file
     * @param fileDate
     * @return
     */
    @RequestMapping("/excel")
    @ResponseBody
    JSONObject excel(@Param("file") MultipartFile file, UploadFile fileDate){
        if(Objects.nonNull(file)){
            try{
                byte bs[]=file.getBytes();
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) {
                    path = new File("");
                }
                File upload = new File(path.getAbsolutePath(),"static/excel/"+fileDate.getId()+"_"+fileDate.getName());
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
