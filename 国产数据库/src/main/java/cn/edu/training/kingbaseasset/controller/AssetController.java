package cn.edu.training.kingbaseasset.controller;

import cn.edu.training.kingbaseasset.model.Asset;
import cn.edu.training.kingbaseasset.model.AssetForm;
import cn.edu.training.kingbaseasset.model.StatusOption;
import cn.edu.training.kingbaseasset.repository.AssetRepository;
import cn.edu.training.kingbaseasset.repository.LookupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/assets")
public class AssetController {
    private final AssetRepository assetRepository;
    private final LookupRepository lookupRepository;

    public AssetController(AssetRepository assetRepository, LookupRepository lookupRepository) {
        this.assetRepository = assetRepository;
        this.lookupRepository = lookupRepository;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status,
            Model model
    ) {
        model.addAttribute("assets", assetRepository.findAll(keyword, categoryId, status));
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedStatus", status);
        addLookups(model);
        return "assets/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("assetForm", new AssetForm());
        model.addAttribute("pageTitle", "新增教学设备");
        model.addAttribute("submitText", "保存资产");
        model.addAttribute("formAction", "/assets");
        addLookups(model);
        return "assets/form";
    }

    @PostMapping
    public String create(
            @ModelAttribute AssetForm assetForm,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = validate(assetForm);
        if (!errors.isEmpty()) {
            return formWithErrors(model, assetForm, "新增教学设备", "保存资产", "/assets", errors);
        }
        try {
            assetRepository.create(assetForm);
            redirectAttributes.addFlashAttribute("message", "资产新增成功");
            return "redirect:/assets";
        } catch (DataIntegrityViolationException ex) {
            return formWithErrors(model, assetForm, "新增教学设备", "保存资产", "/assets",
                    List.of("资产编号不能重复，请更换后重新提交。"));
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Asset asset = assetRepository.findById(id).orElse(null);
        if (asset == null) {
            redirectAttributes.addFlashAttribute("error", "未找到要修改的资产记录");
            return "redirect:/assets";
        }
        model.addAttribute("assetForm", AssetForm.fromAsset(asset));
        model.addAttribute("pageTitle", "修改教学设备");
        model.addAttribute("submitText", "更新资产");
        model.addAttribute("formAction", "/assets/" + id + "/edit");
        addLookups(model);
        return "assets/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @ModelAttribute AssetForm assetForm,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = validate(assetForm);
        if (!errors.isEmpty()) {
            return formWithErrors(model, assetForm, "修改教学设备", "更新资产", "/assets/" + id + "/edit", errors);
        }
        try {
            assetRepository.update(id, assetForm);
            redirectAttributes.addFlashAttribute("message", "资产信息已更新");
            return "redirect:/assets";
        } catch (DataIntegrityViolationException ex) {
            return formWithErrors(model, assetForm, "修改教学设备", "更新资产", "/assets/" + id + "/edit",
                    List.of("资产编号不能与其他记录重复。"));
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        assetRepository.delete(id);
        redirectAttributes.addFlashAttribute("message", "资产记录已删除");
        return "redirect:/assets";
    }

    private String formWithErrors(Model model, AssetForm form, String title, String submitText, String action, List<String> errors) {
        model.addAttribute("assetForm", form);
        model.addAttribute("pageTitle", title);
        model.addAttribute("submitText", submitText);
        model.addAttribute("formAction", action);
        model.addAttribute("errors", errors);
        addLookups(model);
        return "assets/form";
    }

    private List<String> validate(AssetForm form) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(form.getAssetCode())) {
            errors.add("请填写资产编号。");
        }
        if (!StringUtils.hasText(form.getAssetName())) {
            errors.add("请填写资产名称。");
        }
        if (form.getCategoryId() == null) {
            errors.add("请选择设备分类。");
        }
        if (form.getDepartmentId() == null) {
            errors.add("请选择使用部门。");
        }
        if (form.getSupplierId() == null) {
            errors.add("请选择供应商。");
        }
        if (!StringUtils.hasText(form.getStatus())) {
            errors.add("请选择资产状态。");
        }
        if (form.getOriginalValue() != null && form.getOriginalValue().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("原值不能为负数。");
        }
        return errors;
    }

    private void addLookups(Model model) {
        model.addAttribute("categories", lookupRepository.findCategories());
        model.addAttribute("departments", lookupRepository.findDepartments());
        model.addAttribute("suppliers", lookupRepository.findSuppliers());
        model.addAttribute("statuses", statuses());
    }

    private List<StatusOption> statuses() {
        return List.of(
                new StatusOption("IN_STOCK", "在库"),
                new StatusOption("IN_USE", "使用中"),
                new StatusOption("REPAIR", "维修中"),
                new StatusOption("SCRAPPED", "已报废")
        );
    }
}
