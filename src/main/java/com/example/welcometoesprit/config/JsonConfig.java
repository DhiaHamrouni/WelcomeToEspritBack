package com.example.welcometoesprit.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

@Configuration
public class JsonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(GrantedAuthority.class, new GrantedAuthorityDeserializer());
            builder.modulesToInstall(module);
        };
    }

    public static class GrantedAuthorityDeserializer extends StdDeserializer<GrantedAuthority> {

        protected GrantedAuthorityDeserializer() {
            super(GrantedAuthority.class);
        }

        @Override
        public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectCodec codec = p.getCodec();
            JsonNode node = codec.readTree(p);
            String authority = node.get("authority").asText();
            return new SimpleGrantedAuthority(authority);
        }
    }
}
