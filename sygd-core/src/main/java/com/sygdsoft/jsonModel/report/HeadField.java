package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2016-11-16.
 * szTable的Fields数组，后台生成实体类
 * static,default,float,boolean由于冲突无法在后台体现
 */
public class HeadField {
    private String name;//
    private String id;//
    private String exp;//表达式，有exp就不能有id了
    private String copy;//新增之后该字段值是否默认保留，例如添加房号的时候，房类默认保留
    private String selectId;//是否启用下拉列表
    private String freeSelect;//使用sz-select方式读取list，适用于某些大数据，例如菜品太多了，定义套餐时就用这个
    private String width;//宽度
    private String notNull;//输入验证非空
    private String filter;//标题过滤插件，如果该值为date，则弹出时间段选择，否则弹出下拉列表//可选值：date,list,input
    private String filterFather;//父级过滤，属性值为父对象id，父对象变化时，可选值下拉列表也会经过重新筛选，注意，每一个field只能添加一个父级菜单
    private String filterInit;//过滤器初始值
    private String filterContent;//内容过滤器，例如营业部门切换时可选的销售点也要跟着切换
    private String filterContentId;//内容过滤器条件
    private String date;//采用日历控件，如果其中值为short，则不显示时间
    private String length;//字段长度必须等于他
    private String asc;//升序排列，属性值为第几个
    private String desc;//降序排列，属性值为第几个
    private String sum;//加起来，最后出现在表末尾
    private String selectListField;//下拉框表如果是个对象数组，则该值为对应的字段
    private String popChoose;//下拉列表为复选，因此会弹出一个复选框
    private String itemChange;//配合上边的相同参数，看看哪些域在改变时需要调用外部方法

    public HeadField() {
    }

    public HeadField(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getFreeSelect() {
        return freeSelect;
    }

    public void setFreeSelect(String freeSelect) {
        this.freeSelect = freeSelect;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilterFather() {
        return filterFather;
    }

    public void setFilterFather(String filterFather) {
        this.filterFather = filterFather;
    }

    public String getFilterInit() {
        return filterInit;
    }

    public void setFilterInit(String filterInit) {
        this.filterInit = filterInit;
    }

    public String getFilterContent() {
        return filterContent;
    }

    public void setFilterContent(String filterContent) {
        this.filterContent = filterContent;
    }

    public String getFilterContentId() {
        return filterContentId;
    }

    public void setFilterContentId(String filterContentId) {
        this.filterContentId = filterContentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getSelectListField() {
        return selectListField;
    }

    public void setSelectListField(String selectListField) {
        this.selectListField = selectListField;
    }

    public String getPopChoose() {
        return popChoose;
    }

    public void setPopChoose(String popChoose) {
        this.popChoose = popChoose;
    }

    public String getItemChange() {
        return itemChange;
    }

    public void setItemChange(String itemChange) {
        this.itemChange = itemChange;
    }
}
