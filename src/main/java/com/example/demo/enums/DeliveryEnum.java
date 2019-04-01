package com.example.demo.enums;

public enum DeliveryEnum {
    SELF("商家自配",1), EXPRESS("美团专送",2), QUICK("美团快送",3), MIX("混合送",4);
    private String name;
    private Integer index;

    DeliveryEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }
   public static String DeliveryName(Integer index){
        for(DeliveryEnum ob :DeliveryEnum.values() ){
            if(ob.getIndex().equals(index)){
                return ob.getName();
            }
        }
        return "";
   }
    public static DeliveryEnum DeliveryByName(String name){
        for(DeliveryEnum ob :DeliveryEnum.values() ){
            if(ob.getName().equals(name)){
                return ob;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public Integer getIndex() {
        return index;
    }

}