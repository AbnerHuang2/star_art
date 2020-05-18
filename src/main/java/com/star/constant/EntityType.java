package com.star.constant;

/**
 * @Author Abner
 * @CreateDate 2020/5/1
 */
public enum EntityType {
    Entity_News(1),
    Entity_Course(2),
    Entity_User(3);

    int type;

    EntityType(int type){
        this.type =type;
    }

    public int getType(){
        return type;
    }

}
