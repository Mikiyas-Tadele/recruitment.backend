package com.dbe.services.settings;

import com.dbe.services.settings.model.LookupModel;
import com.dbe.services.settings.model.LookupModelDetail;

import java.util.List;

/**
 * Created by Mikiyas on 15/03/2017.
 */
public interface SettingService {
    LookupModel saveLookup(LookupModel model);
    LookupModelDetail saveLookupDetail(LookupModelDetail model);
    List<LookupModel> getLookups();
    LookupModelDetail getLookup(Long id);
    LookupModel getLookup();
    void removeLookup(LookupModelDetail detail);
    void removeLookupCategory(Long id);
    List<LookupModelDetail> getLookupDetail(String code);
    String getDescription(Long id);

}
