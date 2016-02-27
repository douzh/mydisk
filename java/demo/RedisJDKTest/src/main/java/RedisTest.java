import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;

public class RedisTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = connect();
		System.out.println("-------------string------------------------");
		// ���� redis �ַ�������
		jedis.set("stringkey", "this is string value");
		// ��ȡ�洢�����ݲ����
		System.out.println("Stored string in redis:: " + jedis.get("stringkey"));
		System.out.println("--------------list-----------------------");
		// �洢���ݵ��б���
		jedis.lpush("listkey", "Redis");
		jedis.lpush("listkey", "Mongodb");
		jedis.lpush("listkey", "Mysql");
		// ��ȡ�洢�����ݲ����
		for (int i = 0; i < jedis.llen("listkey"); i++) {
			System.out.println("list:" + jedis.lindex("listkey", i));
		}
		System.out.println("---------------jedis.llen----------------");
		List<String> list = jedis.lrange("listkey", 0, jedis.llen("listkey")-1);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("list:" + list.get(i));
		}
		System.out.println("---------------map----------------");
		Map<String,String> m=new HashMap<String,String>();
		m.put("filed1", "abc");
		m.put("filed2", "a string");
		jedis.hmset("mkey", m);
		jedis.hset("mkey", "filed3", "jkl");
		m=jedis.hgetAll("mkey");
		Iterator<Entry<String,String>> ite= m.entrySet().iterator();
		while(ite.hasNext()){
			Entry<String, String> e=ite.next();
			System.out.println("key:" + e.getKey()+" value:"+e.getValue());
		}
		System.out.println("-------------del all key-----------------");
		// ��ȡ���ݲ����
		Iterator<String> keys = jedis.keys("*").iterator();
		while(keys.hasNext()) {
			String key=keys.next();
			jedis.del(key);
			System.out.println("del key: " + key);
		}
	}

	public static Jedis connect() {
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// �鿴�����Ƿ�����
		System.out.println("Server is running: " + jedis.ping());
		return jedis;

	}

}
