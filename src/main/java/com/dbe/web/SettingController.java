package com.dbe.web;

import com.dbe.services.settings.*;
import com.dbe.services.settings.model.LookupModel;
import com.dbe.services.settings.model.LookupModelDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mikiyas on 16/03/2017.
 */
@CrossOrigin(origins = "*", maxAge = 1800)
@RestController
@RequestMapping(ResourceConstants.SETTING_API)
public class SettingController {

    private SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @RequestMapping("/lookups")
    public List<LookupModel> getLookups() {
        return settingService.getLookups();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/saveLookupCategory")
    public LookupModel saveLookupCategory(@RequestBody LookupModel model) {

        return settingService.saveLookup(model);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/saveLookupCategoryDetail")
    public LookupModelDetail saveLookupCategoryDetail(@RequestBody LookupModelDetail model) {
        return settingService.saveLookupDetail(model);
    }

    @RequestMapping(value = "/getlookupDetails/{code}")
    public List<LookupModelDetail> getLookupDetails(@PathVariable String code) {
        return settingService.getLookupDetail(code);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/removeLookupCategory/{id}")
    public void removeLookupCategory(@PathVariable Long id){
        settingService.removeLookupCategory(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/removeLookUp")
    public void removeLookUp(@RequestBody LookupModelDetail model){
        settingService.removeLookup(model);
    }


}
