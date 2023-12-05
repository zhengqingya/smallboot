package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.zhengqing.common.web.util.YmlUtil;
import com.zhengqing.system.model.vo.SysCgProjectPackageTreeVO;
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

    final String rootTplPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/doc/code-generate-template/smallboot";

    @Override
    public List<SysCgProjectPackageTreeVO> projectPackageTree() {
        return this.recursiveTplFile(this.rootTplPath, "", "");
    }

    private List<SysCgProjectPackageTreeVO> recursiveTplFile(String tplRootPath, String tplChildDir, String rootPackageName) {
        List<SysCgProjectPackageTreeVO> childList = Lists.newArrayList();
        File tplFile = new File(tplRootPath + tplChildDir);
        if (!tplFile.exists()) {
            return null;
        }

        File[] tplFileArray = tplFile.listFiles();
        for (File tplFileItem : tplFileArray) {
            String tplFileItemName = tplFileItem.getName();
            SysCgProjectPackageTreeVO cgItem = SysCgProjectPackageTreeVO.builder().build();
            if (tplFileItem.isDirectory()) {
                // 目录
                String childDir = tplChildDir + "/" + tplFileItemName;
                String javaDirName = childDir.replaceAll("/", ".").substring(1);
                cgItem.setName(javaDirName);
                cgItem.setChildList(this.recursiveTplFile(tplRootPath, childDir, rootPackageName));
            } else if (tplFileItem.isFile()) {
                // 文件
                cgItem.setName(tplFileItemName);
                cgItem.setTplContent(FileUtil.readUtf8String(tplFileItem.getAbsoluteFile()));
            }

            childList.add(cgItem);
        }

        return childList;
    }

    @Override
    public void generateTplData() {
        // 读取基本生成配置信息（mysql连接配置等...）
        final String GENERATE_CONFIG_PATH = AppConstant.PROJECT_ROOT_DIRECTORY + "/app/src/main/resources/generate-config.yml";
        GenerateConfig generateConfig = YmlUtil.getYml(GENERATE_CONFIG_PATH, GenerateConfig.class);

        final String ip = generateConfig.getIp();
        final String port = generateConfig.getPort();
        final String username = generateConfig.getUsername();
        final String password = generateConfig.getPassword();
        final String dbName = generateConfig.getDbName();
        final String tableName = generateConfig.getTableName();
        final String parentPackageName = generateConfig.getParentPackageName();
        final String moduleName = generateConfig.getModuleName();


        // 查询字段数据   eg: "username"
        List<String> queryColumnList = Lists.newArrayList();

        // 包名
        String rootPackageName = parentPackageName + "." + moduleName;

        // 生成文件信息
        List<GeneratorCodeTemplateFileBO> tplFileInfoList = this.getTplFileInfo(this.rootTplPath, rootPackageName);

        // 查询表字段信息
        DbTableColumnListVO columnInfo = new MyJdbcUtil().getAllColumnsByDbInfo(DataSourceTypeEnum.MySQL, ip, port, username, password, dbName, tableName);

        // TODO 默认所有字段
        queryColumnList = columnInfo.getColumnInfoList().stream().map(DbTableColumnListVO.ColumnInfo::getColumnName).collect(Collectors.toList());

        // 模板数据处理
        Map<String, Object> templateDataMap = this.getTplData(columnInfo, tplFileInfoList.stream().map(GeneratorCodeTemplateFileBO::getTplRePackage).collect(Collectors.toList()), queryColumnList);
        templateDataMap.put("author", "zhengqingya");
        templateDataMap.put("moduleName", moduleName);

        // 先删除旧数据
        MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH);

        // 模板数据生成
        this.generateTplData(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH, tplFileInfoList, templateDataMap);
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
