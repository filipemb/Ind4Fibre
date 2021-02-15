package com.ind4fibre.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;

@Configuration
public class FTPConnectionConfig {
	
	@Value("${ftp.host}")
    private String host;
	
	@Value("${ftp.user}")
    private String user;
	
	@Value("${ftp.password}")
    private String password;
	
	@Value("${ftp.port}")
    private int port;

	@Bean(destroyMethod = "disconnect")
	public FileTransferClient fileTransferClient() {
		FileTransferClient ftp =  new FileTransferClient();
		try {
			ftp.setRemoteHost(host);
			ftp.setUserName(user);
			ftp.setPassword(password); 
			ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
			ftp.setRemotePort(port);
			ftp.setTimeout(0);//never expires
			ftp.connect();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FTPException e) {
			e.printStackTrace();
		}
		
		return ftp;
	}
	
}

