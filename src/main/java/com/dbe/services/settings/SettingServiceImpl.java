package com.dbe.services.settings;

import com.dbe.domain.settings.Lookup;
import com.dbe.domain.settings.LookupCategory;
import com.dbe.repositories.settings.LookupCategoryRepository;
import com.dbe.services.settings.model.LookupModel;
import com.dbe.services.settings.model.LookupModelDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mikiyas on 15/03/2017.
 */
@Service
public class SettingServiceImpl implements SettingService {

    private LookupCategoryRepository lookupCategoryRepository;

    @Autowired
    public SettingServiceImpl(LookupCategoryRepository lookupCategoryRepository) {
        this.lookupCategoryRepository = lookupCategoryRepository;
    }

    @Override
    public LookupModel saveLookup(LookupModel model) {

        LookupCategory lookupCategory=new LookupCategory();
        lookupCategory.setDescription(model.getDescription());
        lookupCategory.setCode(model.getCode());
        lookupCategory.setStatus(model.getStatus());
        if(model.getId()!=null){
            lookupCategory.setId(model.getId());
        }
        Set<Lookup> lookups=new HashSet<>();
        if(model.getDetails()!=null) {
            for (LookupModelDetail lookupModelDetail : model.getDetails()) {
                Lookup lookup = new Lookup();
                lookup.setDescription(lookupModelDetail.getDescription());
                lookup.setLookupCategory(lookupCategory);
                lookup.setStatus(lookupModelDetail.getStatus());
                if(lookupModelDetail.getValue()!=null){
                    lookup.setValue(lookupModelDetail.getValue());
                }
                if (lookupModelDetail.getId() != null) {
                    lookup.setId(lookupModelDetail.getId());
                }
                lookups.add(lookup);
            }
        }

        lookupCategory.setLookups(lookups);

        lookupCategoryRepository.save(lookupCategory);
        model.setId(lookupCategory.getId());

        return model;

    }


    @Override
    public List<LookupModel> getLookups() {

        List<LookupModel> lookupModels=new ArrayList<>();
        for ( LookupCategory lookupCategory:lookupCategoryRepository.findAll()) {
            LookupModel model=new LookupModel();
            model.setCode(lookupCategory.getCode());
            model.setDescription(lookupCategory.getDescription());
            model.setStatus(lookupCategory.getStatus());
            model.setId(lookupCategory.getId());
            List<LookupModelDetail> modelDetails=new ArrayList<>();
            getLookupDetails(lookupCategory,modelDetails);
            model.setDetails(modelDetails);
            lookupModels.add(model);

        }

        return  lookupModels;

    }

    private LookupModelDetail getLookupModelDetail() {
        return new LookupModelDetail();
    }

    @Override
    public LookupModelDetail saveLookupDetail(LookupModelDetail model) {
        Lookup lookup;
        if(model ==null)
            return null;
        LookupCategory lookupCategory=lookupCategoryRepository.findOne(model.getLookupCategoryId());
        if(model.getId()==null || model.getId()==0){
            lookup=new Lookup();
        }
        else
        {
            lookup=getExistingLookup(lookupCategory,model.getId());
            lookup.setId(model.getId());
        }
        lookup.setLookupCategory(lookupCategory);
             lookup.setStatus(model.getStatus());
             lookup.setDescription(model.getDescription());
             if(model.getValue()!=null){
                 lookup.setValue(model.getValue());
             }
         Set<Lookup> lookups=new HashSet<>();
         lookups.add(lookup);
         lookupCategory.setLookups(lookups);

        lookupCategoryRepository.save(lookupCategory);
        model.setId(lookup.getId());
        return model;
    }

    @Override
    public LookupModelDetail getLookup(Long id) {
        Lookup lookup=lookupCategoryRepository.findByDetailId(id);
                LookupModelDetail modelDetail = new LookupModelDetail();
                modelDetail.setId(lookup.getId());
                modelDetail.setDescription(lookup.getDescription());
                modelDetail.setLookupCategoryId(lookup.getLookupCategory().getId());
                modelDetail.setStatus(lookup.getStatus());
                modelDetail.setValue(lookup.getValue());

                return modelDetail;
    }


    @Override
    public LookupModel getLookup() {
        return null;
    }

    @Override
    public void removeLookupCategory(Long id) {
        if(id!=null)
        lookupCategoryRepository.delete(id);
    }

    @Override
    public void removeLookup(LookupModelDetail detail) {
        if(detail ==null){
            return;
        }
        LookupCategory lookupCategory=lookupCategoryRepository.findOne(detail.getLookupCategoryId());
        Lookup lookupToDelete=getExistingLookup(lookupCategory,detail.getId());
        if(lookupToDelete==null){
            return;
        }

        lookupCategory.getLookups().remove(lookupToDelete);
        lookupCategoryRepository.save(lookupCategory);
    }

    @Override
    public List<LookupModelDetail> getLookupDetail(String code) {
        LookupCategory lookupCategory=lookupCategoryRepository.findbyCode(code);
        List<LookupModelDetail> details=new ArrayList<>();
        if(lookupCategory!=null){
            getLookupDetails(lookupCategory, details);
        }
        return details;
    }

    private void getLookupDetails(LookupCategory lookupCategory, List<LookupModelDetail> details) {
        for (Lookup lookup:lookupCategory.getLookups()) {
            LookupModelDetail detail = getLookupModelDetail();
            detail.setId(lookup.getId());
            detail.setDescription(lookup.getDescription());
            detail.setStatus(lookup.getStatus());
            detail.setLookupCategoryId(lookupCategory.getId());
            detail.setValue(lookup.getValue());
            details.add(detail);
        }
    }

    private Lookup getExistingLookup(LookupCategory category,Long lookupId){
        for (Lookup lookup:category.getLookups()) {
            if(lookup.getId().equals(lookupId)){
                return lookup;
            }

        }
        return null;
    }

    @Override
    public String getDescription(Long id) {
        Lookup lookup=lookupCategoryRepository.findByDetailId(id);
        if(lookup!=null){
            return lookup.getDescription();
        }
        return "";
    }

}
