package com.ind4fibre.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ind4fibre.json.modelo.Armazenamento;
import com.ind4fibre.json.modelo.ArquivoJson;
import com.ind4fibre.json.modelo.Imp3D;
import com.ind4fibre.json.modelo.Sala;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping
public class ImportarController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper mapper;


	@ApiOperation(value = "Recebe o arquivo JSON para importação na base de dados.", nickname="importarArquivoJson")
	@RequestMapping(value = "/importarArquivoJson", produces = { "application/json" }, consumes = { "multipart/form-data" }, method = RequestMethod.POST)
	public ResponseEntity<Void> enviarPorArquivos(
			@ApiParam(value = "O arquivo deve conter os dados no formato JSON") @RequestPart(value="json", required=true) MultipartFile json,
			HttpServletRequest request) throws IOException {
		
		LOGGER.info("Iniciando importação de dados");

		Reader reader = new InputStreamReader(json.getInputStream());
		
		ArquivoJson arquivoJson = mapper.readValue(reader,new TypeReference<ArquivoJson>() {});

		//TODO inserir os dados nas respectivas tabelas
		
		List<Armazenamento> armazenamentos = arquivoJson.getArmazenamento();
		LOGGER.info("Leituras de "+armazenamentos.size()+" armazenamentos");
		
		List<Imp3D> impressoras3D = arquivoJson.getImp3D();
		LOGGER.info("Leituras de "+impressoras3D.size()+" impressoras3D");
		
		List<Sala>  salas = arquivoJson.getSala();
		LOGGER.info("Leituras de "+salas.size()+" salas");
		
		return ResponseEntity.ok().build();
	}



}
