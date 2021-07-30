package com.dbe.domain.settings;

import javax.persistence.*;

/**
 * Created by Mikiyas on 15/03/2017.
 */
@Table(name="setting_lookup",schema = "recruitmentDB")
@Entity
public class Lookup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "lookupDetail_sequence")
    @SequenceGenerator(name = "lookupDetail_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "LOOKUP_ID_SEQ")
    private Long id;
    private String description;
    private Boolean status;
    private String value;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lookupCatId",referencedColumnName = "id")
    private LookupCategory lookupCategory;

    public Lookup() {
    }

    public Lookup(String description, Boolean status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LookupCategory getLookupCategory() {
        return lookupCategory;
    }

    public void setLookupCategory(LookupCategory lookupCategory) {
        this.lookupCategory = lookupCategory;
    }
}
