package org.zts.ai.annotation;

import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ToolsMarkBeanRegistrar implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 1. 扫描所有带有 @ToolsMark 注解的类
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ToolsMark.class));

        ApplicationHome home = new ApplicationHome();  // 无参构造
        File rootDir = home.getDir();
        String rootPath = rootDir.getAbsolutePath();
        // 指定扫描包路径（可动态配置）
        Set<BeanDefinition> candidates = scanner.findCandidateComponents(rootPath);

        // 2. 注册这些 Bean（确保它们已存在于容器中）
        for (BeanDefinition candidate : candidates) {
            String beanName = generateBeanName(candidate);
            if (!registry.containsBeanDefinition(beanName)) {
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 3. 收集所有已注册的 @ToolsMark Bean
        Map<String, Object> toolsBeans = beanFactory.getBeansWithAnnotation(ToolsMark.class);

        // 4. 动态生成 ToolCallbackProvider 并注册
        if (!toolsBeans.isEmpty()) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder
                    .rootBeanDefinition(MethodToolCallbackProvider.class)
                    .addConstructorArgValue(toolsBeans.values()); // 传入构造参数

            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(
                    "toolCallbackProvider",
                    builder.getBeanDefinition()
            );
        }
    }

    private String generateBeanName(BeanDefinition definition) {
        // 根据类名生成默认 Bean 名称（可自定义）
        return StringUtils.uncapitalize(definition.getBeanClassName());
    }
}
