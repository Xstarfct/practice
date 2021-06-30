package com.fct.d.convert;

import com.fct.d.domain.SetterTestDest;
import com.fct.d.domain.SetterTestOrigin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * SetterTestJMHConvert
 *
 * @author fct
 * @version 2021-06-29 16:09
 */
@Mapper(componentModel = "Spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SetterTestJMHConvert extends BaseMappingConvert<SetterTestOrigin, SetterTestDest> {

  SetterTestJMHConvert INSTANCE = Mappers.getMapper(SetterTestJMHConvert.class);

}
