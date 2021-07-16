package com.dbe.repositories.settings;

import com.dbe.domain.settings.Lookup;
import com.dbe.domain.settings.LookupCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Mikiyas on 15/03/2017.
 */
public interface LookupCategoryRepository extends CrudRepository<LookupCategory,Long> {
    @Query("select lc from LookupCategory lc  where lc.code=:code")
    LookupCategory findbyCode(@Param("code") String code);

    @Query("select l from LookupCategory lc  join lc.lookups l where l.id=:id ")
    Lookup findByDetailId(@Param("id") Long id);
}
