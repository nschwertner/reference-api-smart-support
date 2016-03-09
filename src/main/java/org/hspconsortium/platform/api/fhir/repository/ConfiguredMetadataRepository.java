/*
 * #%L
 * Healthcare Services Consortium Platform FHIR Server
 * %%
 * Copyright (C) 2014 - 2015 Healthcare Services Platform Consortium
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.hspconsortium.platform.api.fhir.repository;

import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.resource.Conformance;
import ca.uhn.fhir.model.primitive.UriDt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfiguredMetadataRepository implements MetadataRepository {

    @Value("${hspc.platform.authorization.tokenUrl}")
    private String tokenEndpointUri;

    @Value("${hspc.platform.authorization.authorizeUrl}")
    private String authorizationEndpointUri;

    @Value("${hspc.platform.authorization.smart.registrationEndpointUrl}")
    private String registrationEndpointUri;

    @Value("${hspc.platform.authorization.smart.urisEndpointExtensionUrl}")
    private String urisEndpointExtensionUrl;

    public Conformance addConformance(Conformance conformance){

        List<Conformance.Rest> restList = conformance.getRest();
        Conformance.Rest rest = restList.get(0);
        Conformance.RestSecurity restSecurity = rest.getSecurity();

        ExtensionDt conformanceExtension = new ExtensionDt(false, this.urisEndpointExtensionUrl);
        conformanceExtension.addUndeclaredExtension(new ExtensionDt(false, "authorize", new UriDt( this.authorizationEndpointUri)));
        conformanceExtension.addUndeclaredExtension(new ExtensionDt(false, "token", new UriDt( this.tokenEndpointUri)));
        conformanceExtension.addUndeclaredExtension(new ExtensionDt(false, "register", new UriDt( this.registrationEndpointUri)));

        restSecurity.addUndeclaredExtension(conformanceExtension);

        return conformance;
    }

}
