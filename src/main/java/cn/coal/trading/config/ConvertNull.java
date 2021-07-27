package cn.coal.trading.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.io.IOException;

/**
 * 将请求参数中的空字符串替换为null
 */
@ControllerAdvice
public class ConvertNull
{
    @InitBinder
    public void dataBind(WebDataBinder webDataBinder)
    {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.deserializerByType(String.class, new StdScalarDeserializer <String>(String.class)
        {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException
            {
                String value = jsonParser.getValueAsString();
                if (value == null || "".equals(value.trim()))
                {
                    return null;
                }
                return value;
            }
        });
    }
}

