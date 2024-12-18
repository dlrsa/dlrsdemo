package com.dlrs.dlrsdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqDTO {
    private String district_id;
    private String subdiv_id;
    private String circle_id;
    private String mouza_id;
    private String lot_id;
    private String village_id;
    private String pattano_id;
    private String pattatype_id;
    private String pdar_name;
    private String pdar_father;
    private String dag_area_b;
    private String dag_area_k;
    private String dag_area_lc;
    private String dag_area_type;
    private String dag_no;
}
