package com.dbe.domain.settings;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mikiyas on 15/03/2017.
 */
@Table(name = "lookup_category",schema = "recruitmentDB")
@Entity
public class LookupCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "lookup_sequence")
    @SequenceGenerator(name = "lookup_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "LOOKUP_CATEGORY_ID_SEQ")
    private Long id;
    private String code;
    private String description;
    private Boolean status;
    @OneToMany(mappedBy = "lookupCategory",cascade = CascadeType.ALL)
    private Set<Lookup> lookups;


    public LookupCategory() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Lookup> getLookups() {
        return lookups;
    }

    public void setLookups(Set<Lookup> lookups) {
        this.lookups = lookups;
    }
}
