package com.ruoyi.flowable.survey;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.survey.SurveyPaper;
import com.ruoyi.project.system.service.survey.ISurveyPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调查问卷模板Controller
 * 
 * @author 2y
 * @date 2021-03-21
 */
@RestController
@RequestMapping("/survey/paper")
public class SurveyPaperController extends BaseController
{
    @Autowired
    private ISurveyPaperService surveyPaperService;

    /**
     * 查询调查问卷模板列表
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:list')")
    @GetMapping("/list")
    public TableDataInfo list(SurveyPaper surveyPaper)
    {
        startPage();
        List<SurveyPaper> list = surveyPaperService.selectSurveyPaperList(surveyPaper);
        return getDataTable(list);
    }

    /**
     * 导出调查问卷模板列表
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:export')")
    @Log(title = "调查问卷模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SurveyPaper surveyPaper)
    {
        List<SurveyPaper> list = surveyPaperService.selectSurveyPaperList(surveyPaper);
        ExcelUtil<SurveyPaper> util = new ExcelUtil<SurveyPaper>(SurveyPaper.class);
        return util.exportExcel(list, "paper");
    }

    /**
     * 获取调查问卷模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:query')")
    @GetMapping(value = { "/", "/{id}" })
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        if (StringUtils.isNull(id)) {
            return AjaxResult.success(surveyPaperService.selectPaperAll());
        }
        return AjaxResult.success(surveyPaperService.selectSurveyPaperById(id));
    }

    /**
     * 获取调查问卷模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:query')")
    @GetMapping("/detail")
    public AjaxResult getDetail(Long id)
    {
        return AjaxResult.success(surveyPaperService.selectSurveyPaperDetail(id));
    }

    /**
     * 新增调查问卷模板
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:add')")
    @Log(title = "调查问卷模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SurveyPaper surveyPaper)
    {
        return toAjax(surveyPaperService.insertSurveyPaper(surveyPaper));
    }

    /**
     * 修改调查问卷模板
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:edit')")
    @Log(title = "调查问卷模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SurveyPaper surveyPaper)
    {
        return toAjax(surveyPaperService.updateSurveyPaper(surveyPaper));
    }

    /**
     * 删除调查问卷模板
     */
    @PreAuthorize("@ss.hasPermi('survey:paper:remove')")
    @Log(title = "调查问卷模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(surveyPaperService.deleteSurveyPaperByIds(ids));
    }
}
