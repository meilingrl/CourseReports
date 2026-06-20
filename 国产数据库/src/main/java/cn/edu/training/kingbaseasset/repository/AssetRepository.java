package cn.edu.training.kingbaseasset.repository;

import cn.edu.training.kingbaseasset.model.Asset;
import cn.edu.training.kingbaseasset.model.AssetForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AssetRepository {
    private final JdbcTemplate jdbcTemplate;

    public AssetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Asset> findAll(String keyword, Long categoryId, String status) {
        StringBuilder sql = new StringBuilder("""
                SELECT a.id, a.asset_code, a.asset_name, a.category_id, c.name AS category_name,
                       a.department_id, d.name AS department_name, a.supplier_id, s.name AS supplier_name,
                       a.purchase_date, a.original_value, a.status, a.keeper, a.remark,
                       a.created_at, a.updated_at
                  FROM kb_asset a
                  JOIN kb_asset_category c ON c.id = a.category_id
                  JOIN kb_department d ON d.id = a.department_id
                  JOIN kb_supplier s ON s.id = a.supplier_id
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (StringUtils.hasText(keyword)) {
            sql.append("""
                     AND (LOWER(a.asset_code) LIKE ?
                          OR LOWER(a.asset_name) LIKE ?
                          OR LOWER(d.name) LIKE ?
                          OR LOWER(a.keeper) LIKE ?)
                    """);
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
        }
        if (categoryId != null) {
            sql.append(" AND a.category_id = ?");
            params.add(categoryId);
        }
        if (StringUtils.hasText(status)) {
            sql.append(" AND a.status = ?");
            params.add(status);
        }

        sql.append(" ORDER BY a.updated_at DESC, a.id DESC");
        return jdbcTemplate.query(sql.toString(), assetMapper(), params.toArray());
    }

    public Optional<Asset> findById(Long id) {
        List<Asset> assets = jdbcTemplate.query("""
                SELECT a.id, a.asset_code, a.asset_name, a.category_id, c.name AS category_name,
                       a.department_id, d.name AS department_name, a.supplier_id, s.name AS supplier_name,
                       a.purchase_date, a.original_value, a.status, a.keeper, a.remark,
                       a.created_at, a.updated_at
                  FROM kb_asset a
                  JOIN kb_asset_category c ON c.id = a.category_id
                  JOIN kb_department d ON d.id = a.department_id
                  JOIN kb_supplier s ON s.id = a.supplier_id
                 WHERE a.id = ?
                """, assetMapper(), id);
        return assets.stream().findFirst();
    }

    public void create(AssetForm form) {
        jdbcTemplate.update("""
                INSERT INTO kb_asset (
                    asset_code, asset_name, category_id, department_id, supplier_id,
                    purchase_date, original_value, status, keeper, remark,
                    created_at, updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                """,
                trim(form.getAssetCode()),
                trim(form.getAssetName()),
                form.getCategoryId(),
                form.getDepartmentId(),
                form.getSupplierId(),
                form.getPurchaseDate(),
                form.getOriginalValue(),
                form.getStatus(),
                trim(form.getKeeper()),
                trim(form.getRemark()));
    }

    public void update(Long id, AssetForm form) {
        jdbcTemplate.update("""
                UPDATE kb_asset
                   SET asset_code = ?,
                       asset_name = ?,
                       category_id = ?,
                       department_id = ?,
                       supplier_id = ?,
                       purchase_date = ?,
                       original_value = ?,
                       status = ?,
                       keeper = ?,
                       remark = ?,
                       updated_at = CURRENT_TIMESTAMP
                 WHERE id = ?
                """,
                trim(form.getAssetCode()),
                trim(form.getAssetName()),
                form.getCategoryId(),
                form.getDepartmentId(),
                form.getSupplierId(),
                form.getPurchaseDate(),
                form.getOriginalValue(),
                form.getStatus(),
                trim(form.getKeeper()),
                trim(form.getRemark()),
                id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM kb_asset WHERE id = ?", id);
    }

    private RowMapper<Asset> assetMapper() {
        return new RowMapper<>() {
            @Override
            public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
                Asset asset = new Asset();
                asset.setId(rs.getLong("id"));
                asset.setAssetCode(rs.getString("asset_code"));
                asset.setAssetName(rs.getString("asset_name"));
                asset.setCategoryId(rs.getLong("category_id"));
                asset.setCategoryName(rs.getString("category_name"));
                asset.setDepartmentId(rs.getLong("department_id"));
                asset.setDepartmentName(rs.getString("department_name"));
                asset.setSupplierId(rs.getLong("supplier_id"));
                asset.setSupplierName(rs.getString("supplier_name"));
                asset.setPurchaseDate(rs.getObject("purchase_date", java.time.LocalDate.class));
                asset.setOriginalValue(rs.getBigDecimal("original_value"));
                asset.setStatus(rs.getString("status"));
                asset.setKeeper(rs.getString("keeper"));
                asset.setRemark(rs.getString("remark"));
                asset.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
                asset.setUpdatedAt(rs.getObject("updated_at", java.time.LocalDateTime.class));
                return asset;
            }
        };
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
