package com.dlrs.dlrsdemo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PattadarService {

    @Autowired
    private JdbcTemplate jdbc;
    public List<Map<String, Object>>  getAllSubdivisions() {
        String sql = "SELECT subdiv_code, loc_name \n" +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code != '00' \n" +
                "  AND cir_code = '00' \n" +
                "  AND mouza_pargona_code = '00' \n" +
                "  AND lot_no = '00' \n" +
                "  AND vill_townprt_code = '00000'";

        System.out.println(jdbc.queryForList(sql));

        return jdbc.queryForList(sql);

    }





    public List<Map<String, Object>> getAllCircles(String subdivCode) {

        String sql = "SELECT cir_code, loc_name " +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code != '00' " +
                "  AND mouza_pargona_code = '00' " +
                "  AND lot_no = '00' " +
                "  AND vill_townprt_code = '00000'";

        int subdiv = Integer.parseInt(subdivCode);
        System.out.println(jdbc.queryForList(sql, subdiv));

        return jdbc.queryForList(sql, subdiv);
    }

    public List<Map<String, Object>> getAllMouzas(String subdivCode, String circleCode) {
        String sql = "SELECT mouza_pargona_code, loc_name " +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code != '00' " +
                "  AND lot_no = '00' " +
                "  AND vill_townprt_code = '00000'";


        System.out.println(jdbc.queryForList(sql, subdivCode, circleCode));
        return jdbc.queryForList(sql, subdivCode, circleCode);
    }



    public List<Map<String, Object>> getAllLots(String subdivCode, String circleCode, String mouzaCode) {

        String sql = "SELECT lot_no, loc_name " +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no != '00' " +
                "  AND vill_townprt_code = '00000'";


        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode);
    }

    public List<Map<String, Object>> getAllVillages(String subdivCode, String circleCode, String mouzaCode, String lotNo) {

        String sql = "SELECT vill_townprt_code, loc_name " +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no = ? " +
                "  AND vill_townprt_code != '00000'";


        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo);
    }



    public List<Map<String, Object>> getAllPattaNo(String subdivCode, String circleCode, String mouzaCode, String lotNo, String villageNo) {
        String sql = "SELECT patta_no " +
                "FROM public.chitha_pattadar\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no = ? " +
                "  AND vill_townprt_code = ?";

        System.out.println(jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo));
        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo);
    }

    public List<Map<String, Object>> getAllPattaType(String subdivCode, String circleCode, String mouzaCode, String lotNo, String villageNo, String pattaNo) {
        String sql = "SELECT DISTINCT ON (c.patta_type_code) " +
                "       c.patta_type_code, " +
                "       p.patta_type AS patta_type " +
                "FROM public.chitha_pattadar c " +
                "JOIN public.patta_code p " +
                "  ON c.patta_type_code = p.type_code " +
                "WHERE c.dist_code = '07' " +
                "  AND c.subdiv_code = ? " +
                "  AND c.cir_code = ? " +
                "  AND c.mouza_pargona_code = ? " +
                "  AND c.lot_no = ? " +
                "  AND c.vill_townprt_code = ? " +
                "  AND c.patta_no = ? " +
                "ORDER BY c.patta_type_code, p.patta_type";

        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo);

    }

    public List<Map<String, Object>> getAllPattadars(String subdivCode, String circleCode, String mouzaCode, String lotNo, String villageNo, String pattaNo, String pattaType) {

        String sql = "SELECT pdar_id, pdar_name, pdar_father " +
                "FROM public.chitha_pattadar\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no = ? " +
                "  AND vill_townprt_code = ?" +
                "  AND patta_no = ?" +
                "  AND patta_type_code = ?" ;

        System.out.println(jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo, pattaType));
        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo, pattaType);
    }
}
