package com.zhengqing.common.db.util;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.ProjectConstant;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.common.base.util.MyStringUtil;
import com.zhengqing.common.db.enums.DbColumnJavaTypeEnum;
import com.zhengqing.common.db.model.bo.GeneratorCodeColumnInfoBO;
import com.zhengqing.common.db.model.bo.GeneratorCodeTemplateFileBO;
import com.zhengqing.common.db.model.vo.DbTableColumnListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 代码生成工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/21 2:16 下午
 */
@Slf4j
public class GenerateCodeUtil {

    /**
     * 处理模板数据
     *
     * @param moduleName         模块名
     * @param columnInfo         表字段信息
     * @param packageNameInfoMap 包信息
     * @param queryColumnList    可检索字段信息
     * @return 模板数据
     * @author zhengqingya
     * @date 2020/11/15 21:42
     */
    public static Map<String, Object> handleTplData(String moduleName,
                                                    DbTableColumnListVO columnInfo,
                                                    Map<String, String> packageNameInfoMap,
                                                    List<String> queryColumnList) {
        Assert.isTrue(!CollectionUtils.isEmpty(columnInfo.getColumnInfoList()), "数据库表字段信息丢失!");
        // 数据模型(这里使用map类型) -- [数据模型可以是List、Map对象 注意:Map类型的key必须是String类型]
        Map<String, Object> templateDataMap = Maps.newHashMap();
        String tableName = columnInfo.getTableName();
        // 表名驼峰式处理
        String entityName = tableName.startsWith("t_") ? tableName.substring("t_".length()) : tableName;
        String entityNameLower = MyStringUtil.dbStrToHumpLower(entityName);
        String entityNameUpper = MyStringUtil.dbStrToHumpUpper(entityName);
        templateDataMap.put("tableName", tableName);
        templateDataMap.put("tableNameAbbr", MyStringUtil.tableNameToAbbr(tableName));
        templateDataMap.put("vueApiName", tableName.startsWith("t_") ? tableName.substring("t_".length()) : tableName);
        templateDataMap.put("entityNameLower", entityNameLower);
        templateDataMap.put("entityNameUpper", entityNameUpper);
        templateDataMap.put("entity", entityNameUpper);
        templateDataMap.put("moduleName", moduleName);


        // 获取表字段信息
        // 表注释
        String tableComment = columnInfo.getTableComment();
        templateDataMap.put("tableComment", tableComment);
        List<DbTableColumnListVO.ColumnInfo> columnInfoList = columnInfo.getColumnInfoList();
        if (!CollectionUtils.isEmpty(columnInfoList)) {
            List<GeneratorCodeColumnInfoBO> columnInfoBOList = Lists.newArrayList();
            List<GeneratorCodeColumnInfoBO> queryColumnInfoBOList = Lists.newArrayList();
            // 判断是否处理可检索字段
            boolean ifHandleQueryColumn = !CollectionUtils.isEmpty(queryColumnList);
            // 主键-Java字段名首字母小写
            String primaryColumnNameJavaLower = "";
            // 主键-Java字段名首字母大写
            String primaryColumnNameJavaUpper = "";
            // 主键-java字段数据类型
            String primaryColumnTypeJava = "";
            for (DbTableColumnListVO.ColumnInfo e : columnInfoList) {
                String columnNameDb = e.getColumnName();
                String columnComment = e.getColumnComment();
                String columnType = e.getColumnType();
                boolean ifAutoIncrement = e.isIfAutoIncrement();
                boolean ifPrimaryKey = e.isIfPrimaryKey();
                boolean ifNullAble = e.isIfNullAble();
                String columnNameJavaLower = MyStringUtil.dbStrToHumpLower(columnNameDb);
                String columnNameJavaUpper = MyStringUtil.dbStrToHumpUpper(columnNameDb);
                String columnTypeJava = DbColumnJavaTypeEnum.getEnum(columnType).getColumnTypeJava();

                // 主键相关处理
                if (ifPrimaryKey) {
                    primaryColumnNameJavaLower = columnNameJavaLower;
                    primaryColumnNameJavaUpper = columnNameJavaUpper;
                    primaryColumnTypeJava = columnTypeJava;
                }

                // 封装数据库字段信息
                GeneratorCodeColumnInfoBO generatorCodeColumnInfoBO = GeneratorCodeColumnInfoBO.builder()
                        .columnNameDb(columnNameDb)
                        .columnNameJavaLower(columnNameJavaLower)
                        .columnNameJavaUpper(columnNameJavaUpper)
                        .columnComment(columnComment)
                        .columnType(columnType)
                        .columnTypeJava(columnTypeJava)
                        .ifNullAble(ifNullAble)
                        .ifPrimaryKey(ifPrimaryKey)
                        .ifAutoIncrement(ifAutoIncrement)
                        .build();
                columnInfoBOList.add(generatorCodeColumnInfoBO);

                // 封装可检索字段信息
                if (ifHandleQueryColumn && queryColumnList.contains(columnNameDb)) {
                    queryColumnInfoBOList.add(generatorCodeColumnInfoBO);
                }
            }
            templateDataMap.put("primaryColumnNameJavaLower", primaryColumnNameJavaLower);
            templateDataMap.put("primaryColumnNameJavaUpper", primaryColumnNameJavaUpper);
            templateDataMap.put("primaryColumnTypeJava", primaryColumnTypeJava);
            templateDataMap.put("columnInfoList", columnInfoBOList);
            templateDataMap.put("queryColumnInfoList", queryColumnInfoBOList);
        }

        // 包信息处理
        HashMap<String, Object> packageMap = Maps.newHashMap();
        packageNameInfoMap.forEach((key, value) -> {
            String[] packageNameArray = value.split("\\.");
            String packageName = packageNameArray[packageNameArray.length - 1];
            packageMap.put(packageName, value);
        });
        templateDataMap.put("package", packageMap);

        String dateStr = MyDateUtil.dateToStr(new Date(), MyDateUtil.MINUTE_JAVA_AUTHOR_FORMAT);
        templateDataMap.put("date", dateStr);
        return templateDataMap;
    }


