package com.ind4fibre.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ind4fibre.database.model.AlarmeBraco;
import com.ind4fibre.database.model.BracoRobotico;
import com.ind4fibre.database.model.LeituraBraco;
import com.ind4fibre.database.model.LeituraRecurso;
import com.ind4fibre.database.model.Recurso;
import com.ind4fibre.database.model.Sensor;
import com.ind4fibre.database.repository.AlarmeBracoRepository;
import com.ind4fibre.database.repository.BracoRoboticoRepository;
import com.ind4fibre.database.repository.LeituraBracoRepository;
import com.ind4fibre.database.repository.LeituraRecursoRepository;
import com.ind4fibre.database.repository.RecursoRepository;
import com.ind4fibre.database.repository.SensorRepository;
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
	
	@Autowired
	private LeituraBracoRepository leituraBracoRepository;
	
	@Autowired
	private AlarmeBracoRepository alarmeBracoRepository;
	
	@Autowired
	private BracoRoboticoRepository bracoRoboticoRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private LeituraRecursoRepository leituraRecursoRepository;

	@ApiOperation(value = "Recebe o arquivo JSON para importação na base de dados.", nickname="importarArquivoJson")
	@RequestMapping(value = "/importarArquivoJson", produces = { "video/mp4" }, consumes = { "multipart/form-data" }, method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "localDaSRAM", 
				allowMultiple = false, 
				dataType = "enum", 
				allowableValues = "FLORIANOPOLIS, BAHIA", 
				paramType = "form", 
				value = "Local de onde advém os dados",
				defaultValue = "FLORIANOPOLIS")
	})
	public ResponseEntity<Resource> enviarPorArquivos(
			@ApiParam(value = "O arquivo deve conter os dados de Armazenamento, Imp3D e Sala \r\n\n"
					+ "Topicos: temperatura, umidade e iluminacao \r\n\n"
					+ "no formato JSON")
			@RequestPart(value="filePrinter", required=false) MultipartFile filePrinter,
			@ApiParam(value = "O arquivo deve conter os dados de Robo e Alarme \r\n\n"
					+ "Topicos: joint1, vel1, joint2, vel2, joint3, vel3, joint4, vel4, joint5, vel5, joint6, vel6, joint7, vel7, posX, posY, posZ e Robo"
					+ "no formato JSON") 
			@RequestPart(value="fileRobot", required=false) MultipartFile fileRobot,
			@RequestParam(value="localDaSRAM", required=true) LocalDaSRAM localDaSRAM,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		LOGGER.info("Iniciando importação de dados");
		
		LOGGER.info("Buscando dados basicos (tipos de sensores, bracos por ilha, recursos por ilha...");
		final Sensor sensorPosition = sensorRepository.findByNomeMedicao("position");
		final Sensor sensorVelocity = sensorRepository.findByNomeMedicao("velocity");
		final Sensor sensorEffort = sensorRepository.findByNomeMedicao("effort");
		final Sensor sensorTemperatura = sensorRepository.findByNomeMedicao("temperatura");
		final Sensor sensorUmidade = sensorRepository.findByNomeMedicao("umidade");
		final Sensor sensorIluminacao = sensorRepository.findByNomeMedicao("iluminacao");
		final BracoRobotico bracoRobotico = (LocalDaSRAM.FLORIANOPOLIS.equals(localDaSRAM))?
				bracoRoboticoRepository.findByNomeAndIlha("Braço Robótico", "UFSC"):
					bracoRoboticoRepository.findByNomeAndIlha("Braço Robótico", "UFBA");
		final Recurso recursoArmazenamento = (LocalDaSRAM.FLORIANOPOLIS.equals(localDaSRAM))?
				recursoRepository.findByNomeAndIlha("Armazenamento","UFSC"):
					recursoRepository.findByNomeAndIlha("Armazenamento","UFBA");
		final Recurso recursoImp3D = (LocalDaSRAM.FLORIANOPOLIS.equals(localDaSRAM))?
				recursoRepository.findByNomeAndIlha("Imp3D","UFSC"):
					recursoRepository.findByNomeAndIlha("Imp3D","UFBA");
		final Recurso recursoSala = (LocalDaSRAM.FLORIANOPOLIS.equals(localDaSRAM))?
				recursoRepository.findByNomeAndIlha("Sala","UFSC"):
					recursoRepository.findByNomeAndIlha("Sala","UFBA");

		LOGGER.info("Limpando o banco de dados");
		leituraBracoRepository.truncateCommit();
		alarmeBracoRepository.truncateCommit();
		leituraRecursoRepository.truncateCommit();
		
				
		LOGGER.info("Lendo arquivo da Impressora");
		Reader readerPrinter = new InputStreamReader(filePrinter.getInputStream());
		ArquivoJsonPrinter arquivoJsonPrinter = mapper.readValue(readerPrinter,new TypeReference<ArquivoJsonPrinter>() {});
		
		LOGGER.info("Lendo arquivo do Robo");
		Reader readerRobot = new InputStreamReader(fileRobot.getInputStream());
		ArquivoJsonRobot arquivoJsonRobot = mapper.readValue(readerRobot,new TypeReference<ArquivoJsonRobot>() {});
		
		
		//################################################################## 
		//###################### ROBOS #####################################
		//################################################################## 
		
		List<Robo> robos = arquivoJsonRobot.getRobo();
		LOGGER.info("Leituras de "+robos.size()+" robos");
		
		//Agrupando os dados de robo
		Map<LocalDateTime, List<Robo>> leiturasBracoGroup = robos.stream().collect(Collectors.groupingBy(r -> r.getTimestamp()));
		//Mapeando para objeto relacional
		List<LeituraBraco> leiturasBraco = new ArrayList<LeituraBraco>();
		leiturasBracoGroup.forEach(
				(key,value)->{
					LeituraBraco leituraBraco = new LeituraBraco();
					leituraBraco.setData(key);
					leituraBraco.setBracoRobotico(bracoRobotico);
					value.stream().forEach(r->{
						//coordenadas
						leituraBraco.setCoordenadaX((r.getTopic().contentEquals("posX"))?r.getValue():leituraBraco.getCoordenadaX());
						leituraBraco.setCoordenadaY((r.getTopic().contentEquals("posY"))?r.getValue():leituraBraco.getCoordenadaY());
						leituraBraco.setCoordenadaZ((r.getTopic().contentEquals("posZ"))?r.getValue():leituraBraco.getCoordenadaZ());
						if(leituraBraco.getCoordenadaX()!=null ||leituraBraco.getCoordenadaY()!=null ||leituraBraco.getCoordenadaZ()!=null) {
							leituraBraco.setSensor(sensorPosition);
						}
						//juntas
						leituraBraco.setShoulderLiftJoint((r.getTopic().contentEquals("joint1"))?r.getValue():leituraBraco.getShoulderLiftJoint());
						leituraBraco.setShoulderPanJoint((r.getTopic().contentEquals("joint2"))?r.getValue():leituraBraco.getShoulderPanJoint());
						leituraBraco.setElbowJoint((r.getTopic().contentEquals("joint3"))?r.getValue():leituraBraco.getElbowJoint());
						leituraBraco.setWrist1Joint((r.getTopic().contentEquals("joint4"))?r.getValue():leituraBraco.getWrist1Joint());
						leituraBraco.setWrist2Joint((r.getTopic().contentEquals("joint5"))?r.getValue():leituraBraco.getWrist2Joint());
						leituraBraco.setWrist3Joint((r.getTopic().contentEquals("joint6"))?r.getValue():leituraBraco.getWrist3Joint());
						if(leituraBraco.getShoulderLiftJoint()!=null ||leituraBraco.getShoulderPanJoint()!=null ||leituraBraco.getElbowJoint()!=null
								||leituraBraco.getWrist1Joint()!=null ||leituraBraco.getWrist2Joint()!=null||leituraBraco.getWrist3Joint()!=null) {
							leituraBraco.setSensor(sensorPosition);
						}
						
						//velocidade (nao usada)
						leituraBraco.setShoulderLiftJointVel((r.getTopic().contentEquals("vel1"))?r.getValue():leituraBraco.getShoulderLiftJointVel());
						leituraBraco.setShoulderPanJointVel((r.getTopic().contentEquals("vel2"))?r.getValue():leituraBraco.getShoulderPanJointVel());
						leituraBraco.setElbowJointVel((r.getTopic().contentEquals("vel3"))?r.getValue():leituraBraco.getElbowJointVel());
						leituraBraco.setWrist1JointVel((r.getTopic().contentEquals("vel4"))?r.getValue():leituraBraco.getWrist1JointVel());
						leituraBraco.setWrist2JointVel((r.getTopic().contentEquals("vel5"))?r.getValue():leituraBraco.getWrist2JointVel());
						leituraBraco.setWrist3JointVel((r.getTopic().contentEquals("vel6"))?r.getValue():leituraBraco.getWrist3JointVel());
						if(leituraBraco.getShoulderLiftJointVel()!=null ||leituraBraco.getShoulderPanJointVel()!=null ||leituraBraco.getElbowJointVel()!=null
								||leituraBraco.getWrist1JointVel()!=null ||leituraBraco.getWrist2JointVel()!=null||leituraBraco.getWrist3JointVel()!=null) {
							leituraBraco.setSensor(sensorPosition);
						}
						
					});
					leiturasBraco.add(leituraBraco);
				});
		//ordenado
		List<LeituraBraco> sortedLeiturasBraco = leiturasBraco.stream().sorted(Comparator.comparing(LeituraBraco::getData)).collect(Collectors.toList());
		LOGGER.info("Convertidas em "+sortedLeiturasBraco.size()+" objetos Leitura Braco");
		LOGGER.info("Persistindo...");
		//persistindo
		sortedLeiturasBraco.forEach(lb->leituraBracoRepository.saveCommit(lb));
		LOGGER.info("Persistidas "+sortedLeiturasBraco.size()+" entradas na tabela leitura_braco");
		
		//################################################################## 
		//###################### ALARMES ###################################
		//################################################################## 
		List<Alarme> alarmes = arquivoJsonRobot.getAlarmes();
		List<AlarmeBraco> alarmesBraco = new ArrayList<AlarmeBraco>();
		LOGGER.info("Leituras de "+alarmes.size()+" alarmes");
		alarmes.forEach(a->{
			AlarmeBraco alarmeBraco = new AlarmeBraco();
			alarmeBraco.setData(a.getTimestamp());
			alarmeBraco.setValor(a.getValue());
			alarmeBraco.setBracoRobotico(bracoRobotico);
			alarmesBraco.add(alarmeBraco);
		});
		List<AlarmeBraco> sortedAlarmesBraco = alarmesBraco.stream().sorted(Comparator.comparing(AlarmeBraco::getData)).collect(Collectors.toList());
		LOGGER.info("Convertidas em "+sortedAlarmesBraco.size()+" objetos Alarme Braco");

		LOGGER.info("Persistindo...");
		//persistindo
		sortedAlarmesBraco.forEach(ab->alarmeBracoRepository.saveCommit(ab));
		LOGGER.info("Persistidas "+sortedAlarmesBraco.size()+" entradas na tabela alarme_braco");
		
		
		//################################################################## 
		//###################### RECURSOS ##################################
		//################################################################## 
		
		List<LeituraRecurso> leiturasRecurso = new ArrayList<LeituraRecurso>();

		List<Armazenamento> armazenamentos = arquivoJsonPrinter.getArmazenamento();
		LOGGER.info("Leituras de "+armazenamentos.size()+" armazenamentos");
		List<Imp3D> impressoras3D = arquivoJsonPrinter.getImp3D();
		LOGGER.info("Leituras de "+impressoras3D.size()+" impressoras3D");
		List<Sala>  salas = arquivoJsonPrinter.getSala();
		LOGGER.info("Leituras de "+salas.size()+" salas");
		
		armazenamentos.forEach(a->{
			LeituraRecurso armazenamentoRecurso = new LeituraRecurso();
			armazenamentoRecurso.setData(a.getTimestamp());
			armazenamentoRecurso.setValor(a.getValue());
			armazenamentoRecurso.setRecurso(recursoArmazenamento);
			armazenamentoRecurso.setSensor(
					(a.getTopic().contentEquals("temperatura"))?sensorTemperatura:
						(a.getTopic().contentEquals("umidade"))?sensorUmidade:
							(a.getTopic().contentEquals("iluminacao"))?sensorIluminacao:
								null
				);
			leiturasRecurso.add(armazenamentoRecurso);
		});
		
		impressoras3D.forEach(a->{
			LeituraRecurso impressora3DRecurso = new LeituraRecurso();
			impressora3DRecurso.setData(a.getTimestamp());
			impressora3DRecurso.setValor(a.getValue());
			impressora3DRecurso.setRecurso(recursoImp3D);
			impressora3DRecurso.setSensor(
					(a.getTopic().contentEquals("temperatura"))?sensorTemperatura:
						(a.getTopic().contentEquals("umidade"))?sensorUmidade:
							(a.getTopic().contentEquals("iluminacao"))?sensorIluminacao:
								(a.getTopic().contentEquals("position"))?sensorPosition:
									(a.getTopic().contentEquals("velocity"))?sensorVelocity:
										(a.getTopic().contentEquals("effort"))?sensorEffort:
					null
				);
			leiturasRecurso.add(impressora3DRecurso);
		});
		
		salas.forEach(a->{
			LeituraRecurso salaRecurso = new LeituraRecurso();
			salaRecurso.setData(a.getTimestamp());
			salaRecurso.setValor(a.getValue());
			salaRecurso.setRecurso(recursoSala);
			salaRecurso.setSensor(
					(a.getTopic().contentEquals("temperatura"))?sensorTemperatura:
						(a.getTopic().contentEquals("umidade"))?sensorUmidade:
							(a.getTopic().contentEquals("iluminacao"))?sensorIluminacao:
								(a.getTopic().contentEquals("position"))?sensorPosition:
									(a.getTopic().contentEquals("velocity"))?sensorVelocity:
										(a.getTopic().contentEquals("effort"))?sensorEffort:
					null
				);
			leiturasRecurso.add(salaRecurso);
		});
		List<LeituraRecurso> sortedLeiturasRecurso = leiturasRecurso.stream().sorted(Comparator.comparing(LeituraRecurso::getData)).collect(Collectors.toList());
		LOGGER.info("Convertidas em "+sortedLeiturasRecurso.size()+" objetos Leitura Recurso ");
		LOGGER.info("Persistindo...");
		//persistindo
		sortedLeiturasRecurso.forEach(lr->leituraRecursoRepository.saveCommit(lr));
		LOGGER.info("Persistidas "+sortedLeiturasRecurso.size()+" entradas na tabela leitura_recurso");
		
		//TODO prover meio de saber que acabou de produzir o video
		LOGGER.info("Baixando vídeo do ftp");
		try {
			return donwloadRetryable();
		}catch(Exception e) {
			return donwloadRetryable();
		}
	}
	
	private ResponseEntity donwloadRetryable() throws Exception {
		InputStream is = ftpService.download("ILHA UFSC 3.mp4");
		InputStreamResource resource = new InputStreamResource(is);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=video.mp4");
		LOGGER.info("Disponibilizando vídeo do resultado");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(resource);
	}
	
	
//	@ApiOperation(value = "Lista os arquivos no FTP", nickname="listarFTP")
//	@RequestMapping(value = "/listarArquivos", method = RequestMethod.GET)
//	public ResponseEntity<List<String>> listarVideos(HttpServletRequest request) throws Exception {
//		List<String> files = ftpService.leArquivos();
//		return ResponseEntity.ok(files);
//	}



}
