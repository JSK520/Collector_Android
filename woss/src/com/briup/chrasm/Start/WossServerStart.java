package com.briup.chrasm.Start;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import com.briup.chrasm.Configuration.WossConfiguration;
import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class WossServerStart {

	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		try {
			p.load(new FileReader("conf/conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WossConfiguration c = new WossConfiguration();
		c.init(p);
		Logger logger = c.getLogger();
		logger.info("���������......");
		logger.info("�����ģ������...");
		Server server = c.getServer();
		
		logger.info("���ģ������...");
		
		
		logger.info("���ݽ�����...");
		while(true){
			logger.info("׼������.....");
			Collection<BIDR> collection = server.revicer();
			logger.info("��������: "+collection.size());
			new BDStoreThread(collection,c).start();
		}
	}
	static class BDStoreThread extends Thread{
		private Collection<BIDR> collection;
		private Configuration c;
		
		public BDStoreThread(Collection<BIDR> collection, Configuration c) {
			super();
			this.collection = collection;
			this.c = c;
		}


		@Override
		public void run() {
			try {
				Logger logger = c.getLogger();
				logger.info("׼�����.......");
				DBStore dbStore = c.getDBStore();
				dbStore.saveToDB(collection);
				logger.info("�����ϣ�");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
