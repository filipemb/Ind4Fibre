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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ind4fibre.ftp.FTPService;
import com.ind4fibre.json.modelo.Alarme;
import com.ind4fibre.json.modelo.Armazenamento;
import com.ind4fibre.json.modelo.ArquivoJsonPrinter;
import com.ind4fibre.json.modelo.ArquivoJsonRobot;
import com.ind4fibre.json.modelo.Imp3D;
import com.ind4fibre.json.modelo.LocalDaSRAM;
import com.ind4fibre.json.modelo.Robo;
import com.ind4fibre.json.modelo.Sala;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping
public class ImportarController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private FTPService ftpService;


	@ApiOperation(value = "Recebe o arquivo JSON para importação na base de dados.", nickname="importarArquivoJson")
	@RequestMapping(value = "/importarArquivoJson", produces = { "application/json" }, consumes = { "multipart/form-data" }, method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "localDaSRAM", 
				allowMultiple = false, 
				dataType = "enum", 
				allowableValues = "FLORIANOPOLIS, BAHIA", 
				paramType = "form", 
				value = "Local de onde advém os dados",
				defaultValue = "FLORIANOPOLIS")
	})
	public ResponseEntity<Void> enviarPorArquivos(
			@ApiParam(value = "O arquivo deve conter os dados de Armazenamento, Imp3D e Sala \r\n\n"
					+ "Topicos: temperatura, umidade e iluminacao \r\n\n"
					+ "no formato JSON")
			@RequestPart(value="filePrinter", required=false) MultipartFile filePrinter,
			@ApiParam(value = "O arquivo deve conter os dados de Robo e Alarme \r\n\n"
					+ "Topicos: joint1, vel1, joint2, vel2, joint3, vel3, joint4, vel4, joint5, vel5, joint6, vel6, joint7, vel7, posX, posY, posZ e Robo"
					+ "no formato JSON") 
			@RequestPart(value="fileRobot", required=false) MultipartFile fileRobot,
			@RequestParam(value="localDaSRAM", required=true) LocalDaSRAM localDaSRAM,
			HttpServletRequest request) throws IOException {
		
		LOGGER.info("Iniciando importação de dados");

		LOGGER.info("Lendo arquivo da Impressora");
		Reader readerPrinter = new InputStreamReader(filePrinter.getInputStream());
		ArquivoJsonPrinter arquivoJsonPrinter = mapper.readValue(readerPrinter,new TypeReference<ArquivoJsonPrinter>() {});
		
		LOGGER.info("Lendo arquivo do Robo");
		Reader readerRobot = new InputStreamReader(fileRobot.getInputStream());
		ArquivoJsonRobot arquivoJsonRobot = mapper.readValue(readerRobot,new TypeReference<ArquivoJsonRobot>() {});
		
		List<Robo> robos = arquivoJsonRobot.getRobo();
		LOGGER.info("Leituras de "+robos.size()+" robos");
		
		List<Alarme> alarmes = arquivoJsonRobot.getAlarmes();
		LOGGER.info("Leituras de "+alarmes.size()+" alarmes");
		
		List<Armazenamento> armazenamentos = arquivoJsonPrinter.getArmazenamento();
		LOGGER.info("Leituras de "+armazenamentos.size()+" armazenamentos");
		
		List<Imp3D> impressoras3D = arquivoJsonPrinter.getImp3D();
		LOGGER.info("Leituras de "+impressoras3D.size()+" impressoras3D");
		
		List<Sala>  salas = arquivoJsonPrinter.getSala();
		LOGGER.info("Leituras de "+salas.size()+" salas");
		
		//TODO inserir os dados nas respectivas tabelas
		
		return ResponseEntity.ok().build();
	}
	
	
	@ApiOperation(value = "Lista os arquivos no FTP", nickname="listarFTP")
	@RequestMapping(value = "/listarArquivos", method = RequestMethod.GET)
	public ResponseEntity<List<String>> listarVideos(HttpServletRequest request) throws Exception {
		List<String> files = ftpService.leArquivos();
		return ResponseEntity.ok(files);
	}



}
