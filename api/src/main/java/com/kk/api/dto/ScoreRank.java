package com.kk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kiss_vivi
 * @date 2021/5/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRank {

    private Integer countStu;

    private Integer s90100;
    private Integer s8089;
    private Integer s7079;
    private Integer s6069;
    private Integer s59;
}
