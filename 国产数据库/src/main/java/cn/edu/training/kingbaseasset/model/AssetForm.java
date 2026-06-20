package cn.edu.training.kingbaseasset.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssetForm {
    private String assetCode;
    private String assetName;
    private Long categoryId;
    private Long departmentId;
    private Long supplierId;
    private LocalDate purchaseDate;
    private BigDecimal originalValue;
    private String status = "IN_STOCK";
    private String keeper;
    private String remark;

    public static AssetForm fromAsset(Asset asset) {
        AssetForm form = new AssetForm();
        form.setAssetCode(asset.getAssetCode());
        form.setAssetName(asset.getAssetName());
        form.setCategoryId(asset.getCategoryId());
        form.setDepartmentId(asset.getDepartmentId());
        form.setSupplierId(asset.getSupplierId());
        form.setPurchaseDate(asset.getPurchaseDate());
        form.setOriginalValue(asset.getOriginalValue());
        form.setStatus(asset.getStatus());
        form.setKeeper(asset.getKeeper());
        form.setRemark(asset.getRemark());
        return form;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(BigDecimal originalValue) {
        this.originalValue = originalValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
