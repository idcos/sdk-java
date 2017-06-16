/*******************************************************************************
 * 	Copyright 2016 ContainX and OpenStack4j                                          
 * 	                                                                                 
 * 	Licensed under the Apache License, Version 2.0 (the "License"); you may not      
 * 	use this file except in compliance with the License. You may obtain a copy of    
 * 	the License at                                                                   
 * 	                                                                                 
 * 	    http://www.apache.org/licenses/LICENSE-2.0                                   
 * 	                                                                                 
 * 	Unless required by applicable law or agreed to in writing, software              
 * 	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT        
 * 	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the         
 * 	License for the specific language governing permissions and limitations under    
 * 	the License.                                                                     
 *******************************************************************************/
package org.openstack4j.openstack.dns.v2.internal;

import static com.google.common.base.Preconditions.*;
import static org.openstack4j.core.transport.ClientConstants.*;

import java.util.List;
import java.util.Map;

import org.openstack4j.api.dns.v2.ZoneService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.dns.v2.Nameserver;
import org.openstack4j.model.dns.v2.Zone;
import org.openstack4j.openstack.dns.v2.domain.DesignateNameserver;
import org.openstack4j.openstack.dns.v2.domain.DesignateZone;

public class ZoneServiceImpl extends BaseDNSServices implements ZoneService {

    @Override
    public Zone get(String zoneId) {
        checkNotNull(zoneId);
        return get(DesignateZone.class, PATH_ZONES, "/", zoneId).execute();
    }

    @Override
    public ActionResponse delete(String zoneId) {
        checkNotNull(zoneId);
        return deleteWithResponse(PATH_ZONES, "/", zoneId).execute();
    }

    @Override
    public List<? extends Nameserver> listNameservers(String zoneId) {
        checkNotNull(zoneId);
        return get(DesignateNameserver.Nameservers.class, PATH_ZONES, "/",zoneId, PATH_NAMESERVERS).execute().getList();
    }

    @Override
    public Zone update(Zone zone) {
        checkNotNull(zone);
        return patch(DesignateZone.class, PATH_ZONES, "/", zone.getId()).entity(zone).execute();
    }

    @Override
    public Zone create(Zone zone) {
        checkNotNull(zone);
        return post(DesignateZone.class, uri(PATH_ZONES)).entity(zone).execute();
    }

    @Override
    public Zone create(String name, String email) {
        checkNotNull(name);
        checkNotNull(email);
        return create(DesignateZone.builder().name(name).email(email).build());
    }

    @Override
    public List<? extends Zone> list() {
        return get(DesignateZone.Zones.class, uri(PATH_ZONES)).execute().getList();
    }

    @Override
    public List<? extends Zone> list(Map<String, Object> filters) {
        Invocation<DesignateZone.Zones> invocation = get(DesignateZone.Zones.class, uri(PATH_ZONES));
        invocation.params(filters);
        return invocation.execute().getList();
    }

}
