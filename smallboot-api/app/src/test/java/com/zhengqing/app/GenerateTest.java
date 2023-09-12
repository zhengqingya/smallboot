package com.zhengqing.app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.ProjectConstant;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.common.db.enums.DataSourceTypeEnum;
import com.zhengqing.common.db.model.bo.GeneratorCodeTemplateFileBO;
import com.zhengqing.common.db.model.vo.DbTableColumnListVO;
import com.zhengqing.common.db.util.GenerateCodeUtil;
import com.zhengqing.common.db.util.MyJdbcUtil;
import com.zhengqing.common.web.util.YmlUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p> 代码生成器测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/29 11:23
 */
@Slf4j
public class GenerateTest {

    @Test
    public void testGenerate() throws Exception {
        // 读取基本生成配置信息（mysql连接配置等...）
        final String GENERATE_CONFIG_PATH = AppConstant.PROJECT_ROOT_DIRECTORY + "/src/main/resources/generate-config.yml";
        GenerateConfig generateConfig;
        try {
            generateConfig = YmlUtil.getYml(GENERATE_CONFIG_PATH, GenerateConfig.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("...使用默认配置...");
            generateConfig = GenerateConfig.builder()
                    .ip("127.0.0.1")
                    .port("3306")
                    .username("root")
                    .password("root")
                    .dbName("demo")
                    .tableName("t_demo")
                    .parentPackageName("com.zhengqingya.demo")
                    .moduleName("test")
                    .build();
        }
        final String ip = generateConfig.getIp();
        final String port = generateConfig.getPort();
        final String username = generateConfig.getUsername();
        final String password = generateConfig.getPassword();
        final String dbName = generateConfig.getDbName();
        final String tableName = generateConfig.getTableName();
        final String parentPackageName = generateConfig.getParentPackageName();
        final String moduleName = generateConfig.getModuleName();
        final String tplRootPath = AppConstant.PROJECT_ROOT_DIRECTORY + "/template/smallboot";

        // 查询字段数据   eg: "username"
        List<String> queryColumnList = Lists.newArrayList();

        // 包名信息
        Map<String, String> packageNameInfoMap = Maps.newHashMap();

        // 生成文件信息
        List<GeneratorCodeTemplateFileBO> tplFileInfoList = Lists.newArrayList();
        this.handleTplContentData(tplFileInfoList, tplRootPath, "", parentPackageName + ProjectConstant.SEPARATOR_SPOT + moduleName, packageNameInfoMap);

        // 查询表字段信息
        DbTableColumnListVO columnInfo = new MyJdbcUtil().getAllColumnsByDbInfo(DataSourceTypeEnum.MySQL, ip, port, username, password, dbName, tableName);

        // TODO 默认所有字段
        queryColumnList = columnInfo.getColumnInfoList().stream().map(DbTableColumnListVO.ColumnInfo::getColumnName).collect(Collectors.toList());

        // 模板数据处理
        Map<String, Object> templateDataMap = GenerateCodeUtil.handleTplData(moduleName, columnInfo, packageNameInfoMap, queryColumnList);
        templateDataMap.put("author", "zhengqingya");

        // 先删除旧数据
        MyFileUtil.deleteFileOrFolder(AppConstant.FILE_PATH_CODE_GENERATOR_DATA_PATH);

        // 模板数据生成
        GenerateCodeUtil.generateTplFileData(tplFileInfoList, templateDataMap);
        log.info("=== FINISH ===");
    }

    /**
     * 递归获取模板文件数据
     *
     * @param tplFileInfoList    总模板文件数据
     * @param tplRootPath        模板根目录
     * @param tplChildDir        模板子文件夹
     * @param packageName        包名
     * @param packageNameInfoMap 包名信息
     * @return 模板文件数据
     * @author zhengqingya
     * @date 2021/8/21 6:41 下午
     */
    private void handleTplContentData(List<GeneratorCodeTemplateFileBO> tplFileInfoList, String tplRootPath, String tplChildDir, String packageName, Map<String, String> packageNameInfoMap) {
        File tplRootFile = new File(tplRootPath + tplChildDir);
        if (tplRootFile.exists() && tplRootFile.isDirectory()) {
            File[] tplFileArray = tplRootFile.listFiles();
            for (File tplFileItem : tplFileArray) {
                String tplFileItemName = tplFileItem.getName();
                if (tplFileItem.isDirectory()) {
                    this.handleTplContentData(tplFileInfoList, tplRootPath, tplChildDir + ProjectConstant.SEPARATOR_SPRIT + tplFileItemName, packageName, packageNameInfoMap);
                } else if (tplFileItem.isFile()) {
                    String packageNameItem = packageName + tplRootFile.getPath().replaceAll(ProjectConstant.SEPARATOR_BACKSLASH, ProjectConstant.SEPARATOR_SPRIT).replaceFirst(tplRootPath.replaceAll(ProjectConstant.SEPARATOR_BACKSLASH, ProjectConstant.SEPARATOR_SPRIT), "").replaceAll(ProjectConstant.SEPARATOR_SPRIT, ProjectConstant.SEPARATOR_SPOT);
                    packageNameInfoMap.put(UUID.randomUUID().toString(), packageNameItem);
                    String[] tplFileNameArray = tplFileItemName.split("\\" + ProjectConstant.SEPARATOR_SPOT);
                    tplFileInfoList.add(GeneratorCodeTemplateFileBO.builder()
                            .fileName(tplFileNameArray[0])
                            .generateFileSuffix(ProjectConstant.SEPARATOR_SPOT + tplFileNameArray[1])
                            .templateContent(MyFileUtil.readFileContent(tplFileItem))
                            .templateRePackage(packageNameItem)
                            .build());
                }
            }
        }
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
