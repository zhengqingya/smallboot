package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.config.CommonProperty;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.common.base.util.MyStringUtil;
import com.zhengqing.common.db.enums.DataSourceTypeEnum;
import com.zhengqing.common.db.enums.DbColumnJavaTypeEnum;
import com.zhengqing.common.db.model.bo.GeneratorCodeColumnInfoBO;
import com.zhengqing.common.db.model.bo.GeneratorCodeTemplateFileBO;
import com.zhengqing.common.db.model.vo.DbTableColumnListVO;
import com.zhengqing.common.db.util.FreeMarkerUtil;
import com.zhengqing.common.db.util.MyJdbcUtil;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.model.bo.SysCgConfigBO;
import com.zhengqing.system.service.ICodeGenerateService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 代码生成器 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeGenerateServiceImpl implements ICodeGenerateService {

    private final SystemProperty systemProperty;

    /**
     * 项目根路径
     * replace 目的：便于在测试类中使用
     */
    final String ROOT_PATH = AppConstant.PROJECT_ROOT_DIRECTORY.replace("/smallboot-api/app", "/smallboot-api");

    /**
     * 模板配置信息
     */
    final String ROOT_TPL_PATH = this.ROOT_PATH + "/doc/code-generate-template/smallboot";
    final String CONFIG_JSON_PATH = this.ROOT_PATH + "/doc/code-generate-template/config.json";
    /**
     * 代码生成临时存储路径
     */
    String CODE_GENERATOR_DATA_PATH = this.ROOT_PATH + "/tmp/code-generate";

    @Override
    public SysCgConfigBO getConfig() {
        SysCgConfigBO config = JSONUtil.toBean(FileUtil.readUtf8String(this.CONFIG_JSON_PATH), SysCgConfigBO.class);
        config.setPgList(this.recursiveTplFile(this.ROOT_TPL_PATH, "", ""));
        // 查询表字段信息
        DbTableColumnListVO columnInfo = this.getDbColumn(config.getTableName());
        config.setDbColumnList(columnInfo.getColumnInfoList().stream().map(DbTableColumnListVO.ColumnInfo::getColumnName).collect(Collectors.toList()));
        return config;
    }


    private List<SysCgConfigBO.ProjectPackage> recursiveTplFile(String tplRootPath, String tplChildDir, String rootPackageName) {
        List<SysCgConfigBO.ProjectPackage> childList = Lists.newArrayList();
        File tplFile = new File(tplRootPath + tplChildDir);
        if (!tplFile.exists()) {
            return null;
        }

        File[] tplFileArray = tplFile.listFiles();
        for (File tplFileItem : tplFileArray) {
            String tplFileItemName = tplFileItem.getName();
            SysCgConfigBO.ProjectPackage cgItem = SysCgConfigBO.ProjectPackage.builder().build();
            if (tplFileItem.isDirectory()) {
                // 目录
                String childDir = tplChildDir + "/" + tplFileItemName;
                String javaDirName = childDir.replaceAll("/", ".").substring(1);
                cgItem.setName(javaDirName);
                cgItem.setChildList(this.recursiveTplFile(tplRootPath, childDir, rootPackageName));
            } else if (tplFileItem.isFile()) {
                // 文件
                cgItem.setName(tplFileItemName);
                cgItem.setTplFilePath(tplFileItem.getAbsolutePath());
                cgItem.setTplContent(FileUtil.readUtf8String(tplFileItem.getAbsoluteFile()));
            }

            childList.add(cgItem);
        }

        return childList;
    }

    @Override
    public void saveConfig(SysCgConfigBO config) {
        FileUtil.writeUtf8String(JSONUtil.toJsonStr(config), this.CONFIG_JSON_PATH);
        this.recursiveTplData(config.getPgList());
    }

    private void recursiveTplData(List<SysCgConfigBO.ProjectPackage> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(item -> {
            String tplContent = item.getTplContent();
            if (StrUtil.isNotBlank(tplContent)) {
                // 保存
                FileUtil.writeUtf8String(tplContent, item.getTplFilePath());
            }
            List<SysCgConfigBO.ProjectPackage> childList = item.getChildList();
            if (CollUtil.isNotEmpty(childList)) {
                // 继续下一级
                this.recursiveTplData(childList);
            }
        });
    }


    @Override
    public void generateTplData(SysCgConfigBO config) {
        // 1、先保存下配置
        this.saveConfig(config);

        // 2、再生成代码
        String parentPackageName = config.getParentPackageName();
        String moduleName = config.getModuleName();
        String tableName = config.getTableName();
        List<String> queryColumnList = config.getQueryColumnList();

        // 包名
        String rootPackageName = parentPackageName + "." + moduleName;

        // 生成文件信息
        List<GeneratorCodeTemplateFileBO> tplFileInfoList = this.getTplFileInfo(this.ROOT_TPL_PATH, rootPackageName);

        // 查询表字段信息
        DbTableColumnListVO columnInfo = this.getDbColumn(tableName);

        // 模板数据处理
        Map<String, Object> templateDataMap = this.getTplData(columnInfo, tplFileInfoList.stream().map(GeneratorCodeTemplateFileBO::getTplRePackage).collect(Collectors.toList()), queryColumnList);
        templateDataMap.put("author", "zhengqingya");
        templateDataMap.put("moduleName", moduleName);

        // 先删除旧数据
        MyFileUtil.deleteFileOrFolder(this.CODE_GENERATOR_DATA_PATH);

        // 模板数据生成
        this.generateTplData(this.CODE_GENERATOR_DATA_PATH, tplFileInfoList, templateDataMap);
    }

    /**
     * 查询表字段信息
     */
    private DbTableColumnListVO getDbColumn(String tableName) {
        // 拿到mysql连接配置
        CommonProperty.MysqlConn mysqlConfig = this.systemProperty.getMysql().getMaster();
        return new MyJdbcUtil().getAllColumnsByDbInfo(DataSourceTypeEnum.MySQL, mysqlConfig.getIp(), mysqlConfig.getPort(), mysqlConfig.getUsername(), mysqlConfig.getPassword(), mysqlConfig.getDbName(), tableName);
    }

    /**
     * 拿到模板数据
     *
     * @param rootTplPath     模板根路径
     * @param rootPackageName 根包
     * @return 模板数据
     * @author zhengqingya
     * @date 2020/11/15 21:42
     */
    private List<GeneratorCodeTemplateFileBO> getTplFileInfo(String rootTplPath, String rootPackageName) {
        List<GeneratorCodeTemplateFileBO> tplFileInfoList = Lists.newArrayList();
        // 递归拿到模板文件信息
        List<File> tplFileList = FileUtil.loopFiles(rootTplPath);
        tplFileList.parallelStream().forEach(e -> {
            String tplFileNameItem = e.getName();
            String absolutePathItem = e.getAbsolutePath();
            String packageNameItem = rootPackageName + absolutePathItem.replaceAll("\\\\", "/").replaceFirst(rootTplPath, "").replaceAll("/", ".");
            packageNameItem = packageNameItem.replace("." + tplFileNameItem, "");
            String[] tplFileNameArray = tplFileNameItem.split("\\" + ".");
            tplFileInfoList.add(GeneratorCodeTemplateFileBO.builder()
                    .fileName(tplFileNameItem)
                    .filePrefix(tplFileNameArray[0])
                    .fileSuffix("." + tplFileNameArray[1])
                    .tplContent(FileUtil.readUtf8String(absolutePathItem))
                    .tplRePackage(packageNameItem)
                    .build());
        });
        return tplFileInfoList;
    }

    /**
     * 处理模板数据
     *
     * @param columnInfo      表字段信息
     * @param packageNameList 包名
     * @param queryColumnList 可检索字段信息
     * @return 模板数据
     * @author zhengqingya
     * @date 2020/11/15 21:42
     */
    private Map<String, Object> getTplData(DbTableColumnListVO columnInfo, List<String> packageNameList, List<String> queryColumnList) {
        Assert.isTrue(CollUtil.isNotEmpty(columnInfo.getColumnInfoList()), "数据库表字段信息丢失!");
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

        // 获取表字段信息
        // 表注释
        String tableComment = columnInfo.getTableComment();
        templateDataMap.put("tableComment", tableComment);
        List<DbTableColumnListVO.ColumnInfo> columnInfoList = columnInfo.getColumnInfoList();
        if (CollUtil.isNotEmpty(columnInfoList)) {
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
        packageNameList.parallelStream().forEach(pnItem -> {
            String[] packageNameArray = pnItem.split("\\.");
            String packageName = packageNameArray[packageNameArray.length - 1];
            packageMap.put(packageName, pnItem);
        });
        templateDataMap.put("package", packageMap);

        templateDataMap.put("date", MyDateUtil.nowStr(MyDateUtil.MINUTE_JAVA_AUTHOR_FORMAT));
        return templateDataMap;
    }

    /**
     * 模板数据生成
     *
     * @param generatePath         生成路径
     * @param templateFileInfoList 模板文件信息
     * @param templateDataMap      模板数据
     * @return void
     * @author zhengqingya
     * @date 2020/11/15 21:05
     */
    private void generateTplData(String generatePath, List<GeneratorCodeTemplateFileBO> templateFileInfoList, Map<String, Object> templateDataMap) {
        // 循环生成数据文件
        templateFileInfoList.parallelStream().forEach(tplItem -> {
            // 待生成文件名 eg: `test.java`
            String generateFileNameFinal = FreeMarkerUtil.generateTemplateData(templateDataMap, tplItem.getFileName());

            // 待生成文件内容
            String fileContentFinal = FreeMarkerUtil.generateTemplateData(templateDataMap, tplItem.getTplContent());

            // 包路径
            String templateRePackagePath = tplItem.getTplRePackage().replace(".", "/") + "/";

            // 最终生成文件路径
            String fileFinalPath = generatePath + "/" + templateRePackagePath + generateFileNameFinal;

            // 创建文件并写入生成模板数据
            FileUtil.writeUtf8String(fileContentFinal, fileFinalPath);
        });
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("代码生成配置参数")
    public static class GenerateConfig {
        @ApiModelProperty("mysql-ip地址")
        private String ip;

        @ApiModelProperty("端口")
        private String port;

        @ApiModelProperty("用户名")
        private String username;

        @ApiModelProperty("密码")
        private String password;

        @ApiModelProperty("库名")
        private String dbName;

        @ApiModelProperty("表名")
        private String tableName;

        @ApiModelProperty("父包名")
        private String parentPackageName;

        @ApiModelProperty("模块名")
        private String moduleName;
    }


}
