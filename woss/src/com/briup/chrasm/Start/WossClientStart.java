package com.briup.chrasm.Start;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import com.briup.chrasm.Configuration.WossConfiguration;
import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;

public class WossClientStart {
	public static void test() throws Exception{
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
		logger.info("�ͻ�������......");
		logger.info("�ɼ�ģ�������......");
		Gather gather = c.getGather();
		logger.info("�ͻ���ģ�������......");
		Client client = c.getClient();
		logger.info("����ģ�������......");
		BackUP backup = c.getBackup();
		
		logger.info("���ݲɼ���......");
		Collection<BIDR> list = gather.gather();
		logger.info("�ɼ����ݣ�"+list.size());
		try{
			logger.info("��������......");
			client.send(list);
			logger.info("���ݷ�����ϣ�������");
			
			
			logger.info("��鱸��......");
			File file = new File(p.getProperty("databackup"));
			if(file.exists()){
				if(file.isDirectory()){
					String[] blist = file.list();
					for(String i:blist){
						String str = file.getName()+"/"+i;
						logger.info("�����ļ���"+str);
						Collection<BIDR> l = (Collection<BIDR>)backup.load(str, true);
						logger.info("�������ݣ�"+l.size());
						logger.info("���ͱ�������.....");
						client.send(l);
						logger.info("�������ݷ�����ϣ�");
					}
				}
			}
			
		}catch(Exception e){
			logger.info("����ʧ��,������......");
			String filename = "databackup/"+System.currentTimeMillis();
			logger.info("�����ļ���"+filename);
			backup.store(filename, list, true);
			logger.info("������ϣ�");
		}
	}
	public static void main(String[] args) throws Exception {
		test();
//		test();
	}

}
