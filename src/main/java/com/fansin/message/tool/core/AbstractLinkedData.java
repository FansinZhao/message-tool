package com.fansin.message.tool.core;

/**
 * 〈一句话功能简述〉<br>
 * 〈关联数据〉
 *
 * @author z00461
 * @create 2018 /4/19
 * @since 1.0.0
 */
public abstract class AbstractLinkedData {

    /**
     * 内部关联数据
     */
    private String inwardKey;
    /**
     * 外部关联数据
     */
    private String foreignKey;

    /**
     * Instantiates a new Abstract linked data.
     *
     * @param inwardKey  the inward key
     * @param foreignKey the foreign key
     */
    public AbstractLinkedData(String inwardKey, String foreignKey) {
        this.inwardKey = inwardKey;
        this.foreignKey = foreignKey;
    }

    /**
     * Instantiates a new Abstract linked data.
     */
    public AbstractLinkedData() {
    }

    /**
     * Gets inward key.
     *
     * @return the inward key
     */
    public String getInwardKey() {
        return inwardKey;
    }

    /**
     * Sets inward key.
     *
     * @param inwardKey the inward key
     */
    public void setInwardKey(String inwardKey) {
        this.inwardKey = inwardKey;
    }

    /**
     * Gets foreign key.
     *
     * @return the foreign key
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * Sets foreign key.
     *
     * @param foreignKey the foreign key
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Override
    public String toString() {
        return "AbstractLinkedData{" +
                "inwardKey='" + inwardKey + '\'' +
                ", foreignKey='" + foreignKey + '\'' +
                '}';
    }
}
