package org.zts.ai.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zts.ai.annotation.ToolsMarkBeanRegistrar;
import org.zts.ai.tools.WeatherTools;

@Configuration
@Import(ToolsMarkBeanRegistrar.class)
public class ToolsConfig {



//    @Bean
//    public ToolCallbackProvider toolCallbackProvider(WeatherTools tools) {
//        return MethodToolCallbackProvider.builder().toolObjects(tools).build();
//    }
}
