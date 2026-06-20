package cn.edu.training.kingbaseasset.repository;

import cn.edu.training.kingbaseasset.model.LookupItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LookupRepository {
    private final JdbcTemplate jdbcTemplate;

    public LookupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LookupItem> findDepartments() {
        return findLookupItems("SELECT id, name FROM kb_department ORDER BY name");
    }

    public List<LookupItem> findCategories() {
        return findLookupItems("SELECT id, name FROM kb_asset_category ORDER BY name");
    }

    public List<LookupItem> findSuppliers() {
        return findLookupItems("SELECT id, name FROM kb_supplier ORDER BY name");
    }

    private List<LookupItem> findLookupItems(String sql) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LookupItem(
                rs.getLong("id"),
                rs.getString("name")
        ));
    }
}
