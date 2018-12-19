package banger.domain.enumerate;

public enum MapConfigEnum {
    LNG(1,"lng","经度"),
    LAT(2,"lat", "维度"),
    ZOOM(3,"zoom", "缩放值");

    public final int value;
    public final String name;
    public final String description;

    MapConfigEnum(int value, String name, String description) {
        this.value = value;
        this.name = name;
        this.description = description;
    }

    public static String getNameByValue(int value){
        for(MapConfigEnum configEnum : MapConfigEnum.values()){
            if(configEnum.value == value){
                return configEnum.name;
            }
        }
        return null;
    }

    public static String getDescriptionByValue(int value){
        for(MapConfigEnum configEnum : MapConfigEnum.values()){
            if(configEnum.value == value){
                return configEnum.description;
            }
        }
        return null;
    }


}
