package com.ind4fibre.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprisedt.net.ftp.FTPConnectionClosedException;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;

@Service
public class FTPService {

	@Autowired
	private FileTransferClient ftp;
	

	public List<String> leArquivos() throws Exception {
		String[] files = null;
		try {
			files = ftp.directoryNameList();
		} catch (FTPException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return Arrays.asList(files);
	}
	
	public synchronized InputStream download(String nomeArquivo) throws Exception{
		 InputStream in=null;
		try {
			if(!ftp.isConnected()) {ftp.connect();}
			in = ftp.downloadStream(nomeArquivo);
		}catch (FTPConnectionClosedException e){
			e.printStackTrace();
			throw new Exception(e);
		} catch (FTPException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		 return in;
	}

}
