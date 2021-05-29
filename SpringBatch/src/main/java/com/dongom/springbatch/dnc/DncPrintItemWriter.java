package com.dongom.springbatch.dnc;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DncPrintItemWriter implements ItemWriter<DncMapper>  {

	@Override
	public void write(List<? extends DncMapper> items) throws Exception {
		for (DncMapper item : items) {
			log.info(item.getCusNo());
		}
	}
}
