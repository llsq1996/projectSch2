package com.example.demo.enums;

public enum CategoryEnum {
    FLOWER("药品鲜花",1), FRUIT("水果",2), SUPERMAR("超市",3), STORE("便利店",4);
    private String name;
    private Integer index;

    CategoryEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }
   public static String CategoryName(Integer index){
        for(CategoryEnum ob :CategoryEnum.values() ){
            if(ob.getIndex().equals(index)){
                return ob.getName();
            }
        }
        return "";
   }
    public String getName() {
        return name;
    }
    public Integer getIndex() {
        return index;
    }

}