package com.dlrs.dlrsdemo.service;
import com.dlrs.dlrsdemo.dto.ReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PattadarService {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Map<String, Object>>  getAllSubdivisions() {
        String sql = "SELECT subdiv_code, loc_name \n" +
                "FROM public.location\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code != '00' \n" +
                "  AND cir_code = '00' \n" +
                "  AND mouza_pargona_code = '00' \n" +
                "  AND lot_no = '00' \n" +
                "  AND vill_townprt_code = '00000'";

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

//        int subdiv = Integer.parseInt(subdivCode);
        return jdbc.queryForList(sql, subdivCode);
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
        String sql = "SELECT Distinct patta_no " +
                "FROM public.chitha_pattadar\n" +
                "WHERE dist_code = '07' \n" +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no = ? " +
                "  AND vill_townprt_code = ?";

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

        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo, pattaType);
    }


    public List<Map<String, Object>> getAllLandClass() {
        String sql = "SELECT class_code,land_type FROM \"public\".\"landclass_code\"";
        return jdbc.queryForList(sql);
    }

    public void addChithaPattadar(ReqDTO reqDTO, Integer count) {
        String sql = "INSERT INTO \"public\".\"chitha_pattadar\" (" +
                "\"dist_code\", \"subdiv_code\", \"cir_code\", \"mouza_pargona_code\", \"lot_no\", " +
                "\"vill_townprt_code\", \"pdar_id\", \"patta_no\", \"patta_type_code\", \"pdar_name\", " +
                "\"pdar_father\", \"user_code\", \"date_entry\", \"operation\", \"jama_yn\") " +
                "VALUES (:district_id, :subdiv_id, :circle_id, :mouza_id, :lot_id, " +
                ":village_id, :pdar_id, :pattano_id, :pattatype_id, :pdar_name, :pdar_father, 'test', :date_entry, 'E' , 'y')";

        // Create a map of parameters to pass to the query
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("district_id", reqDTO.getDistrict_id());
        params.addValue("subdiv_id", reqDTO.getSubdiv_id());
        params.addValue("circle_id", reqDTO.getCircle_id());
        params.addValue("mouza_id", reqDTO.getMouza_id());
        params.addValue("lot_id", reqDTO.getLot_id());
        params.addValue("village_id", reqDTO.getVillage_id());
        params.addValue("pattano_id", reqDTO.getPattano_id());
        params.addValue("pattatype_id", reqDTO.getPattatype_id());
        params.addValue("pdar_name", reqDTO.getPdar_name());
        params.addValue("pdar_father", reqDTO.getPdar_father());
        params.addValue("date_entry", new Date());

        params.addValue("pdar_id", count, Types.INTEGER);

        // Execute the insert query
        jdbcTemplate.update(sql, params);
    }
    public void addChithaDagPattadar(ReqDTO reqDTO, Integer count) {
        String sql = "INSERT INTO \"public\".\"chitha_dag_pattadar\" (" +
                "\"dist_code\", \"subdiv_code\", \"cir_code\", \"mouza_pargona_code\", \"lot_no\", " +
                "\"vill_townprt_code\", \"dag_no\", \"dag_por_b\", \"dag_por_k\", \"dag_por_lc\", \"dag_por_g\", " +
                "\"pdar_id\", \"patta_no\", \"patta_type_code\", " +
                "\"user_code\", \"date_entry\", \"operation\", \"jama_yn\") " +
                "VALUES (:district_id, :subdiv_id, :circle_id, :mouza_id, :lot_id, " +
                ":village_id, :dag_no, :dag_por_b, :dag_por_k, :dag_por_lc, :dag_por_g, " +
                ":pdar_id, :pattano_id, :pattatype_id,  'test', :date_entry, 'E', 'y')";

        // Create a map of parameters to pass to the query
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("district_id", reqDTO.getDistrict_id());
        params.addValue("subdiv_id", reqDTO.getSubdiv_id());
        params.addValue("circle_id", reqDTO.getCircle_id());
        params.addValue("mouza_id", reqDTO.getMouza_id());
        params.addValue("lot_id", reqDTO.getLot_id());
        params.addValue("village_id", reqDTO.getVillage_id());
        params.addValue("dag_no", reqDTO.getDag_no());
        params.addValue("dag_por_b", reqDTO.getDag_area_b(),Types.INTEGER);
        params.addValue("dag_por_k", reqDTO.getDag_area_k(),Types.INTEGER);
        params.addValue("dag_por_lc", reqDTO.getDag_area_lc(),Types.INTEGER);
        params.addValue("dag_por_g", 10);
        params.addValue("pattano_id", reqDTO.getPattano_id());
        params.addValue("pattatype_id", reqDTO.getPattatype_id());
        params.addValue("pdar_name", reqDTO.getPdar_name());
        params.addValue("pdar_father", reqDTO.getPdar_father());
        params.addValue("date_entry", new Date());

        params.addValue("pdar_id", count, Types.INTEGER);

        // Execute the insert query
        jdbcTemplate.update(sql, params);
    }


    public void addChithaBasic(ReqDTO reqDTO, Integer count) {
        String sql = "INSERT INTO \"public\".\"chitha_basic\" (" +
                "\"dist_code\", \"subdiv_code\", \"cir_code\", \"mouza_pargona_code\", \"lot_no\", " +
                "\"vill_townprt_code\", \"dag_no\", \"dag_area_b\", \"dag_area_k\", \"dag_area_lc\", \"dag_area_g\",\"dag_area_kr\", " +
                "\"patta_no\", \"patta_type_code\", " +
                "\"user_code\", \"date_entry\", \"operation\", \"jama_yn\") " +
                "VALUES (:district_id, :subdiv_id, :circle_id, :mouza_id, :lot_id, " +
                ":village_id, :dag_no, :dag_por_b, :dag_por_k, :dag_por_lc, :dag_por_g,:dag_por_kr, " +
                ":pattano_id, :pattatype_id,  'test', :date_entry, 'E', 'y')";

        // Create a map of parameters to pass to the query
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("district_id", reqDTO.getDistrict_id());
        params.addValue("subdiv_id", reqDTO.getSubdiv_id());
        params.addValue("circle_id", reqDTO.getCircle_id());
        params.addValue("mouza_id", reqDTO.getMouza_id());
        params.addValue("lot_id", reqDTO.getLot_id());
        params.addValue("village_id", reqDTO.getVillage_id());
        params.addValue("dag_no", reqDTO.getDag_no());
        params.addValue("dag_por_b", reqDTO.getDag_area_b(),Types.INTEGER);
        params.addValue("dag_por_k", reqDTO.getDag_area_k(),Types.INTEGER);
        params.addValue("dag_por_lc", reqDTO.getDag_area_lc(),Types.INTEGER);
        params.addValue("dag_por_g", 10);
        params.addValue("dag_por_kr", 0);
        params.addValue("pattano_id", reqDTO.getPattano_id());
        params.addValue("pattatype_id", reqDTO.getPattatype_id());
        params.addValue("pdar_name", reqDTO.getPdar_name());
        params.addValue("pdar_father", reqDTO.getPdar_father());
        params.addValue("date_entry", new Date());

        params.addValue("pdar_id", count, Types.INTEGER);

        // Execute the insert query
        jdbcTemplate.update(sql, params);
    }

    public Integer getAllPattadarsCount(ReqDTO reqDTO) {

        String sql = "SELECT count(*) " +
                "FROM public.chitha_pattadar " +
                "WHERE dist_code = '07' " +
                "  AND subdiv_code = ? " +
                "  AND cir_code = ? " +
                "  AND mouza_pargona_code = ? " +
                "  AND lot_no = ? " +
                "  AND vill_townprt_code = ? " +
                "  AND patta_no = ? " +
                "  AND patta_type_code = ?";

        // Use queryForObject to fetch a single value
        Integer count = jdbc.queryForObject(
                sql,
                new Object[]{
                        reqDTO.getSubdiv_id(),
                        reqDTO.getCircle_id(),
                        reqDTO.getMouza_id(),
                        reqDTO.getLot_id(),
                        reqDTO.getVillage_id(),
                        reqDTO.getPattano_id(),
                        reqDTO.getPattatype_id()
                },
                Integer.class
        );

        return count != null ? count : 0;
    }

    public List<Map<String, Object>> getAllDagNo(String subdivCode, String circleCode, String mouzaCode, String lotNo, String villageNo, String pattaNo, String pattaType) {

        String sql = "SELECT DISTINCT dag_no FROM \"public\".\"chitha_dag_pattadar\" WHERE dist_code='07' AND subdiv_code = ? AND cir_code = ? AND mouza_pargona_code = ? AND lot_no = ? AND vill_townprt_code = ? AND patta_no = ? AND patta_type_code = ?" ;

        return jdbc.queryForList(sql, subdivCode, circleCode, mouzaCode, lotNo, villageNo, pattaNo, pattaType);
    }


}
