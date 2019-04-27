package com.example.demo.controller;

import com.example.demo.entity.Audit;
import com.example.demo.entity.Shop;
import com.example.demo.entity.TradeMark;
import com.example.demo.entity.exEntity.AuditListRec;
import com.example.demo.entity.exEntity.UploadFile;
import com.example.demo.enums.CategoryEnum;
import com.example.demo.enums.DeliveryEnum;
import com.example.demo.mapper.AuditMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.mapper.TradeMarkMapper;
import com.example.demo.util.Help;
import com.example.demo.util.ToJsonObject;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AuditController {
    @Autowired
    private AuditMapper auditMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private TradeMarkMapper tradeMarkMapper;
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
                File path = new File("");
                File absolutePath=new File(path.getAbsolutePath()+"/static/picture/");
                if(!absolutePath.exists()){
                    absolutePath=new File("");
                }
                File upload = new File(absolutePath.getAbsolutePath(),fileDate.getId()+"_"+fileDate.getName());
                if(!upload.exists()){
                    if(!upload.createNewFile()){
                        return ToJsonObject.getSuccessJSONObject(null);
                    }
                }
                FileOutputStream outputStream=new FileOutputStream(upload);
                outputStream.write(bs);
                outputStream.close();
                shopMapper.updateAddress(fileDate.getId(),fileDate.getName());
            }catch (IOException io){
                System.out.println(io);
            }
        }
        return ToJsonObject.getSuccessJSONObject(null);
    }

    /**
     * excel文件上传,补录处使用
     * @param file
     * @param fileDate
     * @return
     */
    @RequestMapping("/excel")
    @ResponseBody
    JSONObject excel(@Param("file") MultipartFile file, UploadFile fileDate){
        System.out.println(fileDate);
        if(Objects.nonNull(file)){
            if(!Objects.nonNull(fileDate.getId())&&StringUtils.isEmpty(fileDate.getName())){
                return ToJsonObject.getFailJSONObject(null);
            }
            if(!saveExcel(file,fileDate)){
                return ToJsonObject.getFailJSONObject(null);
            }
            Audit audit=new Audit();
            audit.setName(fileDate.getName());
            audit.setSubmitter(fileDate.getUser());
            audit.setIdTradeMark(fileDate.getId());
            auditMapper.shopAdd(audit);
            return ToJsonObject.getSuccessJSONObject();
        }
        return ToJsonObject.getFailJSONObject(null);
    }
    /**
     * excel文件上传2，录入处使用
     * @param file
     * @param fileDate
     * @return
     */
    @RequestMapping("/excel2")
    @ResponseBody
    JSONObject excel2(@Param("file") MultipartFile file, UploadFile fileDate){
        System.out.println(fileDate);
        if(Objects.nonNull(file)){
            if(!Objects.nonNull(fileDate.getId())&&StringUtils.isEmpty(fileDate.getName())){
                return ToJsonObject.getFailJSONObject(null);
            }
            Audit audit=new Audit();
            audit.setName(fileDate.getName());
            audit.setSubmitter(fileDate.getUser());
            Shop shop=shopMapper.getShopById(fileDate.getId());
            audit.setIdTradeMark(shop.getIdTradeMark());
            auditMapper.shopAdd(audit);
            fileDate.setId(audit.getIdTradeMark());
            if(!saveExcel(file,fileDate)){
                return ToJsonObject.getFailJSONObject(null);
            }
            return ToJsonObject.getSuccessJSONObject();
        }
        return ToJsonObject.getFailJSONObject(null);
    }

    /**
     * 获取待审核列表
     * @return
     */
    @GetMapping("/auditList")
    @ResponseBody
    JSONObject auditList(){
        List<AuditListRec> list=auditMapper.getAllShop().stream().map(x->{
            if(Objects.nonNull(x)){
                AuditListRec audit=new AuditListRec();
                BeanUtils.copyProperties(x,audit);
                audit.setCTime(Help.timeFormat(x.getCTime()));
                audit.setETime(Help.timeFormat(x.getETime()));
                TradeMark tradeMark=tradeMarkMapper.getById(x.getIdTradeMark());
                audit.setTradeMarkName(tradeMark.getTradeMarkName());
                if(x.getResult()==0){
                    audit.setResult("待审核");
                }
                return audit;
            }else{
                return null;
            }
        }).collect(Collectors.toList());
        return ToJsonObject.getSuccessJSONObject(list);
    }
    /**
     * 获取已审核列表
     * @return
     */
    @GetMapping("/auditList2")
    @ResponseBody
    JSONObject auditList2(){
        List<AuditListRec> list=auditMapper.getAllAuditShop().stream().map(x->{
            if(Objects.nonNull(x)){
                AuditListRec audit=new AuditListRec();
                BeanUtils.copyProperties(x,audit);
                audit.setCTime(Help.timeFormat(x.getCTime()));
                audit.setETime(Help.timeFormat(x.getETime()));
                TradeMark tradeMark=tradeMarkMapper.getById(x.getIdTradeMark());
                audit.setTradeMarkName(tradeMark.getTradeMarkName());
                if(x.getResult()==1){
                    audit.setResult("已审核");
                }
                return audit;
            }else{
                return null;
            }
        }).collect(Collectors.toList());
        return ToJsonObject.getSuccessJSONObject(list);
    }

    /**
     * 商家文档审核
     * @param id
     * @return
     */
    @GetMapping("/auditExcel")
    @ResponseBody
    JSONObject auditExcel(@Param("id") Integer id,@Param("name") String name){
        Audit audit=auditMapper.getById(id);
        auditMapper.updateUser(id,name);
        String path=audit.getIdTradeMark()+"_"+audit.getName();
        List<Map<String,String>>list=Lists.newArrayList();
        int right=0;
        int wrong=0;
        //读取excel到list
        if(excelToList(list,path)){
            for(Map<String,String>map:list){
                if(shopAdd(map,audit)){
                    right++;
                }else{
                    wrong++;
                }
            }
            //没有错误，审核通过
            if(wrong==0){
                auditMapper.updateStatus(audit.getId());
                return ToJsonObject.getSuccessJSONObject(null);
            }else{
                auditMapper.updateStatus(audit.getId());
                return ToJsonObject.getFailJSONObject2(null,"审核失败，通过"+right+"条记录，"+wrong+"未通过");
            }
        }else{
            return ToJsonObject.getFailJSONObject(null);
        }
    }

    /**
     * 商家资质审核
     * @param id
     * @return
     */
    @GetMapping("/auditPic")
    @ResponseBody
    JSONObject auditPic(@Param("id") Integer id){
       Shop shop=shopMapper.getShopById(id);
       String picName=shop.getPicAddress();
        System.out.println(picName);
        if(Objects.nonNull(picName)){
            if(picName.contains("资质")){
                shopMapper.updateResult(id,1);
                return ToJsonObject.getSuccessJSONObject(null);
            }else{
                shopMapper.updateResult(id,2);
                return ToJsonObject.getFailJSONObject2(null,"资质图片不符");
            }
        }else{
            shopMapper.updateResult(id,2);
            return ToJsonObject.getFailJSONObject2(null,"资质图片不存在");
        }
    }

    /**
     * 文件下载
     * @param id
     * @return
     */
    @GetMapping(value = "/download")
    @ResponseBody
    JSONObject download(@Param("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        Audit audit=auditMapper.getByIdAll(id);
        String path=audit.getIdTradeMark()+"_"+audit.getName();
        String time=Help.timeFormat(new Timestamp(new Date().getTime()));
        String fileName =  audit.getName().split("\\.")[0];
        List<Map<String,String>>list=Lists.newArrayList();
        //读服务器excel到list
        excelToList(list,path);
        XSSFWorkbook workbook = listToExcel(list);
        OutputStream os =  null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            response.reset();// 清空输出流
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") +time+ ".xls");
            // 定义输出类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            workbook.write(os);
            os.flush();
            os.close();
        }catch (Exception ex){
            System.out.println(ex);
            return ToJsonObject.getFailJSONObject2(null,"ex");
        }
        return ToJsonObject.getSuccessJSONObject();
    }
    /**
     * 图片下载
     * @param id
     * @return
     */
    @GetMapping(value = "/downloadPic")
    @ResponseBody
    JSONObject downloadPic(@Param("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        Shop shop=shopMapper.getShopById(id);
        String path=shop.getId()+"_"+shop.getPicAddress();
        String time=Help.timeFormat(new Timestamp(new Date().getTime()));
        String fileName =  shop.getPicAddress().split("\\.")[0];
        OutputStream os =  null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            response.reset();// 清空输出流
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") +time+ ".jpg");
            // 定义输出类型
            response.setContentType("application/octet-stream");
            File resourcePath = new File("");
            File file=new File(resourcePath.getAbsolutePath()+"/static/picture/",path);
            FileImageInputStream  fs = new FileImageInputStream(file);
            int streamLength = (int)fs.length();
            byte[] image = new byte[streamLength ];
            fs.read(image,0,streamLength );
            fs.close();
            os.write(image);
            os.flush();
            os.close();
        }catch (Exception ex){
            System.out.println(ex);
            return ToJsonObject.getFailJSONObject2(null,"ex");
        }
        return ToJsonObject.getSuccessJSONObject();
    }

    /**
     * 录入商家
     * @param map
     * @param audit
     * @return
     */
    private boolean shopAdd(Map<String,String> map,Audit audit){
        System.out.println("shopAdd----------");
        Shop shop=new Shop();
        String category=map.get("类别");
        if(!StringUtils.isEmpty(category)){
            CategoryEnum categoryEnum=CategoryEnum.CategoryByName(category);
            if(Objects.nonNull(categoryEnum)){
                shop.setCategory(categoryEnum.getIndex());
            }
        }
        String spName=map.get("商家名称");
        if(!StringUtils.isEmpty(spName)){
            shop.setSpName(spName);
        }
        String delivery=map.get("配送方式");
        if(!StringUtils.isEmpty(delivery)){
            DeliveryEnum deliveryEnum=DeliveryEnum.DeliveryByName(delivery);
            if(Objects.nonNull(deliveryEnum)){
                shop.setDelivery(deliveryEnum.getIndex());
            }
        }
        String leader=map.get("商家负责人");
        if(!StringUtils.isEmpty(leader)){
            shop.setLeader(leader);
        }
        String leaderPhone=map.get("联系电话");
        if(!StringUtils.isEmpty(leaderPhone)){
            shop.setLeaderPhone(leaderPhone);
        }
        String cusphone=map.get("客服电话");
        if(!StringUtils.isEmpty(cusphone)){
            shop.setCusPhone(cusphone);
        }
        String deliPrice=map.get("起送价");
        if(!StringUtils.isEmpty(deliPrice)){
            shop.setDeliPrice(Double.parseDouble(deliPrice));
        }
        String dispatch=map.get("配送费");
        if(!StringUtils.isEmpty(dispatch)){
            shop.setDispatch(Double.parseDouble(dispatch));
        }
        String worker=audit.getSubmitter();
        if(!StringUtils.isEmpty(worker)){
            shop.setWorker(worker);
        }
        Integer idTradeMark=audit.getIdTradeMark();
        shop.setIdTradeMark(idTradeMark);
        shop.setIsTradeMark(1);
        TradeMark tradeMark=tradeMarkMapper.getById(audit.getIdTradeMark());
        Shop shop2=shopMapper.getShopById(tradeMark.getShopId());
        shop.setAddress(shop2.getAddress());
        return 1==shopMapper.shopAdd(shop);
    }

    /**
     * 读取excel中内容到内存
     * @param list
     * @param path
     * @return
     */
    private boolean excelToList(List<Map<String,String>> list,String path){
        Workbook wb=null;
        Sheet sheet=null;
        Row row=null;
        try{
            File resourcePath = new File("");
            File excel=new File(resourcePath.getAbsolutePath()+"/static/excel/",path);
            InputStream io= new FileInputStream(excel);
            //读取excel
            wb=new XSSFWorkbook(io);
            sheet=wb.getSheetAt(0);
            int rowNum=sheet.getPhysicalNumberOfRows();
            row=sheet.getRow(0);
            int colNum=row.getPhysicalNumberOfCells();
            List<String> head=Lists.newArrayList();
            for(int i=0;i<colNum;i++){
                Cell cell=row.getCell(i);
                cell.setCellType(CellType.STRING);
                head.add(cell.getStringCellValue());
            }
            //存到list中
            for(int i=1;i<rowNum;i++){
                row=sheet.getRow(i);
                Map<String,String>map=new HashMap<>();
                for(int j=0;j<colNum;j++){
                    Cell cell=row.getCell(j);
                    if(Objects.nonNull(cell)){
                        cell.setCellType(CellType.STRING);
                        map.put(head.get(j),cell.getStringCellValue());
                    }
                }
                list.add(map);
            }
        }catch (Exception ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

    /**
     * 读取list内容到excel
     * @param list
     * @return
     */
    private XSSFWorkbook listToExcel(List<Map<String,String>> list){
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet;
        XSSFRow row;
        XSSFCell cell;
        // 建立excel文件
        sheet=workbook.createSheet();
        row=sheet.createRow(0);
        for(int i=0;i<Help.list.size();i++){
            cell=row.createCell(i);
            cell.setCellValue(Help.list.get(i));
        }
        int j=1;
        for(Map<String,String>map:list){
            row=sheet.createRow(j);
            for(int i=0;i<Help.list.size();i++){
                cell=row.createCell(i);
                cell.setCellValue(map.get(Help.list.get(i)));
            }
        }
        return workbook;
    }

    /**
     * 存储上传的excel
     * @param file
     * @param fileDate
     * @return
     */
    private Boolean saveExcel(MultipartFile file, UploadFile fileDate){
        try{
            byte bs[]=file.getBytes();
            File path = new File("");
            File absolutePath=new File(path.getAbsolutePath()+"/static/excel/");
            if(!absolutePath.exists()){
                absolutePath=new File("");
            }
            File upload = new File(absolutePath.getAbsolutePath(),fileDate.getId()+"_"+fileDate.getName());
            System.out.println(upload.getAbsolutePath());
            if(!upload.exists()){
                if(!upload.createNewFile()){
                    return false;
                }
            }
            FileOutputStream outputStream=new FileOutputStream(upload);
            outputStream.write(bs);
            outputStream.close();
        }catch (IOException io){
            System.out.println(io);
        }
        return true;
    }

}