    /**
     * 模板数据生成
     *
     * @param templateFileInfoList 模板文件信息
     * @param templateDataMap      模板数据
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 21:05
     */
    public static void generateTplFileData(List<GeneratorCodeTemplateFileBO> templateFileInfoList,
                                           Map<String, Object> templateDataMap) {
        // 循环生成数据文件
        for (GeneratorCodeTemplateFileBO templateFileInfo : templateFileInfoList) {
            String templateContent = templateFileInfo.getTemplateContent();
            String fileName = templateFileInfo.getFileName();
            String generateFileSuffix = templateFileInfo.getGenerateFileSuffix();
            String templateRePackage = templateFileInfo.getTemplateRePackage();

            // 待生成文件名 ex: `test.java`
            String generateFileName = String.format("%s%s", fileName, generateFileSuffix);
            String generateFileNameFinal = FreeMarkerUtil.generateTemplateData(templateDataMap, generateFileName);

            // 待生成文件内容
            String fileContentFinal = FreeMarkerUtil.generateTemplateData(templateDataMap, templateContent);

            // 包路径
            String templateRePackagePath = templateRePackage.replace(".", ProjectConstant.SEPARATOR_SPRIT) + ProjectConstant.SEPARATOR_SPRIT;

            // 最终生成文件路径
            String fileFinalPath = AppConstant.FILE_PATH_CODE_GENERATOR_SRC_CODE + ProjectConstant.SEPARATOR_SPRIT + templateRePackagePath + generateFileNameFinal;
            // 创建文件并写入生成模板数据
            MyFileUtil.writeFileContent(fileContentFinal, fileFinalPath);
        }
    }

}
