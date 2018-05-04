package com.fansin.message.tool;

import com.fansin.message.tool.core.AbstractLinkedData;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author z00461
 * @create 2018 /4/19
 * @since 1.0.0
 */
public class BaseLinkedData extends AbstractLinkedData {

    /**
     * Instantiates a new Base linked data.
     *
     * @param inwardKey  the inward key
     * @param foreignKey the foreign key
     */
    public BaseLinkedData(String inwardKey, String foreignKey) {
        super(inwardKey, foreignKey);
    }

    public String getRaw(){
        return this.getForeignKey()+','+this.getInwardKey();
    }
}