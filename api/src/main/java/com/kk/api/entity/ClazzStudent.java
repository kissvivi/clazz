package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "clazz_student")
public class ClazzStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_id")
    private Long cId;

    @Column(name = "stu_id")
    private Long stuId;

    private Date time;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return c_id
     */
    public Long getcId() {
        return cId;
    }

    /**
     * @param cId
     */
    public void setcId(Long cId) {
        this.cId = cId;
    }

    /**
     * @return stu_id
     */
    public Long getStuId() {
        return stuId;
    }

    /**
     * @param stuId
     */
    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}