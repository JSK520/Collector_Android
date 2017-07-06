package Client_Server;

import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.util.Logger;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;

import WossConfiguration.WossConfiguration;

public class ClientStart {
	
	public static void main(String[] args) throws Exception {
		
		Properties p = new Properties();
		try {
			p.load(new FileReader("conf/conf.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collection<BIDR> list = null;
		WossConfiguration c = new WossConfiguration();
		c.init(p);
		
		Logger logger = c.getLogger();
		logger.info("�ͻ�������...");
		logger.info("�ɼ�ģ������...");
		
		Gather gather = c.getGather();
		logger.info("�ͻ���ģ������...");
		
		BackUP backup = c.getBackup();
		Client client = c.getClient();
		
		try {
			while(true)
			{
				Collection<BIDR> l = null;
				try {
					l = (Collection<BIDR>) backup.load("key", true);
				} catch (Exception e) {
					break;
				}
				if(l!=null)
				client.send(l);
			}
			logger.info("���ݲɼ���...");
			 list = gather.gather();
			logger.info("��������...");
			client.send(list);
			logger.info("���ݷ�����ϣ�����");
		} catch (Exception e) {
			logger.info("����ʧ�ܣ�������...");
			backup.store("key", list, true);
			logger.info("�������");
		}
	}
}
