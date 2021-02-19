package com.rzk.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @PackageName : com.rzk
 * @FileName : ComponetImport
 * @Description : 导入FastDFS-Client组件
 * @Author : rzk
 * @CreateTime : 19/2/2021 上午12:28
 * @Version : 1.0.0
 */

@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ComponetImport {
    // 导入依赖组件
}