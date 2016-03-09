package org.hspconsortium.platform.api.smart;

import org.hspconsortium.platform.api.fhir.repository.ConfiguredMetadataRepository;
import org.hspconsortium.platform.api.fhir.repository.MetadataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SMARTConfig {

    @Bean
    public MetadataRepository metadataRepository() {
        return new ConfiguredMetadataRepository();
    }
}
