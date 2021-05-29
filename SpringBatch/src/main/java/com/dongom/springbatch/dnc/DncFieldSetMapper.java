package com.dongom.springbatch.dnc;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DncFieldSetMapper implements FieldSetMapper<DncMapper> {

	@Override
	public DncMapper mapFieldSet(FieldSet fieldSet) throws BindException {
		DncMapper dncMapper = new DncMapper();
		dncMapper.setCusNo(fieldSet.readString(0));
		return dncMapper;
	}

}
