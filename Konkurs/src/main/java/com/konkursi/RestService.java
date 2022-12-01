package com.konkursi;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class RestService extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// svi kandidati
		from("direct:getAll").to("sql:select * from konkurs.kandidat").marshal().json().process(e -> {
			String payload = e.getIn().getBody(String.class);
			e.getIn().setBody(payload);
		});

		// primljeni kandidati
		from("direct:find-accepted").to("sql:select * from konkurs.kandidat where primljen=true").marshal().json()
				.process(e -> {
					String payload = e.getIn().getBody(String.class);
					e.getIn().setBody(payload);
				});

		// odbijeni kandidati
		from("direct:find-rejected").to("sql:select * from konkurs.kandidat where primljen=false").marshal().json()
				.process(e -> {
					String payload = e.getIn().getBody(String.class);
					e.getIn().setBody(payload);
				});

		CsvDataFormat csvFormatWithHeader = new CsvDataFormat();
		csvFormatWithHeader.setHeader(Arrays.asList(new String[] { "ID", "IME", "PREZIME", "JMBG", "GOD_RODJENJA",
				"EMAIL", "TELEFON", "NAPOMENA", "PRIMLJEN", "AZURIRAN" }.clone()));

		// sacuvaj u datoteku primljene kandidate
		from("direct:save-accepted").to("sql:select * from konkurs.kandidat where primljen=true?outputType=StreamList")
				.marshal(csvFormatWithHeader).to("file:output?fileName=kandidati.csv");

		// sacuvaj u datoteku odbijene kandidate
		from("direct:save-rejected").to("sql:select * from konkurs.kandidat where primljen=false?outputType=StreamList")
				.marshal(csvFormatWithHeader).to("file:output?fileName=kandidati.csv");

		// sacuvaj u datoteku sve kandidate
		from("direct:save-all").to("sql:select * from konkurs.kandidat?outputType=StreamList")
				.marshal(csvFormatWithHeader).to("file:output?fileName=kandidati.csv");

	}

}
