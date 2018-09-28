package net.cdsunrise.wm.zuul.model;

public class Resource {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 资源类型（1菜单、2事件、3授权）
     */
    private Integer type;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 链接
     */
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
