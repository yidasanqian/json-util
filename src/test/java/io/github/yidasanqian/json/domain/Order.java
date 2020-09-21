package io.github.yidasanqian.json.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class Order {

    private long id;

    private int traceNo;

    private String createAt;

    private Date updateAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(int traceNo) {
        this.traceNo = traceNo;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", traceNo=" + traceNo +
                ", createAt='" + createAt + '\'' +
                ", updateAt=" + updateAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
